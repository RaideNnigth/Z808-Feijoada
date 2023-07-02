package virtual_machine.registers;

import virtual_machine.types.UnsignedByte;
import virtual_machine.types.UnsignedShort;

public class RegWork {
    private UnsignedByte regHigh;
    private UnsignedByte regLow;

    public int getRegHigh() {
        return regHigh.getValue();
    }

    public int getRegLow() {
        return regLow.getValue();
    }

    public int getReg() {
        return UnsignedByte.concatenateBytes( this.regHigh.getValue(), this.regLow.getValue() );
    }

    public void setRegHigh( int regHigh ) {
        this.regHigh.setValue( regHigh );
    }

    public void setRegLow( int regLow ) {
        this.regLow.setValue( regLow );
    }

    public void setReg( UnsignedShort reg ) {
        this.regHigh = reg.getHigh();
        this.regLow = reg.getLow();
    }
}
