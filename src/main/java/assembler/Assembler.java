package assembler;

import assembler.codeprocessors.DirectiveProcessor;
import assembler.codeprocessors.LabelProcessor;
import assembler.codeprocessors.OperationProcessor;

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

        // Reset after assembly
        assembledCode.clear();
        logger.reset();
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
        if (directiveProcessor.assembleDirective(currentLine))
            return;

        // Handling operations
        if (operationProcessor.assembleOperation(currentLine)) {
            for (int i = PC; i < assembledCode.size(); i++) {
                System.out.print(assembledCode.get(i) + " ");
            }
            System.out.println();
        }

        PC = assembledCode.size() - 1;
    }

    public int getPC() {
        return PC;
    }

    public void incrementPC() {
        PC += 1;
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
