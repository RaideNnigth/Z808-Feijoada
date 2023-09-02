package linker.tables;

import java.util.HashMap;
import assembler.tables.symboltable.Symbol;
import assembler.tables.symboltable.UndeclaredSymbol;
import assembler.utils.AssemblerUtils;
import logger.Logger;

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

    public boolean symbolExists(String symbol) {
        return symbolTable.containsKey(symbol);
    }

    public Symbol getSymbol(String symbol) {
        return symbolTable.get(symbol);
    }

    public void addOccurrenceOfSymbol(String symbol) {
        var symbObj = symbolTable.get(symbol);
    }

    public void addSymbol(Symbol s) {
        if (!AssemblerUtils.isValidName(s.getIdentification())) {
            Logger.getInstance().error(0, String.format("%s is not a valid identifier.", s.getIdentification()));
        }

        symbolTable.put(s.getIdentification(), s);
    }

    public void replaceAllOcorrencesOfDeclaredSymbols() throws UndeclaredSymbol {
        for (Symbol s : symbolTable.values()) {
            if (s.isDeclared()) {
                while (!s.getUsedAt().isEmpty()) {

                }
            } else {
                throw new UndeclaredSymbol(s);
            }
        }
    }

    public void reset() {
        symbolTable.clear();
    }
}
