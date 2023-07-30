package assembler.tables.symboltable;

import java.util.LinkedList;

public class Symbol {
    private String identificator;
    private boolean isDeclared;
    private short value;
    private final LinkedList<Integer> usedAt = new LinkedList<>();

    public Symbol (String identificator, boolean isDeclared) {
        this.identificator = identificator;
        this.isDeclared = isDeclared;
    }

    public Symbol(String identificator, boolean isDeclared, short value) {
        this.identificator = identificator;
        this.isDeclared = isDeclared;
        this.value = value;
    }

    public String getIdentificator() {
        return identificator;
    }

    // Is this needed? - Henrique
    /*
    public void setIdentificator(String identificator) {
        this.identificator = identificator;
    }
     */

    public boolean isDeclared() {
        return isDeclared;
    }

    public void setDeclared(boolean declared) {
        isDeclared = declared;
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
