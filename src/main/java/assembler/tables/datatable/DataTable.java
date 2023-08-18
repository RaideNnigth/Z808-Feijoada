package assembler.tables.datatable;

import assembler.Assembler;
import assembler.Log;
import assembler.LogType;
import assembler.Logger;
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

    public void processDataItemDeclaration(String currentLine) {
        String[] tokens = AssemblerUtils.decomposeInTokens(currentLine);
        DataItem d;

        if (tokens.length == 3) {
            if (dataItemExist(tokens[0])) {
                var dataItem = getDataItem(tokens[0]);

                // Check if it's redeclaration
                if (dataItem.isDeclared())
                    throw new IllegalArgumentException(String.format("Redeclaring Data Item %s. Line: %d", tokens[0], Assembler.getInstance().getLineCounter()));
                // If not, we are defining the variable (data segment in the end of file)
                else {
                    dataItem.setDeclared(true);
                    dataItem.setTypeAndInitialValue(tokens[1], tokens[2]);
                    dataItem.setAddress(this.nextAvailableAddress);

                    this.nextAvailableAddress = (short) (nextAvailableAddress + (AssemblerUtils.roundUpToClosestPower2(dataItem.getSize() / 2)));
                    return;
                }
            }

            // If it's not redeclaration, then it's a new variable
            d = new DataItem(tokens[0], true, tokens[1],
                    tokens[2], this.nextAvailableAddress);

            addDataItem(d);
        }
        // Anonymous data item
        else if (tokens.length == 2) {
            d = new DataItem(null, true, tokens[0],
                    tokens[1], this.nextAvailableAddress);
            addDataItem(d);
        } else {
            throw new IllegalArgumentException("Invalid declaration of Data Item!");
        }

        nextAvailableAddress = (short) (nextAvailableAddress + (AssemblerUtils.roundUpToClosestPower2(d.getSize() / 2)));
    }

    private void addDataItem(DataItem d) {
        if (!AssemblerUtils.isValidName(d.getIdentification())) {
            Log l = new Log(LogType.ERROR, Assembler.getInstance().getLineCounter(),
                    String.format("%s is not a valid identifier.", d.getIdentification()));
            Logger.getInstance().addLog(l);
        }
        dataItemHashMap.put(d.getIdentification(), d);
    }

    public boolean dataItemExist(String dataItem) {
        return dataItemHashMap.containsKey(dataItem);
    }

    public DataItem getDataItem(String dataItem) {
        return dataItemHashMap.get(dataItem);
    }

    public void addOccurrenceOfDataItem(DataItem d) {
        if (!dataItemHashMap.containsKey(d.getIdentification()))
            addDataItem(d);

        var assembledCode = Assembler.getInstance().getAssembledCode();

        d.getUsedAt().add(assembledCode.size());
        assembledCode.add((short) 0);
    }

    public void replaceAllOcorrencesOfDeclaredDataItems() throws UndeclaredDataItem {
        for (DataItem d : dataItemHashMap.values()) {
            if (d.isDeclared()) {
                while (!d.getUsedAt().isEmpty()) {
                    int pos = d.getUsedAt().pop();
                    // Coloca o endereço (na memória de dados) do dado referenciado
                    Assembler.getInstance().getAssembledCode().set(pos, d.getAddress());
                }
            } else {
                throw new UndeclaredDataItem(d);
            }
        }
    }

    public short getNextAvailableAddress() {
        return this.nextAvailableAddress;
    }

    public void reset() {
        dataItemHashMap.clear();
        nextAvailableAddress = 0;
    }
}
