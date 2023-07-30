package assembler.tables.symboltable;

import java.util.LinkedList;

public class Symbol {
    private String identificator;
    private boolean isDeclared;
    private final LinkedList<Integer> usedAt;

    public Symbol (String identificator, boolean isDeclared) {
        this.identificator = identificator;
        this.isDeclared = isDeclared;
        usedAt = new LinkedList<>();
    }

    public String getIdentificator() {
        return identificator;
    }

    public void setIdentificator(String identificator) {
        this.identificator = identificator;
    }

    public boolean isDeclared() {
        return isDeclared;
    }

    public void setDeclared(boolean declared) {
        isDeclared = declared;
    }

    public LinkedList<Integer> getUsedAt() {
        return usedAt;
    }
}
