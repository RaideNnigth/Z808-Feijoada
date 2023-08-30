package assembler.mnemonics.logical;

import assembler.Assembler;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class And extends Operation {
    public static final String MNEMONIC = "AND";
    private final short AND_AXAX = 0x23C0;
    private final short AND_AXDX = 0x23C2;
    private final short AND_AXCTE = 0x25FF;
    @Override
    public void assemble( String line ) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        if (tokens[1].equals(AX_STR)) {
            // AX && AX
            if (tokens[2].equals(AX_STR)) {
                assembledCode.add(AND_AXAX);
            }
            // AX && DX
            else if (tokens[2].equals(DX_STR)) {
                assembledCode.add(AND_AXDX);
            }
            // Check for immediate numeric value
            else if (AssemblerUtils.isNumericConstant(tokens[2])) {
                assembledCode.add(AND_AXCTE);
                assembledCode.add(AssemblerUtils.convertNumber(tokens[2]));
            }
        }
    }
}
