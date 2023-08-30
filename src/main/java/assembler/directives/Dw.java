package assembler.directives;

import assembler.AssembleOperation;
import assembler.tables.symboltable.Symbol;
import assembler.tables.symboltable.SymbolTable;
import assembler.utils.AssemblerUtils;

public class Dw implements AssembleOperation {
    public static final String MNEMONIC = "DW";
    private final SymbolTable symbolTable;


    public Dw() {
        symbolTable = SymbolTable.getInstance();
    }
    @Override
    public void assemble(String line) {
        String name;
        String[] tokens = AssemblerUtils.decomposeInTokens(line);
        name = tokens[0];

        if (tokens.length > 3 && tokens[3].equals("DUP")) {
            //DW with DUP
            symbolTable.addSymbol(new Symbol(name + "DUP", true, Short.parseShort( tokens[4] )));
        } else {
            //DW normal
            symbolTable.addSymbol(new Symbol(name, true, Short.parseShort( tokens[2] )));
        }
    }
}
