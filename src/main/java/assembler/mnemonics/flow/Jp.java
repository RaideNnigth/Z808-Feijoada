package assembler.mnemonics.flow;

import assembler.Assembler;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class Jp extends Operation {
    public static final String MNEMONIC = "JP";

    private final short JP = (short) 0x7AFF;

    // JP LABEL

    @Override
    public void assemble(String line) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        assembledCode.add(JP);
        processDirectAddressing(tokens[1]);

    }
}