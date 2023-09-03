package assembler.linkerdirectives;

import assembler.AssembleOperation;
import assembler.Assembler;
import assembler.utils.AssemblerUtils;
import linker.Linker;
import linker.entities.Symbol;

public class Public implements AssembleOperation {
    public static final String MNEMONIC = "PUBLIC";
    @Override
    public void assemble(String line) throws Exception {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        // Get definitions table from linker
        var definitionsTable = Linker.getInstance().getDefinitionsTable(Assembler.getInstance().getModuleName());

        for (int i = 1; i < tokens.length; i++) {
            definitionsTable.addDefinition(tokens[i], new Symbol(tokens[i], (short)0));

            // We need to add the correct value later!!!
            // 0 is just a placeholder
        }
    }
}