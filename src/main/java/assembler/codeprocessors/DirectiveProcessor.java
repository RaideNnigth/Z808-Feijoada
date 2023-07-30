package assembler.codeprocessors;

import assembler.Operation;
import assembler.directives.*;

import java.util.HashMap;

public class DirectiveProcessor {
    // Hashmap for directives
    private final HashMap<String, Operation> directivesTable = new HashMap<>();
    public DirectiveProcessor() {
        // Look at page 72 for more details
        directivesTable.put("END", new End());
        directivesTable.put("SEGMENT", new Segment());
        directivesTable.put("ENDS", new Ends()); // End of segment
        directivesTable.put("DW", new Dw());
        directivesTable.put("EQU", new Equ());
        directivesTable.put("ORG", new Org());
        directivesTable.put("OFFSET", new Offset());
        directivesTable.put("ASSUME", new Assume()); // Meio inutil
        directivesTable.put("PROC", new Proc());
        directivesTable.put("ENDP", new Endp());
    }
    public void assembleDirective(String line) {
        String directive = line.split(" ")[0];
        Operation op = directivesTable.get(directive);

        // If this is not a directive
        if (op == null)
            return;

        op.assemble(line);
    }
}
