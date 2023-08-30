package assembler.directives;

import assembler.AssembleOperation;
import assembler.tables.symboltable.Symbol;
import assembler.tables.symboltable.SymbolTable;
import assembler.utils.AssemblerUtils;

public class Assume implements AssembleOperation {
    public static final String MNEMONIC = "ASSUME";
    private final SymbolTable symbolTable;
    public Assume() {
        symbolTable = SymbolTable.getInstance();
    }
    @Override
    public void assemble(String line) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);
        String[] values = tokens[1].split(";");
        if (values[0].equals( "CS" ) || values[0].equals( "DS" ) || values[0].equals( "SS" )
                || values[0].equals( "DS_END" ) || values[0].equals( "SS_END" ) ) {
            symbolTable.addSymbol(new Symbol(tokens[1], true, (short)0));
        }
    }
}
