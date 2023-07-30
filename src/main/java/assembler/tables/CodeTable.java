package assembler.tables;

import assembler.mnemonics.arithmetical.Add;
import assembler.AssembleableOperation;

import java.util.HashMap;

public class CodeTable {
    private HashMap<String, AssembleableOperation> codeMap;
    private static CodeTable instance = null;

    private CodeTable() {
        codeMap = new HashMap<>();

        // Operações
        codeMap.put(Add.MNEMONIC, new Add());
    }

    public static CodeTable getInstance() {
        if (instance == null)
            instance = new CodeTable();

        return instance;
    }

    public boolean isValidOperation(String key) {
        return codeMap.get(key) != null;
    }

    public AssembleableOperation getOperation(String key) {
        return codeMap.get(key);
    }

    public String[] getKeys() {
        return codeMap.keySet().toArray(new String[0]);
    }
}
