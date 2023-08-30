package assembler.codeprocessors;

import assembler.AssembleOperation;
import assembler.tables.SegmentTable;
import assembler.utils.AssemblerUtils;

public class DirectiveProcessor {
    private final SegmentTable segmentTable;
    public DirectiveProcessor() {
        segmentTable = SegmentTable.getInstance();
    }

    public boolean assembleDirective(String line) throws Exception {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);
        for ( String token : tokens ) {
            if ( segmentTable.isValidOperation( token ) ) {
                AssembleOperation op = segmentTable.getOperation( token );
                op.assemble( line );
                return true;
            }
        }
        return false;
    }
}