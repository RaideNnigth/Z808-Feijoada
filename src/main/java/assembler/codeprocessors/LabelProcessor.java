package assembler.codeprocessors;

import assembler.Assembler;
import assembler.tables.symboltable.Symbol;
import assembler.tables.symboltable.SymbolTable;

public class LabelProcessor {
    public boolean assembleLabel(String line) {
        String label = line.split(":")[0];

        // Line doesn't contains label
        if (label.isEmpty())
            return false;

        // Value is PC + 1 'cause Labels will point to the next instruction always
        Symbol s = new Symbol(label, true, (short) (Assembler.getInstance().getPC() + 1));
        SymbolTable.getInstance().addSymbol(s);

        return true;
    }
}
