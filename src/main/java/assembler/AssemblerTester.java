package assembler;

import assembler.codeprocessors.OperationProcessor;

public class AssemblerTester {
    public static void main(String[] args) {
        OperationProcessor operationProcessor = new OperationProcessor();

        operationProcessor.assembleOperation("ADD AX,20");

    }
}
