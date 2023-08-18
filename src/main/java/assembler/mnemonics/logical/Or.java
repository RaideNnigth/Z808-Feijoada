package assembler.mnemonics.logical;

import assembler.Assembler;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class Or extends Operation {
    public static final String MNEMONIC = "OR";
    private final short OR_AXAX = 0x0BC0;
    private final short OR_AXDX = 0x0BC2;
    private final short OR_AXCTE = 0x0DFF;

    @Override
    public void assemble(String line) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        if (tokens[1].equals(AX_STR)) {
            if (tokens[2].equals(AX_STR)) {
                assembledCode.add(OR_AXAX);
            }
            else if (tokens[2].equals(DX_STR)) {
                assembledCode.add(OR_AXDX);
            }
            else if (AssemblerUtils.isNumericConstant(tokens[2])) {
                assembledCode.add(OR_AXCTE);
                assembledCode.add(AssemblerUtils.convertNumber(tokens[2]));
            }
        }

    }
}
