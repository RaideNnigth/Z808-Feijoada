package assembler.tables;

import assembler.AssembleOperation;
import assembler.mnemonics.arithmetical.*;
import assembler.mnemonics.flow.*;
import assembler.mnemonics.logical.*;
import assembler.mnemonics.move.*;
import assembler.mnemonics.stack.*;

import java.util.HashMap;

public class CodeTable {
    private HashMap<String, AssembleOperation> codeMap;
    private static CodeTable instance = null;

    private CodeTable() {
        codeMap = new HashMap<>();

        // Operations
        codeMap.put(Add.MNEMONIC, new Add());
        codeMap.put(Cmp.MNEMONIC, new Cmp());
        codeMap.put(Div.MNEMONIC, new Div());
        codeMap.put(Mul.MNEMONIC, new Mul());
        codeMap.put(Sub.MNEMONIC, new Sub());
        codeMap.put(Call.MNEMONIC, new Call());
        codeMap.put(Int.MNEMONIC, new Int());
        codeMap.put(Je.MNEMONIC, new Je());
        codeMap.put(Jmp.MNEMONIC, new Jmp());
        codeMap.put(Jnz.MNEMONIC, new Jnz());
        codeMap.put(Jp.MNEMONIC, new Jp());
        codeMap.put(Jz.MNEMONIC, new Jz());
        codeMap.put(Ret.MNEMONIC, new Ret());
        codeMap.put(And.MNEMONIC, new And());
        codeMap.put(Not.MNEMONIC, new Not());
        codeMap.put(Or.MNEMONIC, new Or());
        codeMap.put(Xor.MNEMONIC, new Xor());
        codeMap.put(Mov.MNEMONIC, new Mov());
        codeMap.put(Pop.MNEMONIC, new Pop());
        codeMap.put(Push.MNEMONIC, new Push());
    }

    public static CodeTable getInstance() {
        if (instance == null)
            instance = new CodeTable();

        return instance;
    }

    public boolean isValidOperation(String key) {
        return codeMap.get(key) != null;
    }

    public AssembleOperation getOperation(String key) {
        return codeMap.get(key);
    }

    public String[] getKeys() {
        return codeMap.keySet().toArray(new String[0]);
    }
}
