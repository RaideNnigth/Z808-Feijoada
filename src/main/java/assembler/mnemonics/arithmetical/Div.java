package assembler.mnemonics.arithmetical;

import assembler.Assembler;
import assembler.mnemonics.Operation;
import assembler.utils.AssemblerUtils;

import java.util.LinkedList;

public class Div extends Operation {
    public static final String MNEMONIC = "DIV";
    private final short DIV_AX = (short)0xF7F0;
    private final short DIV_SI = (short)0xF7F6;
    @Override
    public void assemble( String line ) {
        String[] tokens = AssemblerUtils.decomposeInTokens( line );

        LinkedList<Short> assembledCode = Assembler.getInstance().getAssembledCode();

        if ( tokens[1].equals( AX_STR ) ) {
            // AX / AX
            assembledCode.add( DIV_AX );
        } else if ( tokens[1].equals( SI_STR ) ) {
            // AX / SI
            assembledCode.add( DIV_SI );
        }
    }
}
