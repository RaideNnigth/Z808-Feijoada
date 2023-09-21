package linker.entities;

public class LinkerSymbol {

    // Used just on the DefinitionsTable
    // Value can be just R or A indicating if it is absolute or relative
    // if absolute just copy the value to the bin file
    // if relative add the value to the address of the module

    public enum RELOCABILITY {
        R, A
    }

    private RELOCABILITY relocability;
    private short value;
    private String moduleName;

    public LinkerSymbol( ) {
        this.relocability = null;
        this.value = 0;
        this.moduleName = null;
    }

    public RELOCABILITY getRelocability() {
        return relocability;
    }

    public short getValue() {
        return value;
    }
    public String getModuleName() {
        return moduleName;
    }

    public void setRelocability(RELOCABILITY relocability) {
        this.relocability = relocability;
    }

    public void setValue(short value) {
        this.value = value;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
