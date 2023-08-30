package assembler.mnemonics.logical;

import assembler.Assembler;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class Not extends Operation {
    public static final String MNEMONIC = "NOT";
    private final short NOT_AX = (short)0xF7D0;
    @Override
    public void assemble( String line ) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        if (tokens[1].equals(AX_STR)) {
            // AX NOT
            assembledCode.add(NOT_AX);
        }
    }
}
