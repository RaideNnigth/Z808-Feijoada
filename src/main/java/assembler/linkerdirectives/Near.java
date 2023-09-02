package assembler.linkerdirectives;

import assembler.LinkerOperation;
import assembler.utils.AssemblerUtils;
import linker.tables.GlobalSymbolTable;

public class Near implements LinkerOperation {
    public static final String MNEMONIC = "NEAR";
    private final GlobalSymbolTable globalSymbolTable;


    public Near() {
        globalSymbolTable = GlobalSymbolTable.getInstance();
    }

    @Override
    public void link(String line) {
        String name;
        String[] tokens = AssemblerUtils.decomposeInTokens(line);
        name = tokens[2];

        if (tokens.length > 3 && tokens[3].equals("NEAR")) {

        }
    }
}

