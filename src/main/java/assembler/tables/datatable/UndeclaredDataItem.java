package assembler.tables.datatable;

import assembler.Assembler;
import assembler.tables.symboltable.Symbol;

public class UndeclaredDataItem extends Exception {
    public UndeclaredDataItem(DataItem d) {
        super("The dataItem \"" + d.getIdentification() + "\" is used but never declared. Line: " + Assembler.getInstance().getLineCounter() + ".");
    }
}
