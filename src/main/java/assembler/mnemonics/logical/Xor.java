package assembler.mnemonics.logical;

import assembler.Assembler;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class Xor extends Operation {
    public static final String MNEMONIC = "XOR";
    private final short XOR_AXAX = 0x33C0;
    private final short XOR_AXDX = 0x33C2;
    private final short XOR_AXCTE = 0x35FF;
    @Override
    public void assemble( String line ) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        if (tokens[1].equals(AX_STR)) {
            // AX xor AX
            if (tokens[2].equals(AX_STR)) {
                assembledCode.add(XOR_AXAX);
            }
            // AX xor DX
            else if (tokens[2].equals(DX_STR)) {
                assembledCode.add(XOR_AXDX);
            }
            // Check for immediate numeric value
            else if (AssemblerUtils.isNumericConstant(tokens[2])) {
                assembledCode.add(XOR_AXCTE);
                assembledCode.add(AssemblerUtils.convertNumber(tokens[2]));
            }
        }
    }
}
