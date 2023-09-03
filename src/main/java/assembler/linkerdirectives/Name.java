package assembler.linkerdirectives;

import assembler.AssembleOperation;
import assembler.Assembler;
import assembler.utils.AssemblerUtils;
import linker.Linker;
import linker.tables.DefinitionsTable;
import linker.tables.UsageTable;

public class Name implements AssembleOperation {
    public static final String MNEMONIC = "NAME";
    @Override
    public void assemble(String line) throws Exception {
        var tokens = AssemblerUtils.decomposeInTokens(line);

        if(tokens.length != 2)
            throw new Exception("Invalid number of arguments for NAME directive");

        // Create tables in Linker for this module
        Linker.getInstance().addDefinitionsTable(tokens[1], new DefinitionsTable());
        Linker.getInstance().addUsageTable(tokens[1], new UsageTable());
        Assembler.getInstance().setModuleName(tokens[1]);
    }
}
