package assembler.mnemonics.flow;

import assembler.Assembler;
import assembler.AssemblerError;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class Jnz extends Operation {
    public static final String MNEMONIC = "JNZ";

    private final short JNZ = (short) 0x75FF;

    // JNZ LABEL

    @Override
    public void assemble(String line) throws AssemblerError {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        assembledCode.add(JNZ);
        processJumpAddressing(tokens[1]);

    }
}