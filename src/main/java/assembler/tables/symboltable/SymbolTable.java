package assembler.tables.symboltable;

import assembler.Assembler;
import assembler.Log;
import assembler.LogType;
import assembler.utils.AssemblerUtils;

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
        Assembler.getInstance().getAssembledCode().add((short) 0);
    }

    public void addSymbol(Symbol s) {
        if (!AssemblerUtils.isValidName(s.getIdentificator())) {
            Log l = new Log(LogType.ERROR, Assembler.getInstance().getLineCounter(),
                    String.format("%s is not a valid identifier.", s.getIdentificator()));
            Assembler.getInstance().getLogger().addLog(l);
        }

        symbolTable.put(s.getIdentificator(), s);
    }

    public void replaceAllOcorrencesOfDeclaredSymbols() {
        for (Symbol s : symbolTable.values()) {
            if (s.isDeclared()) {
                while (!s.getUsedAt().isEmpty()) {
                    int pos = s.getUsedAt().pop();
                    Assembler.getInstance().getAssembledCode().set(pos, s.getValue());
                }
            }
        }
    }

    public void reset() {
        symbolTable.clear();
    }
}
