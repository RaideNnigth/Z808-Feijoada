package linker.tables;

import linker.entities.LinkerSymbol;
import linker.tables.exceptions.AlreadyDeclaredPublicSymbolException;

import java.util.HashMap;

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

    public void addDefinitionTableToGlobalMap(DefinitionsTable definitionsTable) throws AlreadyDeclaredPublicSymbolException {
        var symbolNames = definitionsTable.getDefinitionsTable().keySet();
        for (var symbolName : symbolNames) {
            if(!globalSymbolTable.containsKey(symbolName)) {
                globalSymbolTable.put(symbolName, definitionsTable.getSymbol(symbolName));
            }
            // Symbol redefinition in another module!
            else {
                throw new AlreadyDeclaredPublicSymbolException(symbolName);
            }
        }

    }

    public boolean containsSymbol(String symbolName) {
        return globalSymbolTable.containsKey(symbolName);
    }

    public void reset() {
        globalSymbolTable.clear();
    }

    public LinkerSymbol getSymbol(String symbolName) {
        return globalSymbolTable.get(symbolName);
    }
}
