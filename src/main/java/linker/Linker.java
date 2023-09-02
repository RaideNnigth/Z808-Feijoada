package linker;

import linker.tables.DefinitionsTable;
import linker.tables.GlobalSymbolTable;
import linker.tables.UsageTable;

import java.util.HashMap;

public class Linker {
    private static Linker instance = null;

    private final HashMap<String, DefinitionsTable> definitionsTables;

    private final HashMap<String, UsageTable> usageTables;

    private final GlobalSymbolTable globalSymbolTable;

    // Singleton pattern
    private Linker() {
        definitionsTables = new HashMap<>();
        usageTables = new HashMap<>();
        globalSymbolTable = GlobalSymbolTable.getInstance();
    }
    public static Linker getInstance() {
        if (instance == null) {
            instance = new Linker();
        }
        return instance;
    }

    public void addDefinitionsTable(String fileName, DefinitionsTable definitionsTable) {
        definitionsTables.put(fileName, definitionsTable);
    }

    public HashMap<String, DefinitionsTable> getHashMapDefinitionsTable() {
        return definitionsTables;
    }

    public DefinitionsTable getDefinitionsTable(String fileName) {
        return definitionsTables.get(fileName);
    }

    public void addUsageTable(String fileName, UsageTable usageTable) {
        usageTables.put(fileName, usageTable);
    }

    public HashMap<String, UsageTable> getHashMapUsageTable() {
        return usageTables;
    }

    public UsageTable getUsageTable(String fileName) {
        return usageTables.get(fileName);
    }
}
