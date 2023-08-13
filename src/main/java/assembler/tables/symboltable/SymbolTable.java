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

    public void addOccurrenceOfSymbol(String symbol) {
        var symbObj = symbolTable.get(symbol);
        var assembledCode = Assembler.getInstance().getAssembledCode();

        symbObj.getUsedAt().add(assembledCode.size());
        assembledCode.add((short) 0);
    }

    public void addSymbol(Symbol s) {
        if (!AssemblerUtils.isValidName(s.getIdentification())) {
            Log l = new Log(LogType.ERROR, Assembler.getInstance().getLineCounter(),
                    String.format("%s is not a valid identifier.", s.getIdentification()));
            Assembler.getInstance().getLogger().addLog(l);
        }

        symbolTable.put(s.getIdentification(), s);
    }

    public void replaceAllOcorrencesOfDeclaredSymbols() throws UndeclaredSymbol {
        for (Symbol s : symbolTable.values()) {
            if (s.isDeclared()) {
                while (!s.getUsedAt().isEmpty()) {
                    int pos = s.getUsedAt().pop();
                    Assembler.getInstance().getAssembledCode().set(pos, s.getValue());
                }
            }
            else {
                throw new UndeclaredSymbol(s);
            }
        }
    }

    public void reset() {
        symbolTable.clear();
    }
}
