package assembler.tables.datatable;

import assembler.tables.symboltable.Symbol;

public class UndeclaredDataItem extends Exception {
    public UndeclaredDataItem(DataItem d) {
        super("The dataItem \"" + d.getIdentification() + "\" is used but never declared!");
    }
}
