package linker.directives;

import linker.LinkerOperation;
import linker.tables.GlobalSymbolTable;

public class Near implements LinkerOperation {
    public static final String MNEMONIC = "Near";
    private final GlobalSymbolTable globalSymbolTable;
    public Near() {
        globalSymbolTable = GlobalSymbolTable.getInstance();
    }

    @Override
    public void link(String line) {

//            String[] tokens = AssemblerUtils.decomposeInTokens(line);
//            String[] values = tokens[1].split(";");
//            if (values[0].equals( "CS" ) || values[0].equals( "DS" ) || values[0].equals( "SS" )
//                    || values[0].equals( "DS_END" ) || values[0].equals( "SS_END" ) ) {
//                symbolTable.addSymbol(new Symbol(tokens[1], true, (short)0));
    }
}

