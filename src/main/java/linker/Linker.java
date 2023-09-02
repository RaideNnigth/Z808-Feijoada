package linker;

import linker.tables.DefinitionsTable;

import java.util.ArrayList;
import java.util.HashMap;

public class Linker {
    private static Linker instance = null;
    private Linker() {
        definitionsTables = new HashMap<>();
    }
    public static Linker getInstance() {
        if (instance == null) {
            instance = new Linker();
        }
        return instance;
    }

    private final HashMap<String, DefinitionsTable> definitionsTables;

    public void addDefinitionsTable(String fileName, DefinitionsTable definitionsTable) {
        definitionsTables.put(fileName, definitionsTable);
    }

    public HashMap<String, DefinitionsTable> getHashMapDefinitionsTable() {
        return definitionsTables;
    }

    public DefinitionsTable getDefinitionsTable(String fileName) {
        return definitionsTables.get(fileName);
    }
}
