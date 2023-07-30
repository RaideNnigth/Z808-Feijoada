package assembler.codeprocessors;

import assembler.Assembler;
import assembler.tables.symboltable.Symbol;
import assembler.tables.symboltable.SymbolTable;
import assembler.utils.AssemblerUtils;

public class LabelProcessor {
    public boolean assembleLabel(String line) {
        // Line doesn't contains label
        if (!AssemblerUtils.isValidLabel(line))
            return false;

        line = line.substring(0, line.length() - 1);
        Symbol s;

        s = new Symbol(line, true, (short) Assembler.getInstance().getAssembledCode().size());

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
