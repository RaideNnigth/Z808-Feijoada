package macroprocessor.utils;

import assembler.tables.CodeTable;

public class MacroUtils {
    private static final String macroRegex = "[a-zA-Z][a-zA-Z0-9]*";

    public static boolean isValidMacroName(String line) {
        if (!line.matches(macroRegex)) {
            return false;
        }

        line = line.substring(0, line.length() - 2);

        // Check if name is reserved word (like instruction name)
        for (String s : CodeTable.getInstance().getKeys()) {
            if (line.equals(s))
                return false;
        }

        return true;
    }

    public static String renameNestedMacro(String parentName, String nestedName) {
        return parentName + "#" + nestedName;
    }
}
