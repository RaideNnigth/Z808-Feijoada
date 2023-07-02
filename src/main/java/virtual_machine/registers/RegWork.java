package virtual_machine.registers;

import virtual_machine.utils.BinaryUtils;

public class RegWork {
    private byte regHigh;
    private byte regLow;

    public byte getRegHigh() {
        return regHigh;
    }

    public byte getRegLow() {
        return regLow;
    }

    public short getReg() {
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
