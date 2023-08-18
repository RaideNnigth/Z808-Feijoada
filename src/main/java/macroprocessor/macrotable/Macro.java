package macroprocessor.macrotable;

public class Macro {
    private final String macroName;
    private final String macroCode;
    private final String[] paramters;
    private final int alignmentLevel;
    private Macro parentMacro;

    public Macro(String macroName, String macroCode, String[] parameters, int alignmentLevel) {
        this.macroName = macroName;
        this.macroCode = macroCode;
        this.paramters = parameters;
        this.alignmentLevel = alignmentLevel;
    }

    public Macro(String macroName, String macroCode, String[] parameters, int alignmentLevel, Macro parentMacro) {
        this.macroName = macroName;
        this.macroCode = macroCode;
        this.paramters = parameters;
        this.alignmentLevel = alignmentLevel;
        this.parentMacro = parentMacro;
    } 

    public String getIdentification() {
        return macroName;
    }

    public String getMacroCode() {
        return macroCode;
    }

    public String[] getParamters() { return paramters;}

    public Macro getParentMacro() {return parentMacro;}

    public int getAlignmentLevel() {return alignmentLevel;}

}
