package assembler.codeprocessors;

import assembler.Assembler;
import assembler.tables.symboltable.Symbol;
import assembler.tables.symboltable.SymbolTable;
import assembler.utils.AssemblerUtils;
import linker.Linker;
import linker.entities.LinkerSymbol;

public class LabelProcessor {
    public boolean assembleLabel(String line) {
        // Line doesn't contains label
        if (!AssemblerUtils.isValidLabel(line))
            return false;

        // Remove ':' from label
        String labelName = line.substring(0, line.length() - 1);

        Symbol s = new Symbol(labelName, true, (short) Assembler.getInstance().getAssembledCode().size());
        SymbolTable.getInstance().addSymbol(s);

        // Check if label is a Public variable
        var moduleName = Assembler.getInstance().getModuleName();
        var definitionTable = Linker.getInstance().getDefinitionsTable(moduleName);

        if (definitionTable.containsSymbol(labelName)) {
            var publicSymbol = definitionTable.getSymbol(labelName);

            publicSymbol.setValue(s.getValue());
            publicSymbol.setModuleName(moduleName);
            publicSymbol.setRelocability(LinkerSymbol.RELOCABILITY.R);
        }

        return true;
    }
}
