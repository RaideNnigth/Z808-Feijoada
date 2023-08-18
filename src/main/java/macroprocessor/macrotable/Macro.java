package macroprocessor.macrotable;

public class Macro {
    private final String macroName;
    private String macroCode;
    private final String[] parameters;
    private Macro parentMacro;
    private boolean wasCalled = false;

    public Macro(String macroName, String[] parameters) {
        this.macroName = macroName;
        this.parameters = parameters;
    }

    public Macro(String macroName, String macroCode, String[] parameters) {
        this.macroName = macroName;
        this.macroCode = macroCode;
        this.parameters = parameters;
    }

    public Macro(String macroName, String macroCode, String[] parameters, Macro parentMacro) {
        this.macroName = macroName;
        this.macroCode = macroCode;
        this.parameters = parameters;
        this.parentMacro = parentMacro;
    } 

    public String getIdentification() {
        return macroName;
    }

    public String getMacroCode() {
        return macroCode;
    }

    public String[] getParameters() { return parameters;}

    public Macro getParentMacro() {return parentMacro;}

    public void setWasCalled() { this.wasCalled = true; }

    public boolean getWasCalled() { return this.wasCalled; }

    public void setMacroCode(String macroCode) { this.macroCode = macroCode; }

    public void setParentMacro(Macro parentMacro) { this.parentMacro = parentMacro; }

}
