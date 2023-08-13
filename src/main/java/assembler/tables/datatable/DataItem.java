package assembler.tables.datatable;

import java.util.LinkedList;

public class DataItem {
    private final String identification;
    private final boolean isDeclared;
    private final boolean isVariable;
    private final short size;
    private final short value;
    private final short address;
    private final LinkedList<Integer> usedAt = new LinkedList<>();

    public DataItem(String identification, boolean isDeclared, boolean isVariable, short size, short value, short address) {
        this.identification = identification;
        this.isDeclared = isDeclared;
        this.isVariable = isVariable;
        this.size = size;
        this.value = value;
        this.address = address;
    }

    public String getIdentification() {
        return identification;
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

    public boolean getIsVariable() {
        return isVariable;
    }

    public short getSize() {
        return size;
    }

    public short getAddress() {
        return address;
    }
}
