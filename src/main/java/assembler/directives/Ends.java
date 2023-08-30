package assembler.directives;

import assembler.AssembleOperation;
import assembler.tables.symboltable.Symbol;
import assembler.tables.symboltable.SymbolTable;
import assembler.utils.AssemblerUtils;

public class Ends implements AssembleOperation {
    public static final String MNEMONIC = "ENDS";
    private final SymbolTable symbolTable;

    public Ends() {
        symbolTable = SymbolTable.getInstance();
    }
    @Override
    public void assemble( String line ) {
        String name;
        String[] tokens = AssemblerUtils.decomposeInTokens(line);
        name = tokens[0];
        symbolTable.addSymbol(new Symbol(name, true, (short)0));
    }
}
