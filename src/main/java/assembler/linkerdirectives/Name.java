package assembler.linkerdirectives;

import assembler.AssembleOperation;
import assembler.utils.AssemblerUtils;
import linker.Linker;
import linker.tables.DefinitionsTable;

public class Name implements AssembleOperation {
    public static final String MNEMONIC = "NAME";
    @Override
    public void assemble(String line) throws Exception {
        var tokens = AssemblerUtils.decomposeInTokens(line);

        if(tokens.length != 2)
            throw new Exception("Invalid number of arguments for NAME directive");

        Linker.getInstance().addDefinitionsTable(tokens[1], new DefinitionsTable());
    }
}
