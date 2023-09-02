package linker.tables.exceptions;

public class UndaclaredModuleNameException extends Exception {
    public UndaclaredModuleNameException() {
        super("The first line in the file must be the module name!");
    }
}
