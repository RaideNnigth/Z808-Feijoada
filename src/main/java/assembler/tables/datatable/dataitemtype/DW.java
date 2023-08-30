package assembler.tables.datatable.dataitemtype;

import assembler.utils.AssemblerUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class DW extends Type {
    public static final String MNEMONIC = "DW";
    private static final int DEFAULT_SIZE = 2;
    private int size;

    public DW(String dataType) {
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
        // If it's not an array, we will just parse the number
        if (size == DEFAULT_SIZE) {
            // Check if is a valid number
            if (!AssemblerUtils.isNumericConstant(val)) {
                throw new IllegalArgumentException("Invalid number: " + val);
            }

            // Parse the number
            return AssemblerUtils.parseDWToByteArrayList(val);
        }

        // Otherwise, we will parse the array
        int sizeArr = size / DEFAULT_SIZE;
        ArrayList<Byte> bytesReturn = new ArrayList<>(size);

        // Array sintax: DW[5] {1, 2, 3, 4, 5}

        val = val.replace(" ", ""); // Remove all spaces
        String[] split = val.split(","); // Split with comma

        // Check array consistency
        if (split.length > sizeArr) {
            throw new IllegalArgumentException("ARRAY OUT OF BOUNDS\nThe size of the array is " + sizeArr + " and you are trying to insert " + split.length + " elements");
        }

        for (String s : split) {
            // Check if is a valid number
            if (!AssemblerUtils.isNumericConstant(s)) {
                throw new IllegalArgumentException("Invalid number: " + s);
            }

            // Parse the number and add to return array
            bytesReturn.addAll(AssemblerUtils.parseDWToByteArrayList(s));
        }

        // Fill the rest of the array with 0, if necessary
        for (int i = 0; i < size - (split.length * DEFAULT_SIZE); i++) {
            bytesReturn.add((byte) 0);
        }

        return bytesReturn;
    }

}
