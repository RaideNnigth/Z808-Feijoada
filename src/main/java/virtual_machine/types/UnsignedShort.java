package virtual_machine.types;

public class UnsignedShort {
    private int value = 0;

    public UnsignedShort(int value) {
        setValue(value);
    }

    public void setValue(int value) {
        if (value < 0)
            this.value = 0;
        else if (value > 65_535)
            this.value = 65_535;
        else
            this.value = value;
    }

    public int getValue() {
        return value;
    }

    public UnsignedByte getHigh() {
        value = value >> 8;

        return new UnsignedByte(value);
    }
    
    public UnsignedByte getLow() {
        value = value & 0b0000000011111111;

        return new UnsignedByte(value);
    }
}
