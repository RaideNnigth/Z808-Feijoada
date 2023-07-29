package assembler.mnemonics;

import assembler.SymbolTable;

public interface Operation {
    void assembleOperation(String line, SymbolTable st);
}
