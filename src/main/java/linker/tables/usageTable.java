package linker.tables;

/*
    * This class is used to store the usage table
    * each file will have one usage table
    *
    * Symbol is the name that it is called from external file
    * in class you define it using public
    *
    * Signal is to indicate address\Absolute\Relative +\- N being N any number
    *
    * Ocorrence is the address of the symbol in bin file
 */

import linker.entities.Symbol;

import java.util.ArrayList;
import java.util.HashMap;

public class usageTable {

    private final HashMap<String, ArrayList<Symbol>> usageTable = new HashMap<>();

    public void addSymbol(String symbolName, Symbol s) {
        // compute if present just add one more symbol to Arraylist in hashmap for symbolName
        // if not present create new Arraylist and add symbol to it
        if (usageTable.containsKey(symbolName)) {
            usageTable.get(symbolName).add(s);
        } else {
            ArrayList<Symbol> symbolList = new ArrayList<>();
            symbolList.add(s);
            usageTable.put(symbolName, symbolList);
        }
    }

    public HashMap<String, ArrayList<Symbol>> getUsageTable() {
        return usageTable;
    }

    public boolean containsSymbol(String symbolName) {
        return usageTable.containsKey(symbolName);
    }

    public ArrayList<Symbol> getSymbolOccurrence(String symbolName) {
        return usageTable.get(symbolName);
    }
}
