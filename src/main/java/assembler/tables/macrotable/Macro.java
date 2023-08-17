package assembler.tables.macrotable;
import java.lang.reflect.Array;
import java.lang.reflect.Parameter;
import java.util.LinkedList;

public class Macro {
    private final String macroName;
    private Array paramName;
    private String macroCode;
    private final boolean isDeclared;
    private final LinkedList<Integer> usedAt = new LinkedList<>();

    public Macro(String macroName, boolean isDeclared) {
        this.macroName = macroName;
        this.isDeclared = isDeclared;
    }

    public Macro(String macroName, boolean isDeclared, String value) {
        this.macroName = macroName;
        this.isDeclared = isDeclared;
        this.macroCode = value;
    }


    public String getIdentification() {
        return macroName;
    }

    public boolean isDeclared() {
        return isDeclared;
    }

    public LinkedList<Integer> getUsedAt() {
        return usedAt;
    }

    public String getValue() {
        return macroCode;
    }

    public void setValue(String macroCode) {
        this.macroCode = macroCode;
    }

}
