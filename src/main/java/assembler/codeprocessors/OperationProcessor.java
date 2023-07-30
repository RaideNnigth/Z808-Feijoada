package assembler.codeprocessors;

import assembler.AssembleableOperation;
import assembler.tables.CodeTable;
import assembler.utils.AssemblerUtils;

public class OperationProcessor {
    private CodeTable codeTable;

    public OperationProcessor() {
        codeTable = CodeTable.getInstance();
    }

    public boolean assembleOperation(String line) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        if(!codeTable.isValidOperation(tokens[0]))
            return false;

        AssembleableOperation op = codeTable.getOperation(tokens[0]);
        op.assemble(line);
        return true;
    }
}
