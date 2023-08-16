package assembler.mnemonics.flow;

import assembler.Assembler;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class Je extends Operation {
    public static final String MNEMONIC = "JE";

    private final short JE = (short) 0x74FF;

    // JE LABEL

    @Override
    public void assemble(String line) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        assembledCode.add(JE);
        processJumpAddressing(tokens[1]);

    }
}