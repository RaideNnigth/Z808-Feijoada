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

    public Symbol( String signal, short occurrence ) {
        this.signal = signal;
        this.occurrence = occurrence;
        this.relocability = null;
    }

    public Symbol( String signal, short occurrence, String relocability ) {
        this.signal = signal;
        this.occurrence = occurrence;
        this.relocability = relocability;
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
