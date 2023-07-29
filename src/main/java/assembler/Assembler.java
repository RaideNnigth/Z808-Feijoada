package assembler;

import java.io.*;
import java.io.File;
import java.util.Scanner;

public class Assembler {
    private String currentLine;
    private SymbolTable symbolTable;
    private SegmentTable segmentTable;
    private OperationProcessor operationProcessor;

    private int PC;
    private final int HEADER_SIZE = 0xC;
    private int CS_END;
    private int DS_START;
    private int DS_END;
    private int SS_START;
    private int SS_END;

    public Assembler() {
        symbolTable = new SymbolTable();
        segmentTable = new SegmentTable();
        operationProcessor = new OperationProcessor();
    }

    public void assemble(String pathToProgram) throws FileNotFoundException {
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
        if(operationProcessor.verifyAndProcessOP(currentLine))
            return;
    }
}
