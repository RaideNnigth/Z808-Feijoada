package assembler;

import assembler.codeprocessors.DirectiveProcessor;
import assembler.codeprocessors.LabelProcessor;
import assembler.codeprocessors.OperationProcessor;
import assembler.tables.symboltable.Symbol;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
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
    private String filename;
    // Position where the code will be
    private int PC;

    // Defining header metadata
    private final int HEADER_SIZE = 0xC;
    private int CS_END;
    private int DS_START;
    private int DS_END;
    private int SS_START;
    private int SS_END;

    // Singleton definition
    private static Assembler instance;

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
        filename = pathToProgram;

        FileReader fileReader = new FileReader(filename);

        try (BufferedReader fileIO = new BufferedReader(fileReader)) {
            lineCounter += 1;
            currentLine = fileIO.readLine();

            while (currentLine != null && !loggerInterruption) {
                // Our assembler IS NOT case sensitive!!
                currentLine = currentLine.toUpperCase();
                assembleLine();

                currentLine = fileIO.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        // Reset after assembly
        assembledCode.clear();
        logger.reset();
    }

    private void assembleLine() {
        currentLine = currentLine.split(";")[0];

        // Line is just a comment
        if (currentLine.equals("")) {
            return;
        }

        // Handling labels
        labelProcessor.assembleLabel(currentLine);

        // Handling directives
        directiveProcessor.assembleDirective(currentLine);

        // Handling operations
        if (operationProcessor.isOperation(currentLine)) {
            operationProcessor.assembleOperation(currentLine);
            for (short s : assembledCode) {
                System.out.print(Integer.toHexString(s) + " ");
            }
            System.out.println();
        }

    }

    public int getPC() {
        return PC;
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
}
