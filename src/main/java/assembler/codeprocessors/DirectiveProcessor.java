package assembler.codeprocessors;

import assembler.AssembleableOperation;
import assembler.directives.*;
import assembler.tables.CodeTable;
import assembler.tables.symboltable.SymbolTable;
import assembler.utils.AssemblerUtils;

import java.util.HashMap;

public class DirectiveProcessor {
    private final CodeTable codeTable;
    public DirectiveProcessor() {
        codeTable = CodeTable.getInstance();
    }
    public boolean assembleDirective(String line) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);
        for ( String token : tokens ) {
            if ( codeTable.isValidOperation( token ) ) {
                AssembleableOperation op = codeTable.getOperation( token );
                op.assemble( line );
                return true;
            }
        }
        return false;
    }
}