package assembler.linkerdirectives;

import assembler.AssembleOperation;
import assembler.Assembler;
import assembler.utils.AssemblerUtils;
import linker.Linker;

public class Extrn implements AssembleOperation {
    public static final String MNEMONIC = "EXTRN";

    @Override
    public void assemble(String line) throws Exception {
        if (Assembler.getInstance().isCodeSegmentFound() || Assembler.getInstance().isDataSegmentFound())
            throw new Exception("EXTRN labels have to be declared before segments");

        var tokens = AssemblerUtils.decomposeInTokens(line);

        var usageTable = Linker.getInstance().getUsageTable(Assembler.getInstance().getModuleName());

        for (int i = 1; i < tokens.length; i++) {
            usageTable.addSymbol(tokens[i]);
        }
    }
}
