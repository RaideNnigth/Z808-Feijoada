package virtual_machine.memory;

public class MemoryController {
    private Memory mainMemory;
    private int codeSegment;
    private int dataSegment;
    private int stackSegment;

    public static final int standardCodeSegment = 0;
    public static final int standardDataSegment = 1001;
    public static final int standardStackSegment = 65_000;

    public short getInstruction(int addAddress) {
        return mainMemory.read((short) (addAddress + codeSegment));
    }

    public void writeInstruction(short value, int addAddress) {
        mainMemory.write(value, (short) (addAddress + codeSegment));
    }

    public short getData(int addAddress) {
        return mainMemory.read((short) (addAddress + dataSegment));
    }

    public void writeData(short value, int addAddress) {
        mainMemory.write(value, (short) (addAddress + dataSegment));
    }

    public void resetSegments() {
        codeSegment = standardCodeSegment;
        dataSegment = standardDataSegment;
        stackSegment = standardStackSegment;
    }
}
