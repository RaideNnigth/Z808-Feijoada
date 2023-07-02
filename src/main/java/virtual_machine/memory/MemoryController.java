package virtual_machine.memory;
import virtual_machine.utils.UnsignedShort;

public class MemoryController {
    private Memory mainMemory;
    private short codeSegment;
    private short dataSegment;
    private short stackSegment;

    public short getInstruction(short addAddress) {
        return mainMemory.read((short) (addAddress + codeSegment));
    }

    public void writeInstruction(short value, short addAddress) {
        mainMemory.write(value, (short) (addAddress + codeSegment));
    }

    public short getData(short addAddress) {
        return mainMemory.read((short) (addAddress + dataSegment));
    }

    public void writeData(short value, short addAddress) {
        mainMemory.write(value, (short) (addAddress + dataSegment));
    }

    public void resetSegments() {
    }
}
