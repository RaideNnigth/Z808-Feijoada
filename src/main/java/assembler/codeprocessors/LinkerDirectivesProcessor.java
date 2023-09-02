package assembler.codeprocessors;

import assembler.utils.AssemblerUtils;

import java.util.HashMap;

public class LinkerDirectivesProcessor {
    private static HashMap<String, String> linkerDirectives = new HashMap<>();

    public boolean processLinkerDirective(String line) {
        String[] tokens = AssemblerUtils.decomposeInTokens(line);

        // do processing...

        return false;
    }
}
