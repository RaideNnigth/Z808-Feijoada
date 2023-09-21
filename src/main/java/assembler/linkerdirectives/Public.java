package assembler.linkerdirectives;

import assembler.AssembleOperation;
import assembler.Assembler;
import assembler.utils.AssemblerUtils;
import linker.Linker;
import linker.entities.LinkerSymbol;
import linker.tables.DefinitionsTable;

public class Public implements AssembleOperation {
    public static final String MNEMONIC = "PUBLIC";
    @Override
    public void assemble(String line) throws Exception {
        if (Assembler.getInstance().isCodeSegmentFound() || Assembler.getInstance().isDataSegmentFound())
            throw new Exception("PUBLIC labels have to be declared before segments");

        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        // Get definitions table from linker
        DefinitionsTable definitionsTable = Linker.getInstance().getDefinitionsTable(Assembler.getInstance().getModuleName());

        // Adding all public symbos to definitions table
        for (int i = 1; i < tokens.length; i++) {
            definitionsTable.addDefinition(tokens[i], new LinkerSymbol());

            // We need to add the correct value later!!!
            // 0 is just a placeholder
        }
    }
}