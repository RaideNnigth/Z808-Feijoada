package assembler;

import assembler.mnemonics.Add;
import assembler.mnemonics.Operation;

import java.util.HashMap;

public class OperationProcessor {
    //private CodeTable codeTable;
    private final HashMap<String, Operation> codeTable = new HashMap<>();

    public OperationProcessor() {
        // DEFINING MNEMONIC OPERATIONS
        codeTable.put(Add.MNEMONIC, new Add());

        //codeTable = CodeTable.getInstance();
    }

    public boolean isOperation(String line) {
        return codeTable.get(line.split(" ")[0]) != null;
        //return codeTable.isValidOperation(line);
    }

    public void assembleOperation(String line) {
        Operation op = codeTable.get(line.split(" ")[0]);
        op.assemble(line);
    }
}
