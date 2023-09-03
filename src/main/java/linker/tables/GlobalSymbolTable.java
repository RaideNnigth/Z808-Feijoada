package linker.tables;

import linker.entities.LinkerSymbol;
import java.util.HashMap;

import linker.entities.Symbol;

public class GlobalSymbolTable {
    // Map symbol name to symbol definition
    private final HashMap<String, LinkerSymbol> globalSymbolTable = new HashMap<>();

    // Singleton pattern
    private static GlobalSymbolTable instance = null;
    private GlobalSymbolTable() {
    }
    public static GlobalSymbolTable getInstance() {
        if (instance == null)
            instance = new GlobalSymbolTable();

        return instance;
    }

    public void addDefinitionTableToGlobalMap(DefinitionsTable definitionsTable) {
        var definitions = definitionsTable.getDefinitionsTable();
        globalSymbolTable.forEach(definitions::putIfAbsent);
    }
}
