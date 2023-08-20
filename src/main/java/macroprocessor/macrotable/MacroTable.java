package macroprocessor.macrotable;

import java.util.ArrayList;
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

    public Macro getMacroIfWasDeclared(String macroName) {
        ArrayList<Macro> macros = getAllNestedMacrosWithName(macroName);
        if (macros.isEmpty()) {
            return getMacro(macroName);
        }

        for (Macro m : macros) {
            if (m.getWasCalled()) {
                return m;
            }
        }
        return null;
    }

    private ArrayList<Macro> getAllNestedMacrosWithName(String macroName) {
        ArrayList<Macro> macros = new ArrayList<>();
        for (Macro m : macroTableDefinitions.values()) {
            if (m.getIdentification().contains("." + macroName)) {
                macros.add(m);
            }
        }
        return macros;
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