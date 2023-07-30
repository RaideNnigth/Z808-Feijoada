package assembler.directives;

import assembler.AssembleableOperation;
import assembler.tables.symboltable.SymbolTable;

public class Org implements AssembleableOperation {
    public static final String MNEMONIC = "ORG";
    private final SymbolTable symbolTable;
    public Org() {
        symbolTable = SymbolTable.getInstance();
    }
    @Override
    public void assemble(String line) {

    }
}
