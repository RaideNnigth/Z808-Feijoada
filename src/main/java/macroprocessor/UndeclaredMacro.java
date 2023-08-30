package macroprocessor;

public class UndeclaredMacro extends Exception {
    public UndeclaredMacro(String nameMacro) {
        super("UNDECLARED MACRO: " + nameMacro);
    }
}
