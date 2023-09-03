package linker.entities;

public class Symbol {

    // Used just on the DefinitionsTable
    // Value can be just R or A indicating if it is absolute or relative
    // if absolute just copy the value to the bin file
    // if relative add the value to the address of the module
    private String relocability;
    private short value;
    private String moduleName;

    public Symbol( ) {
        this.relocability = null;
        this.value = 0;
        this.moduleName = null;
    }

    public String getRelocability() {
        return relocability;
    }

    public short getValue() {
        return value;
    }
    public String getModuleName() {
        return moduleName;
    }

    public void setRelocability(String relocability) {
        this.relocability = relocability;
    }

    public void setValue(short value) {
        this.value = value;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
