package assembler.mnemonics.flow;

import assembler.Assembler;
import assembler.mnemonics.Operation;

import java.util.LinkedList;

public class Hlt extends Operation {
    public static final String MNEMONIC = "HLT";
    private final short HLT = (short) 0x0000;

    @Override
    public void assemble(String line) throws Exception {
        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();
        assembledCode.add(HLT);
    }
}
