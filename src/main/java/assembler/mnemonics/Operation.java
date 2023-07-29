package assembler.mnemonics;

import assembler.SymbolTable;

import java.util.List;

public interface Operation {
    void assembleOperation(String line, SymbolTable st, List<Short> assembledCode);
}
