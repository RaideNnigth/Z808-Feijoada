package assembler.mnemonics.arithmetical;

import assembler.Assembler;
import assembler.AssemblerError;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class Add extends Operation {
    public static final String MNEMONIC = "ADD";

    // Gab Bessa: How to define the diferent binary codes for this??
    // Henrique: Here's how baby:
    // Gustavo: I wanna cry
    private final short ADD_AXAX = 0x03c0;
    private final short ADD_AXDX = 0x03c2;
    private final short ADD_AXCTE = 0x04ff;
    private final short ADD_AXDIR = 0x05ff;

    // ADD [] , []

    @Override
    public void assemble(String line) throws AssemblerError {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        if (tokens[1].equals(AX_STR)) {
            // AX + AX
            if (tokens[2].equals(AX_STR)) {
                assembledCode.add(ADD_AXAX);
            }

            // AX + DX
            else if (tokens[2].equals(DX_STR)) {
                assembledCode.add(ADD_AXDX);
            }

            // Check for immediate numeric value
            else if (AssemblerUtils.isNumericConstant(tokens[2])) {
                assembledCode.add(ADD_AXCTE);
                assembledCode.add(AssemblerUtils.convertNumber(tokens[2]));
            }

            // If none above checks, assume it's a label for a variable
            else {
                assembledCode.add(ADD_AXDIR); // Opcode for add with Direct Addressing

                processDirectAddressing(tokens[2]);
            }
        }
    }
}