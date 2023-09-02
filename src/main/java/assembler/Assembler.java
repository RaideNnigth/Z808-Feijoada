package assembler;

import assembler.codeprocessors.DirectiveProcessor;
import assembler.codeprocessors.LabelProcessor;
import assembler.codeprocessors.LinkerDirectivesProcessor;
import assembler.codeprocessors.OperationProcessor;
import assembler.linkerdirectives.Name;
import assembler.tables.datatable.DataTable;
import assembler.tables.datatable.UndeclaredDataItem;
import assembler.tables.symboltable.SymbolTable;
import assembler.tables.symboltable.UndeclaredSymbol;
import assembler.utils.AssemblerUtils;
import linker.tables.exceptions.UndaclaredModuleNameException;
import logger.*;
import macroprocessor.MacroProcessor;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

// THIS CLASS IS A SINGLETON
public class Assembler {
    // Defining the processors
    private final LabelProcessor labelProcessor = new LabelProcessor();
    private final DirectiveProcessor directiveProcessor = new DirectiveProcessor();
    private final OperationProcessor operationProcessor = new OperationProcessor();
    private final LinkerDirectivesProcessor linkerDirectivesProcessor = new LinkerDirectivesProcessor();

    // Assembled code
    private final LinkedList<Short> assembledCode = new LinkedList<>();
    // Assembled data
    private final ArrayList<Byte> assembledData = new ArrayList<>();

    // Handling files utils
    private String currentModuleName;
    private String inputFile;
    private String outputFile;
    private String currentLine;
    private int lineCounter;

    // Position where the code will be
    private int pc = 0;

    // Header metadata
    private final int headerSize = 0xC;
    private int csEnd;
    private int dsStart;
    private int dsEnd;

    // Singleton definition
    private static Assembler instance;

    // Interaction with Segment system
    private boolean isCodeSegment;
    private boolean codeSegmentSet;
    private boolean dataSegmentSet;

    private final Logger logger = Logger.getInstance();

    private Assembler() {
        lineCounter = 0;
    }

    public static Assembler getInstance() {
        if (instance == null)
            instance = new Assembler();
        return instance;
    }

    public void assembleFile(String inputFile) {
        this.inputFile = inputFile;
        this.outputFile = String.valueOf(inputFile).replace(".pre", ".bin").replace(".asm", ".bin");
        this.startAssembleFile();
        this.resetAssembler();
    }

    public void startAssembleFile() {
        // Handling macros and returns intermediate file path to new file soo we do not change user one
        //pathToProgram = macroProcessor.parseMacros(pathToProgram);
        FileReader fileReader;

        try {
            fileReader = new FileReader(this.inputFile);
        } catch (FileNotFoundException e) {
            logger.error(0, String.format("IO Error: Input file (%s) not found!", this.inputFile));
            //Logger.getInstance().addLog(new Log(LogType.ERROR, 0, String.format("IO Error: Input file (%s) not found!", this.inputFile)));
            return;
        }

        // Assemble line by line
        try (BufferedReader fileIO = new BufferedReader(fileReader)) {
            this.currentLine = fileIO.readLine();

            // Process first line
            currentLine = currentLine.toUpperCase();
            assembleFirstLine();

            this.currentLine = fileIO.readLine();
            while (this.currentLine != null) { //&& !Logger.getInstance().isInterrupted()) {
                // Our assembler IS NOT case-sensitive!!
                // The input file will be uppercased because of macroprocessor
                currentLine = currentLine.toUpperCase();
                assembleLine();

                this.lineCounter += 1;
                this.currentLine = fileIO.readLine();
            }
        } catch (IOException e) {
            logger.error(lineCounter, String.format("IO Error: Error while reading %s (%s)", this.inputFile, e.getMessage()));
            //Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, String.format("IO Error: Error while reading %s (%s)", this.inputFile, e.getMessage())));
            return;
        } catch (AssemblerError e) {
            logger.error(lineCounter, String.format("Assembly error: %s", e.getMessage()));
            //Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, String.format("Assembly error: %s", e.getMessage())));
            return;
        } catch (Exception e) {
            logger.error(lineCounter, String.format("Unknown error: %s", e.getMessage()));
            //Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, String.format("Unknown error: %s", e.getMessage())));
            return;
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                logger.error(lineCounter, String.format("Fatal error: couldn't close input file! (%s)", e.getMessage()));
                //Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, String.format("Fatal error: couldn't close input file! (%s)", e.getMessage())));
            }
            //return;
        }

        // Resolve symbols in symboltable
        try {
            SymbolTable.getInstance().replaceAllOcorrencesOfDeclaredSymbols();
        } catch (UndeclaredSymbol e) {
            logger.error(lineCounter, String.format("Symbol table error: %s", e.getMessage()));
            //Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, String.format("Symbol table error: %s", e.getMessage())));
            //Logger.getInstance().printLogs();
            //resetAssembler();
            return;
        }

        // Resolve symbols in datatable
        try {
            DataTable.getInstance().replaceAllOcorrencesOfDeclaredDataItems();
        } catch (UndeclaredDataItem e) {
            logger.error(lineCounter, String.format("Data table error: %s", e.getMessage()));
            //Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, String.format("Data table error: %s", e.getMessage())));
            return;
        } catch (Exception e) {
            logger.error(lineCounter, String.format("Unknown error: %s", e.getMessage()));
            //Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, String.format("Unknown error: %s", e.getMessage())));
            return;
        }

        // No code segment was declared
        if (!codeSegmentSet) {
            logger.error(lineCounter, "Code segment error: code segment isn't set");
            //Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "Code segment error: code segment isn't set"));
            return;
        }

