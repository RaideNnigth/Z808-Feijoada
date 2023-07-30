package assembler.directives;

import assembler.AssembleableOperation;
import assembler.tables.symboltable.Symbol;
import assembler.tables.symboltable.SymbolTable;
import assembler.utils.AssemblerUtils;

public class Segment implements AssembleableOperation {
    public static final String MNEMONIC = "SEGMENT";
    private final SymbolTable symbolTable;

    public Segment() {
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
