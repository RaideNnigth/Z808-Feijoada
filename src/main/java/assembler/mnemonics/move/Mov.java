package assembler.mnemonics.move;

import assembler.Assembler;
import assembler.AssemblerError;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class Mov extends Operation {
    public static final String MNEMONIC = "MOV";

    private final short MOV_DIRAX = (short) 0xA3FF;
    private final short MOV_AXDX = (short) 0x8BD1;
    private final short MOV_DXAX = (short) 0x8BD0;
    private final short MOV_AXCTE = (short) 0xB8FF;
    private final short MOV_AXDIR = (short) 0xA1FF;

    @Override
    public void assemble(String line) throws AssemblerError {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        if (tokens.length != 3)
            throw new IllegalArgumentException("Invalid arguments for instruction 'MOV' at line " + Assembler.getInstance().getLineCounter() + ".");

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        if (tokens[1].equals(AX_STR)) {
            // AX + DX
            if (tokens[2].equals(DX_STR)) {
                assembledCode.add(MOV_AXDX);
            }

            // AX + CTE
            else if (AssemblerUtils.isNumericConstant(tokens[2])) {
                assembledCode.add(MOV_AXCTE);
                assembledCode.add(AssemblerUtils.convertNumber(tokens[2]));
            }

            // AX + DIR
            else {
                assembledCode.add(MOV_AXDIR);
                processDirectAddressing(tokens[2]);
            }
        } else if (tokens[1].equals(DX_STR)) {
            // DX + AX
            if (tokens[2].equals(AX_STR)) {
                assembledCode.add(MOV_DXAX);
            }
        }
        // DIR + AX
        else {
            assembledCode.add(MOV_DIRAX);
            processDirectAddressing(tokens[1]);
        }

    }
}