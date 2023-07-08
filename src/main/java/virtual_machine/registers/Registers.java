package virtual_machine.registers;

public enum Registers {
    AX("AX: "), DX("DX: "), IP("IP: "), SI("SI: "), SP("SP: "), SR("SR: ");

    private String GUILabel;

    Registers(String label) {
        this.GUILabel = label;
    }

    public String getGUILabel() {
        return this.GUILabel;
    }
}
