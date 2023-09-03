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

import java.util.HashMap;
import java.util.LinkedList;

public class UsageTable {
    public static class SymbolOcurrence {
        String moduleName;
        short address;

        public SymbolOcurrence(String moduleName, short address) {
            this.moduleName = moduleName;
            this.address = address;
        }
    }

    private final HashMap<String, LinkedList<SymbolOcurrence>> usageTable = new HashMap<>();

    public void addSymbol(String symbolName, String moduleName, short address) {
        if(!usageTable.containsKey(symbolName)) {
            usageTable.put(symbolName, new LinkedList<>());
        }

        var list = usageTable.get(symbolName);
        list.add(new SymbolOcurrence(moduleName, address));
    }

    public HashMap<String, LinkedList<SymbolOcurrence>> getUsageTable() {
        return usageTable;
    }

    public boolean containsSymbol(String symbolName) {
        return usageTable.containsKey(symbolName);
    }

    public LinkedList<SymbolOcurrence> getSymbolOccurrence(String symbolName) {
        return usageTable.get(symbolName);
    }
}
