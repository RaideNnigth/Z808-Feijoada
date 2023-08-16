package assembler.tables.datatable;


import assembler.Assembler;
import assembler.tables.datatable.dataitemtype.Type;
import assembler.tables.datatable.dataitemtype.TypeFactory;

import java.util.ArrayList;
import java.util.LinkedList;

public class DataItem {
    private final String identification;
    private boolean isDeclared;
    private Type type;
    private ArrayList<Byte> initialValue;
    private short address;
    private final LinkedList<Integer> usedAt = new LinkedList<>();

    // Used when declarind DataItem in data segment
    public DataItem(String identification, boolean isDeclared, String dataType, String initialValue, short address) {
        this.identification = identification;
        this.isDeclared = isDeclared;
        this.address = address;

        setTypeAndInitialValue(dataType, initialValue);
    }

    // Used when implicit declaring DataItem in code segment
    public DataItem(String identification, boolean isDeclared) {
        this.identification = identification;
        this.isDeclared = isDeclared;
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

    public ArrayList<Byte> getInitialValue() {
        return initialValue;
    }

    public short getSize() {
        return (short) type.getSizeInBytes();
    }

    public short getAddress() {
        return address;
    }

    public void setDeclared(boolean declared) {
        isDeclared = declared;
    }

    public void setTypeAndInitialValue(String type, String initialValue) {
        this.type = TypeFactory.inferType(type);
        this.initialValue = this.type.parseInitialValue(initialValue);
        Assembler.getInstance().getAssembledData().addAll(this.initialValue);
    }

    public void setAddress(short address) {
        this.address = address;
    }
}
