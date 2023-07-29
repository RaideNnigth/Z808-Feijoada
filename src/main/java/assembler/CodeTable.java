package assembler;

import assembler.mnemonics.ADD;
import assembler.mnemonics.Operation;

import java.util.HashMap;

public class CodeTable {
    private HashMap<String, Operation> codeMap;
    private static CodeTable instance = null;

    private CodeTable() {
        codeMap = new HashMap<>();

        // Operações
        codeMap.put(ADD.MNEMONIC, new ADD());
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
