package assembler.codeprocessors;

import assembler.AssembleOperation;
import assembler.Assembler;
import assembler.tables.CodeTable;
import assembler.utils.AssemblerUtils;

public class OperationProcessor {
    private CodeTable codeTable;

    public OperationProcessor() {
        codeTable = CodeTable.getInstance();
    }

    public boolean assembleOperation(String line) throws Exception {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        if (!codeTable.isValidOperation(tokens[0]))
            throw new IllegalArgumentException("UNKNOW OPERATION: " + tokens[0] + " at line " + Assembler.getInstance().getLineCounter() + ".");

        AssembleOperation op = codeTable.getOperation(tokens[0]);
        op.assemble(line);
        return true;
    }
}
