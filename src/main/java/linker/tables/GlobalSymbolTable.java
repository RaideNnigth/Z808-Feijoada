package linker.tables;

import java.util.HashMap;

import linker.entities.Symbol;

public class GlobalSymbolTable {
    private final HashMap<String, Symbol> symbolTable = new HashMap<>();

    // Singleton pattern
    private static GlobalSymbolTable instance = null;

    private GlobalSymbolTable() {
    }

    public static GlobalSymbolTable getInstance() {
        if (instance == null)
            instance = new GlobalSymbolTable();

        return instance;
    }


}
