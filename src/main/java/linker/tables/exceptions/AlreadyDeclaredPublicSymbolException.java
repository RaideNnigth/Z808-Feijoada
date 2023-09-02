package linker.tables.exceptions;

public class AlreadyDeclaredPublicSymbolException extends Exception {
    public AlreadyDeclaredPublicSymbolException(String msg) {
        super(msg);
    }
}
