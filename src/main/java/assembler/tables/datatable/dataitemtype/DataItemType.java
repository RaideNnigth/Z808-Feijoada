package assembler.tables.datatable.dataitemtype;
import java.util.HashMap;

public class DataItemType {
    private static final HashMap<String, Byte> dataItemTypeMap = new HashMap<>();
    public DataItemType(String dataType) {
        dataItemTypeMap.put("DW", (byte) 2);
        dataItemTypeMap.put("DD", (byte) 4);
        dataItemTypeMap.put("CHAR", (byte) 1);
        containsKey(dataType);
    }

    public byte getByteSize(String key) {
        return dataItemTypeMap.get(key);
    }

    public void containsKey(String dataType) {
        if (dataItemTypeMap.containsKey(dataType)) {
            return;
        }
        if (isArray(dataType)) {
            putArrayOnMap(dataType, dataItemTypeMap.get(dataType.substring(0, dataType.indexOf("["))));
        }
        else throw new IllegalArgumentException("Invalid data type: " + dataType);
    }

    private void putArrayOnMap(String key, byte value) {
        String[] split = key.split("\\[");
        String numberPart = split[1].replace("]", "");
        int number = Integer.parseInt(numberPart);
        dataItemTypeMap.put(key, (byte) (value * number));
    }

    private boolean isArray(String key) {
        return key.matches("CHAR\\[(\\d+)]|DW\\[(\\d+)]|DD\\[(\\d+)]");
    }
}
