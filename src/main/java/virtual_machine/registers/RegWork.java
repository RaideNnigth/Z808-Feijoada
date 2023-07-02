package virtual_machine.registers;

import virtual_machine.types.BinaryUtils;

public class RegWork {
    private byte regHigh;
    private byte regLow;

    public int getRegHigh() {
        return regHigh;
    }

    public int getRegLow() {
        return regLow;
    }

    public int getReg() {
        return BinaryUtils.concatBytes(regHigh, regLow);
    }

    public void setRegHigh( int regHigh ) {
        this.regHigh = (byte) regHigh;
    }

    public void setRegLow( int regLow ) {
        this.regLow = (byte) regLow;
    }

    public void setReg( short reg ) {
        this.regHigh = (byte)(reg >> 8);
        this.regLow = (byte)(reg & 0xFF);
    }
}
