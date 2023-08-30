package assembler.tables.datatable.dataitemtype;

import java.util.ArrayList;

// Type base class for every tipe of data item
public abstract class Type {
    protected Type(String dataType) {
        // ------------- Initializing data type -------------

        // We don't need to do any other validation, since this was already done in TypeFactory
        if (dataType.contains("[")) {
            // Is an array
            setSizeInBytes(getDefaultSize() * getArrayLenght(dataType));
        } else {
            // Otherwise will set to the default value
            setSizeInBytes(getDefaultSize());
        }
    }

    private int getArrayLenght(String key) {
        String[] split = key.split("\\[");
        String numberPart = split[1].replace("]", "");
        return Integer.parseInt(numberPart);
    }

    protected abstract void setSizeInBytes(int size);

    // ------------- Public interface -------------
    public abstract int getSizeInBytes();
    public abstract int getDefaultSize();
    public abstract ArrayList<Byte> parseInitialValue(String val);
}
