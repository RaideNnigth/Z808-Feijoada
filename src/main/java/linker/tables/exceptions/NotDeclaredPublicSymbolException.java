package linker.tables.exceptions;

public class NotDeclaredPublicSymbolException extends Exception{
    public NotDeclaredPublicSymbolException(String msg) {
        super(msg);
    }
}
