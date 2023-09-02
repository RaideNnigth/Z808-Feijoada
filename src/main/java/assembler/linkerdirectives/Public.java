package assembler.linkerdirectives;

import assembler.AssembleOperation;
import assembler.utils.AssemblerUtils;

public class Public implements AssembleOperation {
    public static final String MNEMONIC = "PUBLIC";
    @Override
    public void assemble(String line) throws Exception {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        for (int i = 1; i < tokens.length; i++) {
            var
        }
    }
}
