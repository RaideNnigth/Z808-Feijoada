package virtual_machine.memory;

public class MemoryController {
    private Memory mainMemory;
    private int codeSegment;
    private int dataSegment;
    private int stackSegment;

    private static final int standardCodeSegment = 0;
    private static final int standardDataSegment = 1001;
    private static final int standardStackSegment = 65_000;

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

    public void resetSegments() {
        codeSegment = standardCodeSegment;
        dataSegment = standardDataSegment;
        stackSegment = standardStackSegment;
    }
}
