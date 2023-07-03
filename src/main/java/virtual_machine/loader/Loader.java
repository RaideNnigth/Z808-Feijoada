package virtual_machine.loader;

import virtual_machine.memory.MemoryController;
import virtual_machine.utils.BinaryUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Loader {
    private String filePath;
    private long programSize;
    private byte[] programBinary;
    private int PC = 0;
    private MemoryController memoryController;

    public Loader(String path) throws IOException {
        memoryController = new MemoryController();
        setPathToProgram(path);
    }

    public void setPathToProgram(String path) throws IOException {
        filePath = path;

        try (var programFileReader = new FileInputStream(filePath)) {
            programSize = (new File(filePath)).length();
            programBinary = new byte[(int) programSize];
            programFileReader.read(programBinary);
        } catch (IOException e) {
            throw new IOException("Error opening: \"" + filePath + "\" file.");
        }
    }

    /**
     * Esse método retorna um valor de 16 bits lido a partir
     * do byte atual apontado por PC em Little Endian, retornando
     * em Big Endian.
     * A função já deixa PC apontando para a próxima palavra
     * de 16 bits no arquivo.
     *
     * @return Um valor de 16 bits em Big Endian na posição de PC
     */
    private short read16bit() {
        short ret = BinaryUtils.concatBytes(programBinary[PC + 1], programBinary[PC]);
        PC += 2;

        return ret;
    }

    public void loadToMemory() {
        int csStart = 0;
        int csEnd = 0;
        int dsStart = 0;
        int dsEnd = 0;
        int ssStart = 0;
        int ssEnd = 0;

        // Reading header
        csStart = read16bit();
        csEnd = read16bit();
        dsStart = read16bit();
        dsEnd = read16bit();
        ssStart = read16bit();
        ssEnd = read16bit();

        // Print segments addresses (test purposes only)
        System.out.printf("""
                        csStart: %x
                        csEnd: %x
                        dsStart: %x
                        dsEnd: %x
                        ssStart: %x
                        ssEnd: %x
                        """,
                csStart, csEnd,
                dsStart, dsEnd,
                ssStart, ssEnd);

        // Start writing instructions in Code Segment
        PC = csStart;
        int memCounter = 0;
        while (PC <= csEnd) {
            short temp = read16bit();
            //System.out.printf("%04x%n", temp);
            memoryController.writeInstruction(temp, memCounter++);
        }

        // Start writing data in Data Segment
        PC = dsStart;
        memCounter = 0;
        while (PC <= dsEnd) {
            short temp = read16bit();
            //System.out.printf("%04x%n", temp);
            memoryController.writeData(temp, memCounter++);
        }

        // Será q presiza meter o loop da pilha?
        // - Henrique
    }
}
