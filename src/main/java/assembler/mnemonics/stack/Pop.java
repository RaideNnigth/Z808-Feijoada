package assembler.mnemonics.stack;

import assembler.Assembler;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class Pop extends Operation {
    public static final String MNEMONIC = "POP";
    private final short POP_AX = 0x58FF;

    @Override
    public void assemble(String line) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        if (tokens[1].equals(AX_STR)) {
            // AX POP
            assembledCode.add(POP_AX);
        }
    }
}
