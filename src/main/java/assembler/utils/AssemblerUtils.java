package assembler.utils;
import assembler.tables.CodeTable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class AssemblerUtils {
    private static final String nameRegex = "[A-Z@$_][A-Z@$_0-9]*";
    private static final String labelRegex = nameRegex + ":";
    private static final String macroRegex = nameRegex;

    public static String[] decomposeInTokens(String line) {
        // Check if it's an array declaration
        if(line.contains("{")) {
            if(!line.contains("}")) {
                // Array opened but never closed
                throw new IllegalArgumentException("Invalid array declaration: " + line);
            }

            int splitInd = line.indexOf('{');
            String firstHalf = line.substring(0, splitInd);
            String secondHalf = line.substring(splitInd + 1, line.length() - 1);

            // Remove all spaces from the first half
            LinkedList<String> tokens = new LinkedList<>(Arrays.asList(firstHalf.split("\\s*,\\s*|\\s+")));
            tokens.add(secondHalf.trim());

            return tokens.toArray(new String[0]);
        }
        return line.split("\\s*,\\s*|\\s+");
    }

    public static boolean isNumericConstant(String element) {
        return element.matches("[-+]?[0-9]+[BH]?");
    }

    public static short convertNumber(String number) {
        // Binary number
        if (number.matches("[0-1]+B")) {
            return (short) Integer.parseInt(number.substring(0, number.length() - 1), 2);
        }
        // Hex number
        else if (number.matches("[+-]?[0-9A-F]+H")) {
            return (short) Integer.parseInt(number.substring(0, number.length() - 1), 16);
        }
        // Decimal number
        return (short) Integer.parseInt(number);
    }

    public static boolean isPowerOf2(int num) {
        if (num <= 0) {
            return false;
        }

        return (num & (num - 1)) == 0;
    }

    public static int roundUpToClosestPower2(int num) {
        if (isPowerOf2(num)) {
            return num;
        }

        int pow = Integer.SIZE - Integer.numberOfLeadingZeros(num);

        return 1 << pow;
    }

    public static ArrayList<Byte> parseDWToByteArrayList(String number) {
        int num;

        // Binary number
        if (number.matches("[0-1]+B")) {
            num = Integer.parseInt(number.substring(0, number.length() - 1), 2);
        }
        // Hex number
        else if (number.matches("[+-]?[0-9A-F]+H")) {
            num = Integer.parseInt(number.substring(0, number.length() - 1), 16);
        }
        // Decimal number
        else
            num = Integer.parseInt(number);

        // Creating list breaking the number in bytes
        ArrayList<Byte> bytes = new ArrayList<>(2);
        bytes.add((byte) (num >>> 8));
        bytes.add((byte) num);

        return bytes;
    }

    public static ArrayList<Byte> parseDDToByteArrayList(String number) {
        int num;

        // Binary number
        if (number.matches("[0-1]+B")) {
            num = Integer.parseInt(number.substring(0, number.length() - 1), 2);
        }
        // Hex number
        else if (number.matches("[+-]?[0-9A-F]+H")) {
            num = Integer.parseInt(number.substring(0, number.length() - 1), 16);
        }
        // Decimal number
        else
            num = Integer.parseInt(number);

        // Creating list breaking the number in bytes
        ArrayList<Byte> bytes = new ArrayList<>(4);
        bytes.add((byte) (num  >>> 24));
        bytes.add((byte) (num >>> 16));
        bytes.add((byte) (num >>> 8));
        bytes.add((byte) num);

        return bytes;
    }

    public static ArrayList<Byte> toByteArrayList(byte[] bArr) {
        ArrayList<Byte> bytes = new ArrayList<>(bArr.length);
        for (byte b : bArr) {
            bytes.add(b);
        }
        return bytes;
    }

    public static boolean isValidName(String name) {
        if (!name.matches(nameRegex)) {
            return false;
        }

        // Check if name is a invalid word
        for (String s : CodeTable.getInstance().getKeys()) {
            if (name.equals(s))
                return false;
        }

        return true;
    }

    public static boolean isValidLabel(String line) {
        if (!line.matches(labelRegex)) {
            return false;
        }

        line = line.substring(0, line.length() - 2);

        // Check if name is a invalid word
        for (String s : CodeTable.getInstance().getKeys()) {
            if (line.equals(s))
                return false;
        }

        return true;
    }

    public static boolean isValidMacro(String macroName) {
        if (!macroName.matches(macroRegex)) {
            return false;
        }

        // Check if name is invalid word
        for (String s : CodeTable.getInstance().getKeys()) {
            if (macroName.equals(s))
                return false;
        }

        return true;
    }
}
