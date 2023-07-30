package assembler.directives;

import assembler.AssembleableOperation;
import assembler.tables.symboltable.Symbol;
import assembler.tables.symboltable.SymbolTable;
import assembler.utils.AssemblerUtils;

public class Segment implements AssembleableOperation {
    public static final String MNEMONIC = "SEGMENT";
    private final SymbolTable symbolTable;
    private String name;
    public Segment() {
        symbolTable = SymbolTable.getInstance();
    }
    @Override
    public void assemble( String line ) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);
        this.name = tokens[0];
        symbolTable.addSymbol(new Symbol(this.name, true, (short)0));
    }
}
