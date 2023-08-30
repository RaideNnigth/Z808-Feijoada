package assembler.directives;

import assembler.AssembleOperation;
import assembler.tables.symboltable.Symbol;
import assembler.tables.symboltable.SymbolTable;
import assembler.utils.AssemblerUtils;

public class Equ implements AssembleOperation {
    public static final String MNEMONIC = "EQU";
    private final SymbolTable symbolTable;
    public Equ() {
        symbolTable = SymbolTable.getInstance();
    }

    @Override
    public void assemble(String line) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);
        String name = tokens[0];
        StringBuilder value = new StringBuilder();
        for (int i = 2; i < tokens.length; i++) {
            value.append( tokens[i] ).append( " " );
        }
        symbolTable.addSymbol(new Symbol(name, true, Short.parseShort( value.toString() )));
    }
}
