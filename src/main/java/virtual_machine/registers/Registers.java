package virtual_machine.registers;

public enum Registers {
    AX("AX: "), DX("DX: "), IP("IP: "), SI("SI: "),
    SP("SP: "), CS("CS: "), DS("DS: "), SS("SS: "),
    SR("SR: ");

    private String label;

    Registers(String id) {
        this.label = id;
    }

    public String getLabel() {
        return this.label;
    }
}
