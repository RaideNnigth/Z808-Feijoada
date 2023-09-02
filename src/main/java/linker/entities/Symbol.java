package linker.entities;

public class Symbol {
    private final String signal;

    // Ocurrence address of the symbol occurrence in the bin file
    private final short occurrence;

    // Used just on the DefinitionsTable
    // Value can be just R or A indicating if it is absolute or relative
    // if absolute just copy the value to the bin file
    // if relative add the value to the address of the module
    private final String relocability;

    //Usage on GlobalSymbolTable
    private final String definition;
    private final short absoluteValue;
    private final String moduleNameAddress;

    public Symbol( String signal, short occurrence ) {
        this.signal = signal;
        this.occurrence = occurrence;
        this.relocability = null;
        this.definition = null;
        this.absoluteValue = 0;
        this.moduleNameAddress = null;
    }

    public Symbol( String signal, short occurrence, String relocability, short value ) {
        this.signal = signal;
        this.occurrence = occurrence;
        this.relocability = relocability;
        this.definition = null;
        this.absoluteValue = value;
        this.moduleNameAddress = null;
    }

    public Symbol( String signal, short occurrence, String relocability, String moduleNameAddress ) {
        this.signal = signal;
        this.occurrence = occurrence;
        this.relocability = relocability;
        this.definition = null;
        this.absoluteValue = 0;
        this.moduleNameAddress = moduleNameAddress;
    }

    public Symbol( String signal, String relocability, String definition, short value ) {
        this.signal = signal;
        this.occurrence = 0;
        this.relocability = relocability;
        this.definition = definition;
        this.absoluteValue = value;
        this.moduleNameAddress = null;
    }

    public Symbol( String signal, String relocability, String definition, String moduleNameAddress ) {
        this.signal = signal;
        this.occurrence = 0;
        this.relocability = relocability;
        this.definition = definition;
        this.absoluteValue = 0;
        this.moduleNameAddress = moduleNameAddress;
    }

    public String getSignal() {
        return signal;
    }
    public short getOccurrence() {
        return occurrence;
    }
    public String getRelocability() {
        return relocability;
    }
}
