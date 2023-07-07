package virtual_machine.memory;

public class MemoryController {
    private final Memory mainMemory;
    private int codeSegment;
    private int dataSegment;
    private int stackSegment;

    public final int standardCodeSegment = 0;
    public final int standardDataSegment = 1001;
    public final int standardStackSegment = 65_000;

    public MemoryController() {
        mainMemory = Memory.getInstance();
        codeSegment = standardCodeSegment;
        dataSegment = standardDataSegment;
        stackSegment = standardStackSegment;
    }

    public short getWord(short address) {
        return mainMemory.read(Short.toUnsignedInt(address));
    }

    public void writeWord(short address, short word) {
        mainMemory.write(word, Short.toUnsignedInt(address));
    }

    public int getCapacity() {
        return mainMemory.getCapacity();
    }

    public short getInstruction(int addAddress) {
        return mainMemory.read((short) (addAddress + codeSegment));
    }

    public void writeInstruction(short value, int address) {
        mainMemory.write(value, (short) (address + codeSegment));
    }

    public short getData(int address) {
        return mainMemory.read((short) (address + dataSegment));
    }

    public void writeData(short value, int address) {
        mainMemory.write(value, (short) (address + dataSegment));
    }

    /**
     * Pop from stack, it also verifies if the given address is a valid stack address
     * @param address
     * @return data at that address
     */
    public short getStack(int address) {
        if (address < stackSegment)
            return 0x0000;

        short value = mainMemory.read((short) (address));
        mainMemory.write((short) 0x0000, (short) (address));
        return value;
    }

    /**
     * Push to stack, it also verifies if the given address is a valid stack address
     * @param value
     * @param address
     */
    public void writeStack(short value, int address) {
        if (address < stackSegment)
            return;

        mainMemory.write(value, (short) (address));
    }

    public void resetSegments() {
        codeSegment = standardCodeSegment;
        dataSegment = standardDataSegment;
        stackSegment = standardStackSegment;
    }
}
