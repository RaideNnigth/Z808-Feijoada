package main;

public class UnsignedByte {
    private int value = 0;

    public UnsignedByte(int value) {
        setValue(value);
    }

    public void setValue(int value) {
        if (value < 0)
            this.value = 0;
        else if (value > 255)
            this.value = 255;
        else
            this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static int concatenateBytes(int byte1, int byte2) {
        byte1 = byte1 << 8;
        int result = byte1 | byte2;

        return result;
    }
}
