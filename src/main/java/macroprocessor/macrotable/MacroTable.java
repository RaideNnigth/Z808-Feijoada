package macroprocessor.macrotable;

import java.util.HashMap;

//Singleton class
public class MacroTable {
    private final HashMap<String, Macro> macroTableDefinitions;

    private static MacroTable instance = null;

    private MacroTable() {
        macroTableDefinitions = new HashMap<>();
    }

    public static MacroTable getInstance() {
        if (instance == null) {
            instance = new MacroTable();
        }
        return instance;
    }

    public Macro getMacro(String macro) {
        return macroTableDefinitions.get(macro);
    }

    public boolean macroExists(String macro) {
        return macroTableDefinitions.containsKey(macro);
    }

    public void declareMacro(String macroName, String[] parameters) {
        Macro newMacro = new Macro(macroName, parameters);

        macroTableDefinitions.put(macroName, newMacro);
    }

    public void reset() {
        this.macroTableDefinitions.clear();
    }
}