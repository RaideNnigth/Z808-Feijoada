package virtual_machine.memory;

import virtual_machine.types.UnsignedShort;

public interface InstructionMemory {
    public Memory getInstance();
    public void write(UnsignedShort value, UnsignedShort address);
    public UnsignedShort read(UnsignedShort address);
}
