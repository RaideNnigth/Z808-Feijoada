package assembler.mnemonics;

import assembler.AssembleableOperation;
import assembler.Assembler;
import assembler.Log;
import assembler.LogType;
import assembler.tables.datatable.DataItem;
import assembler.tables.datatable.DataTable;
import assembler.tables.symboltable.Symbol;
import assembler.tables.symboltable.SymbolTable;
import assembler.utils.AssemblerUtils;

public abstract class Operation implements AssembleableOperation {
    public static final String AX_STR = "AX";
    public static final String DX_STR = "DX";
    public static final String SI_STR = "SI";

    // For variables declared in Data Segment
    protected void processDirectAddressing(String token) {
        var dataTable = DataTable.getInstance();
        var assembledCode = Assembler.getInstance().getAssembledCode();

        // Check if variable exists in DataTable
        if (dataTable.dataItemExist(token)) {
            var dataItem = dataTable.getDataItem(token);

            // Check if variable is declared
            if (dataItem.isDeclared()) {
                // If exists and is declared, yey! Add its address to assembled code
                assembledCode.add(dataItem.getAddress());
            } else {
                // If exists but is not declared, then the code segment doesn't appears in the start of file.
                // Add it to list of occurrences
                dataTable.addOccurrenceOfDataItem(dataItem);
            }
        }
        // If doesn't exists, then it's a new variable. Check if name is valid and add it to DataTable.
        else if (AssemblerUtils.isValidName(token)) {
            DataItem d = new DataItem(token, false);
            dataTable.addOccurrenceOfDataItem(d);
        }
        // If it's not a valid name, then it's an error
        else {
            Assembler.getInstance().getLogger().addLog(new Log(LogType.ERROR, Assembler.getInstance().getLineCounter(),
                    "Invalid name for Data Item: " + token));
            Assembler.getInstance().setLoggerInterruption(true);
        }
    }

    // For LABELS!
    protected void processJumpAddressing(String token) {
        var st = SymbolTable.getInstance();
        var assembledCode = Assembler.getInstance().getAssembledCode();

        if (st.symbolExists(token)) {
            var sy = st.getSymbol(token);
            if (sy.isDeclared()) {
                assembledCode.add(sy.getValue());
            } else {
                st.addOccurrenceOfSymbol(token);
            }
        } else if (AssemblerUtils.isValidName(token)) {
            Symbol s = new Symbol(token, false);

            st.addSymbol(s);
            st.addOccurrenceOfSymbol(s.getIdentification());
        }
        // If it's not a valid name, then it's an error
        else {
            Assembler.getInstance().getLogger().addLog(new Log(LogType.ERROR, Assembler.getInstance().getLineCounter(),
                    "Invalid name for Label: " + token));
            Assembler.getInstance().setLoggerInterruption(true);
        }
    }
}
