package assembler;

import assembler.codeprocessors.DirectiveProcessor;
import assembler.codeprocessors.LabelProcessor;
import assembler.codeprocessors.OperationProcessor;
import assembler.tables.symboltable.SymbolTable;
import assembler.tables.symboltable.UndeclaredSymbol;

import java.io.*;
import java.util.LinkedList;

// THIS CLASS IS A SINGLETON
public class Assembler {
    // Defining the processors
    private final LabelProcessor labelProcessor = new LabelProcessor();
    private final DirectiveProcessor directiveProcessor = new DirectiveProcessor();
    private final OperationProcessor operationProcessor = new OperationProcessor();

    // Assembled code
    private LinkedList<Short> assembledCode = new LinkedList<>();

    // Logger
    private final Logger logger = new Logger();
    private boolean loggerInterruption;

    // Handling files utils
    private String currentLine;
    private int lineCounter;

    // Position where the code will be
    private int PC = 0;
    // DELETE AFTER
    private int lastPrint = 0;

    // Defining header metadata
    private final int HEADER_SIZE = 0xC;
    public static int CS_END;
    public static int DS_START;
    public static int DS_END;
    public static int SS_START;
    public static int SS_END;

    // Singleton definition
    private static Assembler instance;

    // Interaction with Segment system
    private boolean isCodeSegment;
    private static boolean codeSegmentSet;
    private static boolean dataSegmentSet;

    private Assembler() {
        loggerInterruption = false;
        lineCounter = 0;
    }

    public static Assembler getInstance() {
        if (instance == null)
            instance = new Assembler();
        return instance;
    }

    public void assembleFile(String pathToProgram) throws FileNotFoundException {
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
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        try {
            SymbolTable.getInstance().replaceAllOcorrencesOfDeclaredSymbols();
        } catch (UndeclaredSymbol e) {
            System.out.println(e.getMessage());
        }

        // Reset after assembly
        SymbolTable.getInstance().reset();
        assembledCode.clear();
        logger.reset();
        PC = 0;
        lastPrint = 0;

        // TODO:
        // 1- create header
        // 2- write in binary file
        // 3- interface with GUI
        // 4- when assembled -> enable buttons

        // Writing header
        try(OutputStream outputStream = new FileOutputStream(pathToProgram + ".bin")) {
            outputStream.write(HEADER_SIZE);
            outputStream.write(CS_END + HEADER_SIZE);
            outputStream.write(DS_START + HEADER_SIZE);
            outputStream.write(DS_END + HEADER_SIZE);
            outputStream.write();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void assembleLine() {
        currentLine = currentLine.split(";")[0];

        // Line is just a comment
        if (currentLine.isEmpty())
            return;

        // Handling labels
        if (labelProcessor.assembleLabel(currentLine))
            return;

        try {
            // Handling directives
            if (directiveProcessor.assembleDirective(currentLine))
                return;
        } catch (Exception e) {

        }

        // Handling operations
        if (operationProcessor.assembleOperation(currentLine)) {
            PC = assembledCode.size() - 1;

            // Print -> Remove later
            for (; lastPrint < assembledCode.size(); lastPrint++) {
                System.out.print(Integer.toHexString(assembledCode.get(lastPrint) & 0xFFFF) + " ");
            }
            System.out.println();
        }

        if (isCodeSegment) {
            CS_END = PC;
            DS_START = CS_END + 1;
        } else {
            DS_END = PC;
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

    public String getCurrentLine() {
        return currentLine;
    }

    public int getLineCounter() {
        return lineCounter;
    }

    public void setSegment(String segment) {
        switch (segment) {
            case ".CODE" -> isCodeSegment = true;
            case ".DATA" -> isCodeSegment = false;
        }
    }

    public void codeSegmentFound() {
        codeSegmentSet = true;
    }

    public void dataSegmentFound() {
        dataSegmentSet= true;
    }
}
