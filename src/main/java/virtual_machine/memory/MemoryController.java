package virtual_machine.memory;

import virtual_machine.utils.BinaryUtils;

import java.util.ArrayList;

public class MemoryController {
    private final Memory mainMemory;

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
     * @param address
     * @return
     */
    public short getWordLE(short address) {
        return mainMemory.read(Short.toUnsignedInt(address));
    }

    /**
     * Get the word at the memory address specified as Big Endian, it considers that the memory is Little Endian
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
     * @param word
     * @param address
     */
    public void writeWord(short word, short address) {
        mainMemory.write(BinaryUtils.swapHighAndLowOrder(word), Short.toUnsignedInt(address));
    }
}
