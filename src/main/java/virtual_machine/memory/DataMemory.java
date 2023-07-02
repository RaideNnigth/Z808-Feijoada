package virtual_machine.memory;

import virtual_machine.types.UnsignedShort;

public interface DataMemory {
    public Memory getInstance();
    public void write(UnsignedShort value, UnsignedShort address);
    public UnsignedShort read(UnsignedShort address);
}
