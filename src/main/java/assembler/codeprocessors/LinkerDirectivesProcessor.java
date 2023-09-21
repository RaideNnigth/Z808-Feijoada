package assembler.codeprocessors;

import assembler.AssembleOperation;
import assembler.linkerdirectives.Extrn;
import assembler.linkerdirectives.Name;
import assembler.linkerdirectives.Public;
import assembler.utils.AssemblerUtils;

import java.util.HashMap;

public class LinkerDirectivesProcessor {
    private final HashMap<String, AssembleOperation> linkerDirectives = new HashMap<>();

    public LinkerDirectivesProcessor() {
        linkerDirectives.put(Public.MNEMONIC, new Public());
        linkerDirectives.put(Name.MNEMONIC, new Name());
        linkerDirectives.put(Extrn.MNEMONIC, new Extrn());
    }

    public boolean processLinkerDirective(String line) throws Exception {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        var op = linkerDirectives.get(tokens[0]);

        if(op != null) {
            op.assemble(line);
            return true;
        }

        return false;
    }
}
