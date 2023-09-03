package assembler.linkerdirectives;

import assembler.AssembleOperation;
import assembler.Assembler;
import assembler.utils.AssemblerUtils;
import linker.Linker;

public class Extrn implements AssembleOperation {
    public static final String MNEMONIC = "EXTRN";

    @Override
    public void assemble(String line) throws Exception {
        var tokens = AssemblerUtils.decomposeInTokens(line);

        var usageTable = Linker.getInstance().getUsageTable(Assembler.getInstance().getModuleName());

        for (String token : tokens) {
            token = token.split(":")[0];
            usageTable.addSymbol(token);
        }
    }
}
