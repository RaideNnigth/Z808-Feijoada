package assembler.directives;

import assembler.AssembleableOperation;
import assembler.utils.AssemblerUtils;
import assembler.Assembler;

public class Segment implements AssembleableOperation {
    public static final String MNEMONIC = "SEGMENT";

    @Override
    public void assemble(String line) throws Exception {
        Assembler assembler = Assembler.getInstance();

        String name;
        String[] tokens = AssemblerUtils.decomposeInTokens(line);
        name = tokens[0];

        if (name.equals(".DATA")) {
            if (assembler.isDataSegmentFound())
                throw new Exception("Duplicate data segment name");
            assembler.dataSegmentFound();
            assembler.setSegment(name);
        } else if (name.equals(".CODE")) {
            if (assembler.isCodeSegmentFound())
                throw new Exception("Duplicate code segment name");
            assembler.codeSegmentFound();
            assembler.setSegment(name);
        } else
            throw new Exception("Invalid segment name");
    }
}
