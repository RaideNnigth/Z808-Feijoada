package virtual_machine.memory;

public class Memory {
    public static final int MEM_SIZE = 65_536;
    private final short[] mainMemory;
    private static Memory memoryInstance = null;

    private Memory() {
        mainMemory = new short[MEM_SIZE];
        for (int i = 0; i < MEM_SIZE; i++) {
            this.mainMemory[i] = 0;
        }
    }

    public static Memory getInstance() {
        if (memoryInstance == null)
            memoryInstance = new Memory();

        return memoryInstance;
    }

    public void write(short value, int address) {
        this.mainMemory[address] = value;
    }

    public short read(int address) {
        return this.mainMemory[address];
    }
}