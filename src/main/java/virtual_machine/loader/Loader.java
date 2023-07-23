package virtual_machine.loader;

import virtual_machine.interpreter.Interpreter;
import virtual_machine.memory.MemoryController;
import virtual_machine.utils.BinaryUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Loader {
    private String pathToProgram;
    private long programSize;
    private byte[] programBinary;
    private int PC = 0;

    public Loader() {

    }

    public void setProgramToLoad(String path) throws IOException {
        pathToProgram = path;

        try (var programFileReader = new FileInputStream(pathToProgram)) {
            programSize = (new File(pathToProgram)).length();
            programBinary = new byte[(int) programSize];
            programFileReader.read(programBinary);
        } catch (IOException e) {
            throw new IOException("Error opening: \"" + pathToProgram + "\" file.");
        }
    }

    /**
     * Esse método retorna um valor de 16 bits lido a partir
     * do byte em Little Endian apontado por PC, retornando
     * em Little Endian também.
     * A função já deixa PC apontando para a próxima palavra
     * de 16 bits no arquivo.
     *
     * @return Um valor de 16 bits em Little Endian na posição de PC
     */
    private short read16bitLE() {
        short ret = BinaryUtils.concatBytes(programBinary[PC], programBinary[PC + 1]);
        PC += 2;

        return ret;
    }

    /**
     * Esse método retorna um valor de 16 bits lido a partir
     * do byte em Little Endian apontado por PC, retornando
     * em Big Endian.
     * A função já deixa PC apontando para a próxima palavra
     * de 16 bits no arquivo.
     *
     * @return Um valor de 16 bits em Big Endian na posição de PC
     */
    private short read16bitBE() {
        short ret = BinaryUtils.concatBytes(programBinary[PC + 1], programBinary[PC]);
        PC += 2;

        return ret;
    }

    public void loadToMemory(MemoryController memoryController, int codeSegment, int dataSegment, int stackSegment) {
        int csStart = 0;
        int csEnd = 0;
        int dsStart = 0;
        int dsEnd = 0;
        int ssStart = 0;
        int ssEnd = 0;

        // Reading header
        csStart = read16bitBE();
        csEnd = read16bitBE();
        dsStart = read16bitBE();
        dsEnd = read16bitBE();
        ssStart = read16bitBE();
        ssEnd = read16bitBE();

        // Start writing instructions in Code Segment
        PC = csStart;
        int memCounter = 0;
        while (PC <= csEnd) {
            short temp = read16bitBE();
            memoryController.writeWord((short) ((memCounter++) + codeSegment), temp);
        }

        //System.out.println("Data loaded to memory: ");

        // Start writing data in Data Segment
        PC = dsStart;
        memCounter = 0;
        while (PC <= dsEnd) {
            short temp = read16bitBE();
            memoryController.writeWord((short) ((memCounter++) + dataSegment), temp);
        }

        PC = ssStart;
        memCounter = 0;
        while (PC <= ssEnd) {
            short temp = read16bitBE();
            memoryController.writeWord((short) ((memCounter++) + stackSegment), temp);
        }

    }
}
