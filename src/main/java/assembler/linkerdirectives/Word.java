package assembler.linkerdirectives;

import assembler.LinkerOperation;
import linker.tables.GlobalSymbolTable;

public class Word implements LinkerOperation {
    public static final String MNEMONIC = "Near";
    private final GlobalSymbolTable globalSymbolTable;


    public Word() {
        globalSymbolTable = GlobalSymbolTable.getInstance();
    }

    @Override
    public void link(String line) {

    }
}
