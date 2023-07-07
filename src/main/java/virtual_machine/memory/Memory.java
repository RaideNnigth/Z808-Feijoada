package virtual_machine.memory;

public class Memory {
    private final short[] mainMemory;
    private final int capacity = 65_536;
    private static Memory memoryInstance = null;

    private Memory() {
        mainMemory = new short[capacity];
        for (int i = 0; i < capacity; i++) {
            this.mainMemory[i] = 0;
        }
    }

    public static Memory getInstance() {
        if (memoryInstance == null)
            memoryInstance = new Memory();

        return memoryInstance;
    }

    public void write(short value, int address) {
        if (address >= 0 || address < capacity)
            this.mainMemory[address] = value;
    }

    public short read(int address) {
        if (address >= 0 || address < capacity)
            return this.mainMemory[address];
        return (short) 0x0000;
    }

    public int getCapacity() {
        return capacity;
    }
}