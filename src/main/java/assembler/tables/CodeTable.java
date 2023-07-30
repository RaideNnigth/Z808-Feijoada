package assembler.tables;

import assembler.AssembleableOperation;
import assembler.directives.Assume;
import assembler.directives.Dw;
import assembler.directives.End;
import assembler.directives.Endp;
import assembler.directives.Ends;
import assembler.directives.Equ;
import assembler.directives.Offset;
import assembler.directives.Org;
import assembler.directives.Proc;
import assembler.directives.Segment;
import assembler.mnemonics.arithmetical.*;
import assembler.mnemonics.flow.*;
import assembler.mnemonics.logical.*;
import assembler.mnemonics.move.*;
import assembler.mnemonics.stack.*;
import assembler.directives.*;

import java.util.HashMap;

public class CodeTable {
    private HashMap<String, AssembleableOperation> codeMap;
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

        // Directives
        codeMap.put(End.MNEMONIC, new End());
        codeMap.put(Segment.MNEMONIC, new Segment());
        codeMap.put(Ends.MNEMONIC, new Ends()); // End of segment
        codeMap.put(Dw.MNEMONIC, new Dw());
        codeMap.put(Equ.MNEMONIC, new Equ());
        codeMap.put(Org.MNEMONIC, new Org());
        codeMap.put(Offset.MNEMONIC, new Offset());
        codeMap.put(Assume.MNEMONIC, new Assume());
        // Gab Bessa: Meio inutil ^
        // Henrique: Inutil eh tua vida
        // Gustavo: Inutil eh c#
        codeMap.put(Proc.MNEMONIC, new Proc());
        codeMap.put(Endp.MNEMONIC, new Endp());
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
