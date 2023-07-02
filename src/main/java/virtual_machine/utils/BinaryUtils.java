package virtual_machine.utils;

public class BinaryUtils {
    public static final short maxShort = Short.MAX_VALUE;
    public static final short minShort = Short.MIN_VALUE;
    public static final byte minByte = Byte.MIN_VALUE;
    public static final byte maxByte = Byte.MAX_VALUE;

    public static short concatBytes(byte b1, byte b2) {
        int num1 = b1;

        num1 = num1 << 8;

        return (short) (num1 | b2);
    }

    public static int concatShorts(short s1, short s2) {
        int num1 = s1;
        num1 = num1 << 16;

        return (num1 | s2);
    }

    public static byte getLowByte(short b) {
        return (byte) (b & 0xFF);
    }

    public static byte getHighByte(short b) {
        return (byte) (b >> 8);
    }
}
