package assembler.mnemonics.arithmetical;

import assembler.Assembler;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class Sub extends Operation {
        public static final String MNEMONIC = "SUB";
        private final short SUB_AXAX = 0x2BC0;
        private final short SUB_AXDX = 0x2BC2;
        private final short SUB_AXCTE = 0x2CFF;
        private final short SUB_AXDIR = 0x2DFF;
    @Override
    public void assemble(String line) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        if (tokens[1].equals(AX_STR)) {
            // AX - AX
            if (tokens[2].equals(AX_STR)) {
                assembledCode.add(SUB_AXAX);
            }

            // AX - DX
            else if (tokens[2].equals(DX_STR)) {
                assembledCode.add(SUB_AXDX);
            }

            // Check for immediate numeric value
            else if (AssemblerUtils.isNumericConstant(tokens[2])) {
                assembledCode.add(SUB_AXCTE);
                assembledCode.add(AssemblerUtils.convertNumber(tokens[2]));
            }

            // If none above checks, assume it's a label for a variable
            else {
                assembledCode.add(SUB_AXDIR); // Opcode for sub with Direct Addressing
                processDirectAddressing(tokens[2]);
            }
        }
    }
}