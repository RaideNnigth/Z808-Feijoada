package assembler.directives;

import assembler.AssembleableOperation;
import assembler.tables.symboltable.Symbol;
import assembler.tables.symboltable.SymbolTable;
import assembler.utils.AssemblerUtils;
import assembler.Assembler;

public class Segment implements AssembleableOperation {
    public static final String MNEMONIC = "SEGMENT";
    private final SymbolTable symbolTable;
    private final Assembler assembler = Assembler.getInstance();

    private Boolean isDataFound = false;
    private Boolean isCodeFound = false;

    public Segment() {
        symbolTable = SymbolTable.getInstance();
    }

    @Override
    public void assemble(String line) throws Exception {
        String name;
        String[] tokens = AssemblerUtils.decomposeInTokens(line);
        name = tokens[0];
        if (name.equals(".DATA")) {
            if (isDataFound)
                throw new Exception("Duplicate data segment name");
            symbolTable.addSymbol(new Symbol(name, true, (short) 0));
            isDataFound = true;
            assembler.dataSegmentFound();
        } else if (name.equals(".CODE")) {
            if (isCodeFound)
                throw new Exception("Duplicate code segment name");
            symbolTable.addSymbol(new Symbol(name, true, (short) 0));
            isCodeFound = true;
            assembler.codeSegmentFound();
        } else
            throw new Exception("Invalid segment name");
    }
}
