package assembler.utils;

import assembler.tables.CodeTable;

import java.util.Arrays;

public class AssemblerUtils {
    private static final String nameRegex = "[A-Z@$_][A-Z@$_0-9]*";
    private static final String labelRegex = nameRegex + ":";

    public static String[] decomposeInTokens(String line) {
        return line.split("\\s*,\\s*|\\s+");
    }

    public static boolean isNumericConstant(String element) {
        return element.matches("[-+]?[0-9]+[bh]?");
    }

    public static short convertNumber(String number) {
        // Binary number
        if (number.matches("[0-1]+b")) {
            return (short) Integer.parseInt(number.substring(0, number.length() - 1), 2);
        }
        // Hex number
        else if (number.matches("[+-]?[0-9A-F]+h")) {
            return (short) Integer.parseInt(number.substring(0, number.length() - 1), 16);
        }
        // Decimal number
        return (short) Integer.parseInt(number);
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
}
