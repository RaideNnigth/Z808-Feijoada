package virtual_machine.memory;

import virtual_machine.types.UnsignedShort;

public class Memory {
    private final UnsignedShort[] mainMemory;
    private static Memory memInstance = null;

    private Memory() {
        mainMemory = new UnsignedShort[65_536];
        for (int i = 0; i < 65_536; i++) {
            this.mainMemory[i] = new UnsignedShort(0);
        }
    }

    public static Memory getInstance() {
        if (memInstance == null)
            memInstance = new Memory();

        return memInstance;
    }

    public void write(UnsignedShort value, UnsignedShort address) {
        this.mainMemory[address.getValue()] = value;
    }

    public UnsignedShort read(UnsignedShort address) {
        return this.mainMemory[address.getValue()];
    }
}