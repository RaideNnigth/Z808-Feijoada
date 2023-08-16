package assembler.mnemonics.flow;

import assembler.Assembler;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class Jmp extends Operation {
    public static final String MNEMONIC = "JMP";

    // Daniel: How to access internet without paying??
    // Ferrao: Here's how baby:
    private final short JMP = (short) 0xEBFF;

    // JMP LABEL

    @Override
    public void assemble(String line) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        assembledCode.add(JMP);
        processJumpAddressing(tokens[1]);
    }
}