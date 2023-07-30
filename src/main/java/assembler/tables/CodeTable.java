package assembler.tables;

import assembler.mnemonics.Add;
import assembler.Operation;

import java.util.HashMap;

public class CodeTable {
    private HashMap<String, Operation> codeMap;
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

    public Operation getOperation(String key) {
        return codeMap.get(key);
    }
}
