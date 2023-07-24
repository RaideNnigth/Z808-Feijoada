package assembler;

import java.io.File;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;

public class Assembler {
    private String filepath;
    private File assemblyFile;
    private SymbolTable symbolTable;
    private SegmentTable segmentTable;

    public Assembler() {
        filepath = "";
        symbolTable = new SymbolTable();
        segmentTable = new SegmentTable();
    }

    public void stepOne() throws IOException {
        Scanner reader = new Scanner(assemblyFile);
        String buffer = "";
        while (reader.hasNext()) {
            buffer.concat(reader.next());
        }
    }

    public void stepTwo() {

    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) throws FileNotFoundException {
        assemblyFile = new File(filepath);

        if (!assemblyFile.exists())
            throw new FileNotFoundException("File not found!");
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public SegmentTable getSegmentTable() {
        return segmentTable;
    }
}
