package assembler.codeprocessors;

import assembler.Assembler;
import assembler.tables.symboltable.Symbol;

public class LabelProcessor {
    public LabelProcessor() {

    }

    public void assembleLabel(String line) {
        String label = line.split(":")[0];

        // Line doesn't contains label
        if (label.equals(""))
            return;

        Symbol symbol = new Symbol(label, true, (short) Assembler.getInstance().getPC());
        Assembler.getInstance().getSymbolTable().put(label, symbol);
    }
}
