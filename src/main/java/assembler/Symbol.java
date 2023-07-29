package assembler;

import java.util.ArrayList;

public class Symbol {
    private String identificator;
    private boolean isDeclared;
    private final ArrayList<Short> usedAt;

    public Symbol (String identificator, boolean isDeclared) {
        this.identificator = identificator;
        this.isDeclared = isDeclared;
        usedAt = new ArrayList<>();
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

    public ArrayList<Short> getUsedAt() {
        return usedAt;
    }
}
