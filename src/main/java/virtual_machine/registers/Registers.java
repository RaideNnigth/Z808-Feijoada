package virtual_machine.registers;

public enum Registers {
    AX("AX: "), DX("DX: "), IP("IP: "), SI("SI: "),
    SP("SP: "), SR("SR: "), CS("CS: "), DS("DS: "),
    SS("SS: ");

    private String label;

    Registers(String id) {
        label = id;
    }

    public String getlabel() {
        return this.label;
    }
}
