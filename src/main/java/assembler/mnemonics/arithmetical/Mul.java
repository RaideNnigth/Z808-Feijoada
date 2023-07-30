package assembler.mnemonics.arithmetical;

import assembler.Assembler;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class Mul extends Operation {
    public static final String MNEMONIC = "MUL";
    private final short MUL_AX = (short)0xF7E0;
    private final short MUL_SI = (short)0xF7E6;
    @Override
    public void assemble( String line ) {
        String[] tokens = AssemblerUtils.decomposeInTokens( line );

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        if ( tokens[1].equals( AX_STR ) ) {
            // AX * AX
            assembledCode.add( MUL_AX );
        } else if ( tokens[1].equals( SI_STR ) ) {
            // AX * SI
            assembledCode.add( MUL_SI );
        }
    }
}
