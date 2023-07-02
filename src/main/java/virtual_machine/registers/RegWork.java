package virtual_machine.registers;

import virtual_machine.types.UnsignedByte;
import virtual_machine.types.UnsignedShort;

public class RegWork {
    private UnsignedByte regHigh;
    private UnsignedByte regLow;

    public UnsignedByte getRegHigh() {
        return regHigh;
    }
    public UnsignedByte getRegLow() {
        return regLow;
    }
    public void setRegHigh(UnsignedByte regHigh) {
        this.regHigh = regHigh;
    }
    public void setRegLow(UnsignedByte regLow) {
        this.regLow = regLow;
    }
    public void setReg(UnsignedShort reg) {
        this.regHigh = reg.getHigh();
        this.regLow = reg.getLow();
    }
}
