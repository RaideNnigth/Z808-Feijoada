package assembler.tables.symboltable;

import java.util.HashMap;

public class SymbolTable {
    private final HashMap<String, Symbol> symbolTable = new HashMap<>();

    // Singleton pattern
    private static SymbolTable instance = null;

    private SymbolTable() {}

    public static SymbolTable getInstance(){
        if(instance == null)
            instance = new SymbolTable();

        return instance;
    }

    public boolean symbolExists(String symbol) {
        return symbolTable.containsKey(symbol);
    }

    public Symbol getSymbol(String symbol) {
        return symbolTable.get(symbol);
    }

    public void addOccurrenceOfSymbol(String symbol, int index) {
        var symbObj = symbolTable.get(symbol);
        symbObj.getUsedAt().add(index);
    }

    public void addSymbol(Symbol s) {
        symbolTable.put(s.getIdentificator(), s);
    }
}
