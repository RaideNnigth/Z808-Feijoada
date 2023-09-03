package linker.entities;

public class Symbol {

    // Used just on the DefinitionsTable
    // Value can be just R or A indicating if it is absolute or relative
    // if absolute just copy the value to the bin file
    // if relative add the value to the address of the module
    private final String relocability;

    //Usage on GlobalSymbolTable
    private final String definition;
    private final short absoluteValue;
    private final String moduleNameAddress;

    public Symbol( ) {
        this.relocability = null;
        this.definition = null;
        this.absoluteValue = 0;
        this.moduleNameAddress = null;
    }

    public Symbol(String relocability, short value ) {
        this.relocability = relocability;
        this.definition = null;
        this.absoluteValue = value;
        this.moduleNameAddress = null;
    }

    public Symbol(String relocability, String moduleNameAddress ) {
        this.relocability = relocability;
        this.definition = null;
        this.absoluteValue = 0;
        this.moduleNameAddress = moduleNameAddress;
    }

    public Symbol( String relocability, String definition, short value ) {
        this.relocability = relocability;
        this.definition = definition;
        this.absoluteValue = value;
        this.moduleNameAddress = null;
    }

    public Symbol( String relocability, String definition, String moduleNameAddress ) {
        this.relocability = relocability;
        this.definition = definition;
        this.absoluteValue = 0;
        this.moduleNameAddress = moduleNameAddress;
    }

    public String getRelocability() {
        return relocability;
    }

    public String getDefinition() {
        return definition;
    }
    public short getAbsoluteValue() {
        return absoluteValue;
    }
    public String getModuleNameAddress() {
        return moduleNameAddress;
    }
}
