package assembler.tables.macrotable;

import assembler.Assembler;
import assembler.Log;
import assembler.LogType;
import assembler.tables.symboltable.Symbol;
import assembler.tables.symboltable.UndeclaredSymbol;
import assembler.utils.AssemblerUtils;

import java.util.HashMap;

//Singleton class
public class MacroTable {
    private HashMap<String, Macro> macroTableDefinitions = new HashMap<>();

    private static MacroTable instance = null;
    private MacroTable() {}

    public MacroTable getInstance() {
        if (instance == null) {
            instance = new MacroTable();
        }

        return instance;
    }

    public HashMap<String, Macro> getMacroTableDefinitions() {
        return macroTableDefinitions;
    }

    public boolean macroExists(String macro) {
        return macroTableDefinitions.containsKey(macro);
    }

    public Macro getMacro(String macro) {
        return macroTableDefinitions.get(macro);
    }

    public void addOccurrenceOfMacro(String macro) {
        var macroObj = macroTableDefinitions.get(macro);
        var assembledCode = Assembler.getInstance().getAssembledCode();

        macro.getUsedAt().add(assembledCode.size());
        assembledCode.add((short) 0);
    }

    public void addSymbol(Symbol s) {
        if (!AssemblerUtils.isValidName(s.getIdentification())) {
            Log l = new Log(LogType.ERROR, Assembler.getInstance().getLineCounter(),
                    String.format("%s is not a valid identifier.", s.getIdentification()));
            Assembler.getInstance().getLogger().addLog(l);
        }

        macroTableDefinitions.put(s.getIdentification(), s);
    }

    public void replaceAllOcorrencesOfDeclaredSymbols() throws UndeclaredSymbol {
        for (Macro macro : macroTableDefinitions.values()) {
            if (macro.isDeclared()) { // Bessa viadinho
                while (!macro.getUsedAt().isEmpty()) {
                    int pos = macro.getUsedAt().pop();
                    Assembler.getInstance().getAssembledCode().set(pos, (short) (macro.getValue() - pos));
                }
            } else {
                throw new UndeclaredSymbol(macro);
            }
        }
    }

    public void reset() {
        this.macroTableDefinitions.clear();
    }
}
