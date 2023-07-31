package assembler.directives;

import assembler.AssembleableOperation;
import assembler.tables.symboltable.SymbolTable;

public class Offset implements AssembleableOperation {
    // Gustavo: We jumped guys from now
    public static final String MNEMONIC = "OFFSET";
    private final SymbolTable symbolTable;

    public Offset() {
        symbolTable = SymbolTable.getInstance();
    }
    @Override
    public void assemble(String line) {

    }
}
