package linker;

import linker.tables.DefinitionsTable;
import linker.tables.GlobalSymbolTable;
import linker.tables.UsageTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Linker {
    private static Linker instance = null;

    // Tables from files (modules)
    private final HashMap<String, DefinitionsTable> definitionsTables = new HashMap<>();
    private final HashMap<String, UsageTable> usageTables;

    // Global symbol table
    private final GlobalSymbolTable globalSymbolTable;

    // Map: moduleName -> path to bin file
    private final HashMap<String, String> binFilesPaths;

    // Singleton pattern
    private Linker() {
        usageTables = new HashMap<>();
        globalSymbolTable = GlobalSymbolTable.getInstance();
        binFilesPaths = new HashMap<>();
    }

    public static Linker getInstance() {
        if (instance == null) {
            instance = new Linker();
        }
        return instance;
    }


    public String link() {
        // First let's populate the global symbol table
        for (String module : binFilesPaths.keySet()) {
            var definitionsTable = definitionsTables.get(module);
            globalSymbolTable.addDefinitionTableToGlobalMap(definitionsTable);
        }

        // Now it's time to rock, Bessa Turing

        return "";
    }

    public void addBinPath(String moduleName, String binPath) {
        binFilesPaths.put(moduleName, binPath);
    }

    public void addDefinitionsTable(String moduleName, DefinitionsTable definitionsTable) {
        definitionsTables.put(moduleName, definitionsTable);
    }

    public DefinitionsTable getDefinitionsTable(String moduleName) {
        return definitionsTables.get(moduleName);
    }

    public void addUsageTable(String moduleName, UsageTable usageTable) {
        usageTables.put(moduleName, usageTable);
    }

    public UsageTable getUsageTable(String moduleName) {
        return usageTables.get(moduleName);
    }

    public boolean isExternalSymbol(String moduleName, String symbolName) {
        return usageTables.get(moduleName).containsSymbol(symbolName) || definitionsTables.get(moduleName).containsSymbol(symbolName);
    }
}
