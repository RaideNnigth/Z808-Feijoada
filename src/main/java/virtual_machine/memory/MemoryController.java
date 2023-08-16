package virtual_machine.memory;

import virtual_machine.utils.BinaryUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MemoryController {
    private final Memory mainMemory;

    public static final short CODE_SEGMENT_DEFAULT_START = (short) 0;
    //public static final short DATA_SEGMENT_DEFAULT_START = (short) 32_767;
    public static final short DATA_SEGMENT_DEFAULT_START = (short) 16_383;
    public static final short STACK_DEFAULT_START = (short) (Memory.MEM_SIZE - 1);

    public MemoryController() {
        mainMemory = Memory.getInstance();
    }

    /**
     * Esse método garante que o endereço recebido esteja dentro do
     * intervalo de endereços permitos pelo Z808 [0 - 65.535].
     *
     * @param addr endereço
     * @return endereço dentro do permitido
     */
    private int validAddr(int addr) {
        return Math.abs(addr % Memory.MEM_SIZE);
    }

    /**
     * Get the word at the memory address specified as Little Endian, it considers that the memory is Little Endian
     *
     * @param address
     * @return
     */
    public short getWordLE(short address) {
        return mainMemory.read(Short.toUnsignedInt(address));
    }

    /**
     * Get the word at the memory address specified as Big Endian, it considers that the memory is Little Endian
     *
     * @param address
     * @return
     */
    public short getWordBE(short address) {
        short temp = mainMemory.read(Short.toUnsignedInt(address));
        return BinaryUtils.swapHighAndLowOrder(temp);
    }

    /**
     * Since our data is handled as big-endian internally we have to convert to little-endian to the memory in order
     * to stay correct.
     *
     * @param address
     * @param word
     */
    public void writeWord(short address, short word) {
        mainMemory.write(BinaryUtils.swapHighAndLowOrder(word), Short.toUnsignedInt(address));
    }

    /**
     * Write a file with the data info of the memory.
     */
    public void exportDataMem() {
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter("dataMem.txt"))) {
            for (int i = DATA_SEGMENT_DEFAULT_START; i < Memory.MEM_SIZE; i++) {
                bfw.write("[" + i + " | " + (i - DATA_SEGMENT_DEFAULT_START) + " ] = " + getWordBE((short) i) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Data memory exported to dataMem.txt in location: " + System.getProperty("user.dir"));
    }
}
