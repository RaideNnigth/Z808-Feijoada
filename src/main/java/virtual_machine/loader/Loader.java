package virtual_machine.loader;

import virtual_machine.memory.MemoryController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Loader {
    private String filePath;
    private FileInputStream programFileReader;
    private MemoryController memoryController;

    public Loader(String path) {
        memoryController = new MemoryController();
        setPathToProgram(path);
    }

    public void setPathToProgram(String path) {
        filePath = path;

        try {
            programFileReader = new FileInputStream(filePath);

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado!");
        }
    }

    public boolean loadToMemory() {
        // Reading header
        try {
            int CS_Start = programFileReader.read();
            int CS_End = programFileReader.read();
            int DS_Start = programFileReader.read();
            int DS_End = programFileReader.read();
            int SS_Start = programFileReader.read();
            int SS_End = programFileReader.read();
        } catch (IOException e) {
            System.out.println("Deu bosta");
        }

        return true;
    }
}
