package assembler.tables.symboltable;

public class UndeclaredSymbol extends Exception {
    public UndeclaredSymbol(Symbol s) {
        super("The symbol \"" + s.getIdentificator() + "\" is used but never declared!");
    }
}
