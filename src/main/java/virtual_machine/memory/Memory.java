package virtual_machine.memory;

public class Memory {
    //public static final int MEM_SIZE = 65_536;
    public static final int MEM_SIZE = 32_768; // Henrique: I think we should use this one
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
        if (address >= 0 || address < MEM_SIZE)
            this.mainMemory[address] = value;
    }

    public short read(int address) {
        if (address >= 0 || address < MEM_SIZE)
            return this.mainMemory[address];
        return (short) 0x0000;
    }

    public void reset() {
        for (int i = 0; i < MEM_SIZE; i++) {
            this.mainMemory[i] = 0;
        }
    }
}