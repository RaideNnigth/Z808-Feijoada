package assembler.tables.symboltable;

import java.util.LinkedList;

public class Symbol {
    private final String indentification;
    private final boolean isDeclared;
    private short value;
    private final LinkedList<Integer> usedAt = new LinkedList<>();

    public Symbol(String identification, boolean isDeclared) {
        this.indentification = identification;
        this.isDeclared = isDeclared;
    }

    public Symbol(String identification, boolean isDeclared, short value) {
        this.indentification = identification;
        this.isDeclared = isDeclared;
        this.value = value;
    }


    public String getIdentification() {
        return indentification;
    }

    public boolean isDeclared() {
        return isDeclared;
    }

    public LinkedList<Integer> getUsedAt() {
        return usedAt;
    }

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }

}
