package assembler.codeprocessors;

import assembler.Assembler;
import assembler.tables.macrotable.Macro;
import assembler.tables.macrotable.MacroTable;
import assembler.tables.symboltable.SymbolTable;
import assembler.utils.AssemblerUtils;
import java.util.Arrays;

public class MacroProcessor {

    private MacroTable macroTable;

    public MacroProcessor () {
        MacroTable.getInstance();
    }

    public boolean assembleMacro(String line) {

        // Line does not contain macro
        if (!AssemblerUtils.isValidMacro(line))
            return false;

        // All macro tokens
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        // Macro name
        String macroName = tokens[0];

        // Macro parameters
        String[] macroParams = Arrays.copyOfRange(tokens, 2, tokens.length);

        // Macro being added to the macro table
        Macro macro = new Macro(macroName, macroParams, Assembler.getInstance().getLineCounter());

        /*
        if (Assembler.getInstance().getPC() == 0)
            s = new Symbol(line, true, (short) 0);
        else
            // Value is PC + 1 'cause Labels will point to the next instruction always
            s = new Symbol(line, true, (short) (Assembler.getInstance().getPC() + 1));
         */

        SymbolTable.getInstance().addSymbol(s);
        return true;
    }
}
