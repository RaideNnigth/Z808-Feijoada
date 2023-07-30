package assembler.utils;

import assembler.tables.CodeTable;

public class AssemblerUtils {
    public static String[] decomposeInTokens(String line) {
        return line.split("[ ,]");
    }

    public static boolean isNumericConstant(String element) {
        return element.matches("[0-9]+[bh]?");
    }

    public static short convertNumber(String number) {
        // Binary number
        if (number.matches("[0-1]+b")) {
            return (short) Integer.parseInt(number.substring(0, number.length() - 1), 2);
        }
        // Hex number
        else if (number.matches("[0-9A-F]+h")) {
            return (short) Integer.parseInt(number.substring(0, number.length() - 1), 16);
        }
        // Decimal number
        return (short) Integer.parseInt(number);
    }

    public static boolean isValidName(String name) {
        // Check if name is a invalid word
        for(String s : CodeTable.getInstance().getKeys()) {
            if(name.equals(s))
                return false;
        }

        return name.matches("[A-Z@$_][A-Z@$_0-9]*");
    }
}
