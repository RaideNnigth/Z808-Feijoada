package assembler.mnemonics.flow;

import assembler.Assembler;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class Int extends Operation {
    public static final String MNEMONIC = "INT";

    private final short INT = (short) 0xCDFF;

    // INT CTE

    @Override
    public void assemble(String line) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        if (AssemblerUtils.isNumericConstant(tokens[1])) {
            assembledCode.add(INT);
            assembledCode.add(AssemblerUtils.convertNumber(tokens[1]));
        }
    }
}
