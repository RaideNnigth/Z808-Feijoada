package virtual_machine.memory;
import virtual_machine.types.UnsignedShort;

public class Memory implements InstructionMemory, DataMemory {
    private final UnsignedShort[] mainMemory = new UnsignedShort[65_536];
    public Memory() {
        for (int i = 0; i < 65_536; i++) {
            this.mainMemory[i] = new UnsignedShort(0);
        }
    }
    @Override
    public Memory getInstance() {
        return this;
    }

    @Override
    public void write(UnsignedShort value, UnsignedShort address) {
        this.mainMemory[address.getValue()] = value;
    }

    @Override
    public UnsignedShort read(UnsignedShort address) {
        return this.mainMemory[address.getValue()];
    }
}