package assembler.mnemonics.arithmetical;

import assembler.Assembler;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;
import assembler.AssembleableOperation;

import java.util.LinkedList;
public class Cmp extends Operation{
    public static final String MNEMONIC = "CMP";
    private final short CMP_AXDX = 0x3BC2;
    private final short CMP_AXCTE = 0x3DFF;
    private final short CMP_AXDIR = 0x3CFF;

    @Override
    public void assemble(String line) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        if (tokens[1].equals(AX_STR)) {
            if (tokens[2].equals(DX_STR)) {
                assembledCode.add(CMP_AXDX);
            }
            else if (AssemblerUtils.isNumericConstant(tokens[2])) {
                assembledCode.add(CMP_AXCTE);
                assembledCode.add(AssemblerUtils.convertNumber(tokens[2]));
            }
            else {
                assembledCode.add(CMP_AXDIR);

                processDirectAddressing(tokens[2]);
            }
        }
    }
}
