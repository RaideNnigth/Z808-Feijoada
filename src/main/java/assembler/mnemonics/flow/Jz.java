package assembler.mnemonics.flow;

import assembler.Assembler;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class Jz extends Operation {
    public static final String MNEMONIC = "JZ";

    private final short JZ = (short) 0x74FF;

    // JNZ LABEL

    @Override
    public void assemble(String line) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        assembledCode.add(JZ);
        processJumpAddressing(tokens[1]);

    }
}