package assembler;

import assembler.codeprocessors.DirectiveProcessor;
import assembler.codeprocessors.LabelProcessor;
import assembler.codeprocessors.OperationProcessor;
import assembler.tables.symboltable.SymbolTable;
import assembler.tables.symboltable.UndeclaredSymbol;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

// THIS CLASS IS A SINGLETON
public class Assembler {
    // Defining the processors
    private final LabelProcessor labelProcessor = new LabelProcessor();
    private final DirectiveProcessor directiveProcessor = new DirectiveProcessor();
    private final OperationProcessor operationProcessor = new OperationProcessor();

    // Assembled code
    private final LinkedList<Short> assembledCode = new LinkedList<>();

    // Logger
    private final Logger logger = new Logger();
    private boolean loggerInterruption;

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
        loggerInterruption = false;
        lineCounter = 0;
    }

    public static Assembler getInstance() {
        if (instance == null)
            instance = new Assembler();
        return instance;
    }

    public void assembleFile(String pathToProgram) throws Exception {
        FileReader fileReader = new FileReader(pathToProgram);

        try (BufferedReader fileIO = new BufferedReader(fileReader)) {
            lineCounter += 1;
            currentLine = fileIO.readLine();

            while (currentLine != null && !loggerInterruption) {
                // Our assembler IS NOT case-sensitive!!
                currentLine = currentLine.toUpperCase();
                assembleLine();

                currentLine = fileIO.readLine();
            }
        } catch (IOException e) {
            logger.addLog(new Log(LogType.ERROR, lineCounter, "Error while reading " + pathToProgram + "file: " + e.getMessage()));
            System.exit(-1);
        }

        try {
            SymbolTable.getInstance().replaceAllOcorrencesOfDeclaredSymbols();
        } catch (UndeclaredSymbol e) {
            logger.addLog(new Log(LogType.ERROR, lineCounter, "Error on Symbol table " + e.getMessage()));
        }

        if (!codeSegmentSet) {
            throw new Exception("Code segment not set!");
        }

        // Set invalid segment for interpreter ignore
        // Defining header metadata
        int headerSize = 0xC;
        if (!dataSegmentSet) {
            dsStart = headerSize;
            dsEnd = csEnd;
        }

        // Writing header
        OutputStream outputStream = new FileOutputStream(pathToProgram + ".bin");
        try (DataOutputStream dataOutStream = new DataOutputStream(outputStream)) {

            dataOutStream.writeShort(Short.reverseBytes((short) headerSize));
            dataOutStream.writeShort(Short.reverseBytes((short) (csEnd * 2 + headerSize + 1)));

            dataOutStream.writeShort(Short.reverseBytes((short) (dsStart * 2 + headerSize + 1)));
            dataOutStream.writeShort(Short.reverseBytes((short) (dsEnd * 2 + headerSize + 1)));

            dataOutStream.writeShort(Short.reverseBytes((short) (0)));
            dataOutStream.writeShort(Short.reverseBytes((short) (-5)));

            for (short s : assembledCode) {
                dataOutStream.writeShort(Short.reverseBytes(s));
            }
        } catch (IOException e) {
            logger.addLog(new Log(LogType.ERROR, lineCounter, "Error while writing binary file!"));
        }

        // Log success message
        logger.addLog(new Log(LogType.INFO, 0, "Finish to assemble code file!"));

        // Reset after assembly
        resetAfterAssembly();
    }

    private void resetAfterAssembly() {
        SymbolTable.getInstance().reset();
        assembledCode.clear();
        logger.reset();
        pc = 0;
    }

    private void assembleLine() {
        currentLine = currentLine.split(";")[0];

        // Line is just a comment
        if (currentLine.isEmpty())
            return;

        // Handling labels
        if (labelProcessor.assembleLabel(currentLine))
            return;

        // Handling directives
        try {
            if (directiveProcessor.assembleDirective(currentLine))
                return;
        } catch (Exception e) {
            logger.addLog(new Log(LogType.ERROR, lineCounter, "Error on Directive Processor: " + e.getMessage()));
        }

        // Handling operations
        try {
            if (operationProcessor.assembleOperation(currentLine)) {
                pc = assembledCode.size() - 1;
            }
        } catch (Exception e) {
            logger.addLog(new Log(LogType.ERROR, lineCounter, "Error on Operation Processor: " + e.getMessage()));
        }


        if (isCodeSegment) {
            csEnd = pc;
            dsStart = csEnd + 1;
        } else {
            dsEnd = pc;
        }
    }

    public boolean isLoggerInterruption() {
        return loggerInterruption;
    }

    public void setLoggerInterruption(boolean loggerInterruption) {
        this.loggerInterruption = loggerInterruption;
    }

    public LinkedList<Short> getAssembledCode() {
        return assembledCode;
    }

    public Logger getLogger() {
        return logger;
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
}
