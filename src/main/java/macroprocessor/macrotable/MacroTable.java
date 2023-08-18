package macroprocessor.macrotable;
import java.util.HashMap;

//Singleton class
public class MacroTable {
    private final HashMap<String, Macro> macroTableDefinitions;

    private static MacroTable instance = null;
    private MacroTable() {
        macroTableDefinitions = new HashMap<>();
    }

    public static MacroTable getInstance() {
        if (instance == null) {
            instance = new MacroTable();
        }
        return instance;
    }

    public HashMap<String, Macro> getMacroTableDefinitions() {
        return macroTableDefinitions;
    }

    public boolean macroExists(String macro) {
        return macroTableDefinitions.containsKey(macro);
    }

    public Macro getMacro(String macro) {
        return macroTableDefinitions.get(macro);
    }

    public void declareMacro(String macroName, String macroCode, String[] parameters, int alignmentLevel, Macro parentMacro) {
       Macro newMacro;
        if(parentMacro == null) {
            newMacro = new Macro(macroName, macroCode, parameters, alignmentLevel);
        }
        // Henrique me de um pão
        // Nao - Rick
        else {
            newMacro = new Macro(macroCode, macroCode, parameters, alignmentLevel, parentMacro);
        }

        macroTableDefinitions.put(macroName, newMacro);
    }

    /*
    public void addSymbol(Symbol s) {
        if (!AssemblerUtils.isValidName(s.getIdentification())) {
            Log l = new Log(LogType.ERROR, Assembler.getInstance().getLineCounter(),
                    String.format("%s is not a valid identifier.", s.getIdentification()));
            Logger.getInstance().addLog(l);
        }

        macroTableDefinitions.put(s.getIdentification(), s);
    }
    */

    /*
    public void replaceAllOcorrencesOfDeclaredSymbols() throws UndeclaredSymbol {
        for (Macro macro : macroTableDefinitions.values()) {
            if (macro.isDeclared()) {
                while (!macro.getUsedAt().isEmpty()) {
                    int pos = macro.getUsedAt().pop();
                    Assembler.getInstance().getAssembledCode().set(pos, (short) (macro.getValue() - pos));
                }
            } else {
                throw new UndeclaredMacro(macro);
            }
        }
    }
    ^ Codigo ruim (não fui eu q escrevi) - Henrique
     */

    public void reset() {
        this.macroTableDefinitions.clear();
    }
}
/* Alguém bota isso numa classe eu não tenho permissão
vai tomar no cu - Henrique
// não sei usar virgula, como voce
public class UndeclaredMacro extends Exception {
    public UndeclaredMacro(Macro macro) {
        super("The symbol \"" + macro.getIdentification() + "\" is used but never declared!");
    }
}
*/