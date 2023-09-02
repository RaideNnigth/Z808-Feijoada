package linker.tables;

/*
 * This class is responsible for storing the definitions table for file
 * example: P1.asm --> definitions table P1
 *          P2.asm --> definitions table P2
 *
 * Symbol is the name that it is called from external file
 * definition is the address of the symbol in bin file for each,
 * define it with public
 *
 * Relocability is to indicate if the symbol is relocatable or not ( R or A )
 *
 * Value is the address for the symbol in the file (moduleName:0001), 0001 is the address in short
 * */

import linker.entities.Symbol;
import linker.tables.exceptions.AlreadyDeclaredPublicSymbolException;
import linker.tables.exceptions.NotDeclaredPublicSymbolException;

import java.util.HashMap;

public class DefinitionsTable {

    private final HashMap<String, Symbol> definitionsTable = new HashMap<>();

    public void addDefinition(String symbolName, Symbol s) throws AlreadyDeclaredPublicSymbolException {
        if (this.definitionsTable.containsKey(symbolName)) {
            throw new AlreadyDeclaredPublicSymbolException("Such symbol is already declared, please check your code, symbolName:" + symbolName);
        }
        this.definitionsTable.put(symbolName, s);
    }

    public HashMap<String, Symbol> getDefinitionsTable() {
        return definitionsTable;
    }

    public Symbol getSymbol(String symbolName) throws NotDeclaredPublicSymbolException {
        if (!this.definitionsTable.containsKey(symbolName)) {
            throw new NotDeclaredPublicSymbolException("Such symbol is not declared, please check your code, symbolName:" + symbolName);
        }
        return this.definitionsTable.get(symbolName);
    }

}