        this.calculateHeader();

        try {
            this.writeToOutputFile();
        } catch (FileNotFoundException e) {
            logger.error(lineCounter, String.format("File error: couldn't create output file (%s)", e.getMessage()));
            //Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, String.format("File error: couldn't create output file (%s)", e.getMessage())));
            return;
        } catch (IOException e) {
            logger.error(lineCounter, String.format("IO Error: Error while writing to %s (%s)", this.outputFile, e.getMessage()));
            //Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, String.format("IO Error: Error while writing to %s (%s)", this.outputFile, e.getMessage())));
            return;
        }

        // Log success message
        logger.info(lineCounter, "Code assembled!");
        //Logger.getInstance().addLog(new Log(LogType.INFO, 0, "Code assembled!"));
        //Logger.getInstance().printLogs();

        // Reset after assembly
        //resetAssembler();
    }

    private void assembleLine() throws AssemblerError, Exception {
        currentLine = currentLine.split(";")[0];

        // Line is just a comment
        if (currentLine.isEmpty())
            return;

        // Handling labels declaration
        if (labelProcessor.assembleLabel(currentLine))
            return;

        // Handling directives
        //try {
        if (directiveProcessor.assembleDirective(currentLine))
            return;
        //} catch (Exception e) {
            //Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "ERROR ON DIRECTIVE PROCESSOR\n " + e.getMessage()));
            //return;
        //}



        // Handling operations and data declarations
        if (isCodeSegment) {
            // Handle operations
            operationProcessor.assembleOperation(currentLine);
            pc = assembledCode.size() - 1;
        } else {
            // Handle declaration of "variables"
            DataTable.getInstance().processDataItemDeclaration(this.currentLine);
        }

    }

    // First line MUST be the name of the module
    private void assembleFirstLine() throws UndaclaredModuleNameException {
        String[] tokens = AssemblerUtils.decomposeInTokens(currentLine);

        if(tokens[0].equals(Name.MNEMONIC)) {
            linkerDirectivesProcessor.processLinkerDirective(currentLine);
        }
        else {
            throw new UndaclaredModuleNameException();
        }
    }

    private void calculateHeader() {
        // -------------------------- Calculating segments of header --------------------------
        // Since each short takes 2 bytes, we need to multiply by 2. We need to subtract 1 because the first byte is already counted
        this.csEnd = this.headerSize + (this.assembledCode.size() * 2) - 1;

        // If no data segment was declared, set invalid segment for interpreter ignore
        if (!this.dataSegmentSet) {
            this.dsStart = headerSize;
            this.dsEnd = this.csEnd;
        }
        // If data segment was declared,
        else {
            this.dsStart = this.csEnd + 1;
            // For the same reason than code segment, we need to subtract 1. But this time we don't need to multiply by 2, since each byte takes 1 byte
            this.dsEnd = this.dsStart + this.assembledData.size() - 1;
        }
    }

    private void writeToOutputFile() throws FileNotFoundException, IOException {
        System.out.println("Writing to output file (from Assembler class): " + this.outputFile);
        OutputStream outputStream = new FileOutputStream(this.outputFile);

        DataOutputStream dataOutStream = new DataOutputStream(outputStream);
        dataOutStream.writeShort(Short.reverseBytes((short) headerSize));
        dataOutStream.writeShort(Short.reverseBytes((short) (csEnd)));

        dataOutStream.writeShort(Short.reverseBytes((short) (dsStart)));
        dataOutStream.writeShort(Short.reverseBytes((short) (dsEnd)));

        // Henrique: podemos usar essa área antiga da pilha para colocar o endereço de variáveis externas
        // Gustavo: você é muito inteligente
        dataOutStream.writeShort(Short.reverseBytes((short) (0)));
        dataOutStream.writeShort(Short.reverseBytes((short) (-1)));

        // Writing code segment
        for (short s : assembledCode) {
            dataOutStream.writeShort(Short.reverseBytes(s));
        }

        // Writing data segment
        for (int i = 1; i < assembledData.size(); i += (i % 2 == 0) ? 3 : -1) {
            dataOutStream.writeByte(assembledData.get(i));
        }

        // Amados, fechem as streams. Jesus perdoa vocês, eu não - Henrique
        outputStream.close();
        dataOutStream.close();
    }

    private void resetAssembler() {
        SymbolTable.getInstance().reset();
        DataTable.getInstance().reset();

        assembledData.clear();
        assembledCode.clear();

        lineCounter = 0;

        csEnd = 0;
        dsStart = 0;
        dsEnd = 0;
        pc = 0;

        isCodeSegment = false;
        codeSegmentSet = false;
        dataSegmentSet = false;
    }

    public LinkedList<Short> getAssembledCode() {
        return assembledCode;
    }

    public ArrayList<Byte> getAssembledData() {
        return assembledData;
    }

    public int getLineCounter() {
        return lineCounter;
    }

    public void setSegment(String segment) {
        if (segment.equals(".CODE")) {
            isCodeSegment = true;
        } else if (segment.equals(".DATA")) {
            isCodeSegment = false;
        }
    }

    public void codeSegmentFound() {
        codeSegmentSet = true;
    }

    public void dataSegmentFound() {
        dataSegmentSet = true;
    }

    public boolean isCodeSegmentFound() {
        return codeSegmentSet;
    }

    public boolean isDataSegmentFound() {
        return dataSegmentSet;
    }

    public void setModuleName(String moduleName) {
        this.currentModuleName = moduleName;
    }

    public String getModuleName() {
        return this.currentModuleName;
    }
}
