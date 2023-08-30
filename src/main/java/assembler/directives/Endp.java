package assembler.directives;

import assembler.AssembleOperation;
import assembler.tables.symboltable.Symbol;
import assembler.tables.symboltable.SymbolTable;
import assembler.utils.AssemblerUtils;

public class Endp implements AssembleOperation {
    public static final String MNEMONIC = "ENDP";
    private final SymbolTable symbolTable;
    public Endp() {
        symbolTable = SymbolTable.getInstance();
    }
    public void assemble( String line ) {
        String name;
        String[] tokens = AssemblerUtils.decomposeInTokens(line);
        name = tokens[0];
        symbolTable.addSymbol(new Symbol(name, true, (short)0));
    }
}
