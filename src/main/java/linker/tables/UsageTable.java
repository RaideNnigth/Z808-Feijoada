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
 * Occurrence is the address of the symbol in bin file
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class UsageTable {
    private final HashMap<String, LinkedList<SymbolOccurrence>> usageTable = new HashMap<>();

    public static class SymbolOccurrence {
        public String moduleName;
        public short address;

        public SymbolOccurrence(String moduleName, short address) {
            this.moduleName = moduleName;
            this.address = address;
        }
    }

    public void addSymbol(String symbolName) {
        if (!usageTable.containsKey(symbolName)) {
            usageTable.put(symbolName, new LinkedList<>());
        }
    }

    public void addSymbolOccurrence(String symbolName, String moduleName, short address) {
        var list = usageTable.get(symbolName);
        list.add(new SymbolOccurrence(moduleName, address));
    }

    public HashMap<String, LinkedList<SymbolOccurrence>> getUsageTable() {
        return usageTable;
    }

    public boolean containsSymbol(String symbolName) {
        return usageTable.containsKey(symbolName);
    }

    public LinkedList<SymbolOccurrence> getSymbolOccurrences(String symbolName) {
        return usageTable.get(symbolName);
    }

    public Set<String> getSymbolNames() {
        return usageTable.keySet();
    }
}
