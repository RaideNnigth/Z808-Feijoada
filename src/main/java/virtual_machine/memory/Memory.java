package virtual_machine.memory;

public class Memory {
    private final short[] mainMemory;
    private static Memory memInstance = null;

    private Memory() {
        mainMemory = new short[65_536];
        for (int i = 0; i < 65_536; i++) {
            this.mainMemory[i] = 0;
        }
    }

    public static Memory getInstance() {
        if (memInstance == null)
            memInstance = new Memory();

        return memInstance;
    }

    public void write(short value, short address) {
        this.mainMemory[address] = value;
    }

    public short read(short address) {
        return this.mainMemory[address];
    }
}