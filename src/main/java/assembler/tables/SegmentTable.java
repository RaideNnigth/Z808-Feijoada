package assembler.tables;

import assembler.AssembleOperation;
import assembler.directives.*;

import java.util.HashMap;

public class SegmentTable {
    private HashMap<String, AssembleOperation> codeMap;
    private static SegmentTable instance = null;

    private SegmentTable() {
        codeMap = new HashMap<>();

        // Directives
        codeMap.put(End.MNEMONIC, new End());
        codeMap.put(Segment.MNEMONIC, new Segment());
        codeMap.put(Ends.MNEMONIC, new Ends()); // End of segment
        //codeMap.put(Dw.MNEMONIC, new Dw());
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

    public static SegmentTable getInstance() {
        if (instance == null)
            instance = new SegmentTable();

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
