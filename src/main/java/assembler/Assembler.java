package assembler;

import assembler.codeprocessors.DirectiveProcessor;
import assembler.codeprocessors.LabelProcessor;
import assembler.codeprocessors.OperationProcessor;
import assembler.tables.datatable.DataTable;
import assembler.tables.symboltable.SymbolTable;
import assembler.tables.symboltable.UndeclaredSymbol;
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
    private final MacroProcessor macroProcessor = new MacroProcessor();

    // Assembled code
    private final LinkedList<Short> assembledCode = new LinkedList<>();
    // Assembled data
    private final ArrayList<Byte> assembledData = new ArrayList<>();

    // Handling files utils
    private String currentLine;
    private int lineCounter;

    // Position where the code will be
    private int pc = 0;

    // Header metadata
    private int csEnd;
    private int dsStart;
    private int dsEnd;

    // Singleton definition
    private static Assembler instance;

    // Interaction with Segment system
    private boolean isCodeSegment;
    private boolean codeSegmentSet;
    private boolean dataSegmentSet;

    private Assembler() {
        lineCounter = 0;
    }

    public static Assembler getInstance() {
        if (instance == null)
            instance = new Assembler();
        return instance;
    }

    public void assembleFile(String pathToProgram) throws Exception {
        // Handling macros and returns intermediate file path to new file soo we do not change user one
        pathToProgram = macroProcessor.parseMacros(pathToProgram);
        FileReader fileReader = new FileReader(pathToProgram);
        Logger.getInstance().reset();

        // Assemble line by line
        try (BufferedReader fileIO = new BufferedReader(fileReader)) {
            currentLine = fileIO.readLine();
            lineCounter += 1;

            while (currentLine != null && !Logger.getInstance().isInterrupted()) {
                // Our assembler IS NOT case-sensitive!!
                currentLine = currentLine.toUpperCase();
                assembleLine();
                lineCounter += 1;

                currentLine = fileIO.readLine();
            }
        } catch (IOException e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "Error while reading " + pathToProgram + "file: " + e.getMessage()));
            Logger.getInstance().printLogs();
            resetAssembler();
            return;
        } catch (Exception e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "ASSEMBLY ERROR\n" + e.getMessage()));
            Logger.getInstance().printLogs();
            resetAssembler();
            return;
        } finally {
            fileReader.close();
        }

        // Resolve symbols in symboltable
        try {
            SymbolTable.getInstance().replaceAllOcorrencesOfDeclaredSymbols();
        } catch (UndeclaredSymbol e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "ERROR ON SYMBOL TABLE" + e.getMessage()));
            Logger.getInstance().printLogs();
            resetAssembler();
            return;
        }

        // Resolve symbols in datatable
        try {
            DataTable.getInstance().replaceAllOcorrencesOfDeclaredDataItems();
        } catch (Exception e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "ERROR ON DATA TABLE\n" + e.getMessage()));
            Logger.getInstance().printLogs();
            resetAssembler();
            return;
        }

        // No code segment was declared
        if (!codeSegmentSet) {
            resetAssembler();
            throw new Exception("Code segment not set!");
        }

        // -------------------------- Calculating segments of header --------------------------
        int headerSize = 0xC;
        // Since each short takes 2 bytes, we need to multiply by 2. We need to subtract 1 because the first byte is already counted
        csEnd = headerSize + (assembledCode.size() * 2) - 1;

        // If no data segment was declared, set invalid segment for interpreter ignore
        if (!dataSegmentSet) {
            dsStart = headerSize;
            dsEnd = csEnd;
        }
        // If data segment was declared,
        else {
            dsStart = csEnd + 1;
            // For the same reason than code segment, we need to subtract 1. But this time we don't need to multiply by 2, since each byte takes 1 byte
            dsEnd = dsStart + assembledData.size() - 1;
        }

        // Removes ".asm" extension and append ".bin"
        pathToProgram = pathToProgram.substring(0, pathToProgram.length() - 4);
        pathToProgram += ".bin";

        // Writing header
        OutputStream outputStream = new FileOutputStream(pathToProgram);
        try (DataOutputStream dataOutStream = new DataOutputStream(outputStream)) {
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
        } catch (IOException e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "Error while writing " + pathToProgram + "file: " + e.getMessage()));
            Logger.getInstance().printLogs();
            resetAssembler();
            return;
        }

        // Log success message
        Logger.getInstance().addLog(new Log(LogType.INFO, 0, "Code assembled!"));
        Logger.getInstance().printLogs();

        // Reset after assembly
        resetAssembler();
    }

    private void assembleLine() throws Exception {
        currentLine = currentLine.split(";")[0];

        // Line is just a comment
        if (currentLine.isEmpty())
            return;

        // Handling labels declaration
        if (labelProcessor.assembleLabel(currentLine))
            return;

        // Handling directives
        try {
            if (directiveProcessor.assembleDirective(currentLine))
                return;
        } catch (Exception e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "ERROR ON DIRECTIVE PROCESSOR\n " + e.getMessage()));
            return;
        }

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
}
