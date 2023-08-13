package assembler.tables.datatable;

import assembler.Assembler;
import assembler.Log;
import assembler.LogType;
import assembler.utils.AssemblerUtils;

import java.util.HashMap;

public class DataTable {
    private final HashMap<String, DataItem> dataItemHashMap = new HashMap<>();

    // Singleton pattern
    private static DataTable instance = null;

    // Next available address on memory access it through static access
    private short nextAvailableAddress = 0;

    private DataTable() {
    }

    public static DataTable getInstance() {
        if (instance == null)
            instance = new DataTable();

        return instance;
    }

    public void processDataItem(String currentLine) throws Exception{
        String[] tokens = AssemblerUtils.decomposeInTokens(currentLine);
        if (tokens.length == 3) {
            DataTable.getInstance().addDataItem(new DataItem(tokens[0], true,
                    true, (short) 16, Short.parseShort(tokens[2]), this.nextAvailableAddress));
        } else if (tokens.length == 2){
            DataTable.getInstance().addDataItem(new DataItem(null, true,
                    false, (short) 16, Short.parseShort(tokens[1]), this.nextAvailableAddress));
        }
    }
    public boolean dataItemNameExist(String symbol) {
        return dataItemHashMap.containsKey(symbol);
    }

    public DataItem getDataItem(String symbol) {
        return dataItemHashMap.get(symbol);
    }

    public void addOccurrenceOfDataItem(String dataItem) {
        var symbolObj = dataItemHashMap.get(dataItem);
        var assembledCode = Assembler.getInstance().getAssembledCode();

        symbolObj.getUsedAt().add(assembledCode.size());
        assembledCode.add((short) 0);
    }

    public void addDataItem(DataItem d) {
        if (!AssemblerUtils.isValidName(d.getIdentification())) {
            Log l = new Log(LogType.ERROR, Assembler.getInstance().getLineCounter(),
                    String.format("%s is not a valid identifier.", d.getIdentification()));
            Assembler.getInstance().getLogger().addLog(l);
        }
        dataItemHashMap.put(d.getIdentification(), d);
        nextAvailableAddress = (short) (nextAvailableAddress + d.getSize());
    }

    public void replaceAllOcorrencesOfDeclaredSymbols() throws UndeclaredDataItem {
        for (DataItem s : dataItemHashMap.values()) {
            if (s.isDeclared()) {
                while (!s.getUsedAt().isEmpty()) {
                    int pos = s.getUsedAt().pop();
                    Assembler.getInstance().getAssembledCode().set(pos, s.getValue());
                }
            } else {
                throw new UndeclaredDataItem(s);
            }
        }
    }

    public void reset() {
        dataItemHashMap.clear();
    }
}
