package assembler.mnemonics.flow;

import assembler.Assembler;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class Call extends Operation {
    public static final String MNEMONIC = "CALL";

    private final short CALL = (short) 0xE8FF;

    // CALL LABEL

    @Override
    public void assemble(String line) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        assembledCode.add(CALL);
        processJumpAddressing(tokens[1]);

    }
}