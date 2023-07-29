package assembler;

import java.util.List;

public class OperationProcessor {
    private CodeTable codeTable;
    private SymbolTable st;
    private List<Short> assembledCode;

    public OperationProcessor(SymbolTable st, List<Short> assembledCode) {
        codeTable = CodeTable.getInstance();
        this.st = st;
        this.assembledCode = assembledCode;
    }

    public boolean isOperation(String line) {
        return codeTable.isValidOperation(line);
    }

    public void assembleOperation(String line) {
        var op = codeTable.getOperation(line.split(" ")[0]);

        op.assembleOperation(line, st, assembledCode);
    }
}
