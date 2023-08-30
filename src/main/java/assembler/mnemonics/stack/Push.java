package assembler.mnemonics.stack;

import assembler.Assembler;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class Push extends Operation {
    public static final String MNEMONIC = "PUSH";
    private final short PUSH_AX = 0x50FF;
    @Override
    public void assemble( String line ) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        if (tokens[1].equals(AX_STR)) {
            // Pop Ax from Stack
            assembledCode.add(PUSH_AX);
        }
    }
}
