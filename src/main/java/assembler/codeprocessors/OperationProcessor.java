package assembler.codeprocessors;

import assembler.Operation;
import assembler.tables.CodeTable;
import assembler.utils.AssemblerUtils;

public class OperationProcessor {
    private CodeTable codeTable;

    public OperationProcessor() {
        codeTable = CodeTable.getInstance();
    }

    public boolean isOperation(String line) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        return codeTable.isValidOperation(tokens[0]);
    }

    public void assembleOperation(String line) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        Operation op = codeTable.getOperation(tokens[0]);
        op.assemble(line);
    }
}
