package assembler.tables.datatable.dataitemtype;

import assembler.utils.AssemblerUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class CHAR extends Type {
    public static final String MNEMONIC = "CHAR";
    private static final int DEFAULT_SIZE = 1;
    private int size;

    public CHAR(String dataType) {
        super(dataType);
    }

    @Override
    protected void setSizeInBytes(int size) {
        this.size = size;
    }

    @Override
    public int getSizeInBytes() {
        return size;
    }

    @Override
    public int getDefaultSize() {
        return DEFAULT_SIZE;
    }

    @Override
    public ArrayList<Byte> parseInitialValue(String val) {
        // Check if it's a single char
        if (val.matches("'\\w'")) {
            // Safe to use because we already checked if it's a single char inside simple quotes
            var b = val.substring(1, 2).getBytes();
            return AssemblerUtils.toByteArrayList(b);
        }
        // Check if it's a string (only ASCII chars accepted)
        else if (val.matches("\"\\p{ASCII}+\"")) {
            var b = val.substring(1, val.length() - 1).getBytes();
            var bList = AssemblerUtils.toByteArrayList(b);

            // Add the null terminator
            bList.add((byte) '\0');

            return bList;
        }
        // If none works, it's a bad value
        else {
            throw new IllegalArgumentException("Invalid initial value for CHAR: " + val);
        }
    }
}
