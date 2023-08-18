package assembler.directives;

import assembler.AssembleOperation;
import assembler.tables.symboltable.SymbolTable;

public class Org implements AssembleOperation {
    // Gustavo: We jumped guys from now
    public static final String MNEMONIC = "ORG";
    private final SymbolTable symbolTable;
    public Org() {
        symbolTable = SymbolTable.getInstance();
    }
    @Override
    public void assemble(String line) {

    }
}
