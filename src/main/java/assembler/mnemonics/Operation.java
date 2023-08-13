package assembler.mnemonics;

import assembler.AssembleableOperation;
import assembler.Assembler;
import assembler.tables.datatable.DataItem;
import assembler.tables.datatable.DataTable;
import assembler.tables.symboltable.Symbol;
import assembler.tables.symboltable.SymbolTable;
import assembler.utils.AssemblerUtils;

public abstract class Operation implements AssembleableOperation {
    public static final String  AX_STR = "AX";
    public static final String DX_STR = "DX";
    public static final String SI_STR = "SI";

    protected void processDirectAddressing( String currentLine ) throws Exception {
        var dt = DataTable.getInstance();
        var assembledCode = Assembler.getInstance().getAssembledCode();
        String[] tokens = AssemblerUtils.decomposeInTokens(currentLine);
        if (tokens.length == 3){
            if (dt.dataItemNameExist(tokens[0])) {
                var sy = dt.getDataItem(tokens[0]);
                if (sy.isDeclared()) {
                    assembledCode.add(sy.getValue());
                } else {
                    dt.addOccurrenceOfDataItem(tokens[0]);
                }
            } else if (AssemblerUtils.isValidName(tokens[0])) {
                dt.processDataItem(currentLine);
                DataItem d = dt.getDataItem(tokens[0]);
                dt.addOccurrenceOfDataItem(d.getIdentification());
            }
        } else if (AssemblerUtils.isValidName(tokens[0])) {
            dt.processDataItem(currentLine);
            DataItem d = dt.getDataItem(tokens[0]);
            dt.addOccurrenceOfDataItem(d.getIdentification());
        }

    }
    protected  void processJumpAddressing( String token ) {
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
    }
}
