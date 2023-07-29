package assembler;

import java.io.*;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

// THIS CLASS IS A SINGLETON
public class Assembler {
    private final HashMap<String, Symbol> symbolTable = new HashMap<>();
    private final HashMap<Segments, Short> segmentTable = new HashMap<>();

    // Defining the processors
    private final LabelProcessor labelProcessor = new LabelProcessor();
    private final DirectiveProcessor directiveProcessor = new DirectiveProcessor();
    private final OperationProcessor operationProcessor = new OperationProcessor();

    // Assembled code
    private LinkedList<Short> assembledCode = new LinkedList<>();

    // Handling files utils
    private String currentLine;
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

    }

    public static Assembler getInstance() {
        if (instance == null)
            instance = new Assembler();
        return instance;
    }

    public void assembleFile(String pathToProgram) throws FileNotFoundException {
        FileReader fileReader = new FileReader(pathToProgram);

        try (BufferedReader fileIO = new BufferedReader(fileReader)) {
            do {
                // Our assembler IS NOT case sensitive!!
                currentLine = fileIO.readLine().toUpperCase();
                assembleLine();
            } while (!currentLine.isEmpty());
        }
        catch (IOException e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void assembleLine() {
        if(operationProcessor.isOperation(currentLine)) {
            operationProcessor.assembleOperation(currentLine);
        }

    }
}
