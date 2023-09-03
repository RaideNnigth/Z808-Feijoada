package assembler.mnemonics;

import assembler.*;
import assembler.tables.datatable.DataItem;
import assembler.tables.datatable.DataTable;
import assembler.tables.symboltable.Symbol;
import assembler.tables.symboltable.SymbolTable;
import assembler.utils.AssemblerUtils;
import linker.Linker;
import logger.Log;
import logger.LogType;
import logger.Logger;

public abstract class Operation implements AssembleOperation {
    public static final String AX_STR = "AX";
    public static final String DX_STR = "DX";
    public static final String SI_STR = "SI";

    // For variables declared in Data Segment
    protected void processDirectAddressing(String token) throws AssemblerError {
        var dataTable = DataTable.getInstance();
        var assembledCode = Assembler.getInstance().getAssembledCode();
        var moduleName = Assembler.getInstance().getModuleName();
        var linker = Linker.getInstance();


        // Check if it is external symbol in usage table
        if (linker.getUsageTable(moduleName).containsSymbol(token)) {
            linker.getUsageTable(moduleName).addSymbolOccurrence(token, moduleName, (short) assembledCode.size());
            assembledCode.add((short) 0); // Add placeholder for the address of the symbol
            return;
        }

        // If it's not external, check if variable exists in DataTable
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
            throw new AssemblerError(String.format("Invalid name for data item: %s", token));
            //Logger.getInstance().addLog(new Log(LogType.ERROR, Assembler.getInstance().getLineCounter(),
            //        "Invalid name for Data Item: " + token));
        }
    }

    // For LABELS!
    protected void processJumpAddressing(String token) throws AssemblerError {
        var st = SymbolTable.getInstance();
        var moduleName = Assembler.getInstance().getModuleName();
        var linker = Linker.getInstance();
        var assembledCode = Assembler.getInstance().getAssembledCode();

        // Check if it is external symbol in usage table
        if (linker.getUsageTable(moduleName).containsSymbol(token)) {
            linker.getUsageTable(moduleName).addSymbolOccurrence(token, moduleName, (short) assembledCode.size());
            assembledCode.add((short) 0); // Add placeholder for the address of the symbol
            return;
        }

        if (st.symbolExists(token)) {
            st.addOccurrenceOfSymbol(token);
        } else if (AssemblerUtils.isValidName(token)) {
            Symbol s = new Symbol(token, false);

            st.addSymbol(s);
            st.addOccurrenceOfSymbol(s.getIdentification());
        }
        // If it's not a valid name, then it's an error
        else {
            throw new AssemblerError(String.format("Invalid name for label: %s", token));
            //Logger.getInstance().addLog(new Log(LogType.ERROR, Assembler.getInstance().getLineCounter(),
            //        "Invalid name for Label: " + token));
        }
    }
}
