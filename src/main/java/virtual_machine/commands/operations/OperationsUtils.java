package virtual_machine.commands.operations;

public abstract class OperationsUtils {
    public static boolean hasCarry(int op1, int op2) {
        return (op1 & op2) != 0;
    }

    public static boolean hasOverflow16(int result) {
        if (result <= 32_767)
            return false;
        if (result >= -32_768)
            return false;
        return true;
    }

    public static boolean hasOverflow32(long result) {
        if (result <= 2_147_483_647)
            return false;
        if (result >= -2_147_483_648)
            return false;
        return true;
    }

    public static boolean isZero(int result) {
        if (result == 0)
            return true;
        return false;
    }

    public static boolean hasSignal(int result) {
        if (result < 0)
            return true;
        return false;
    }

    public static boolean parityBit(int result) {
        int counter = 0;
        for (int i = 0; i < 16; i++) {
            if (((result >>> i) & 0x0001) == 1)
                counter++;
        }

        if (counter % 2 == 0)
            return true;

        return false;
    }

}
