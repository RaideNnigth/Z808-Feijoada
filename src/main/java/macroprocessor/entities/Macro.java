package macroprocessor.entities;

import macroprocessor.UndeclaredMacro;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Macro {
    private final String macroName;
    private final LinkedList<String> macroCode;
    private final ArrayList<String> formalParameters;
    private final boolean isNamedByUser;

    public Macro(String macroName, LinkedList<String> macroCode, ArrayList<String> formalParameters, boolean isNamedByUser) {
        this.macroName = macroName;
        this.formalParameters = (ArrayList<String>) formalParameters.clone();
        this.isNamedByUser = isNamedByUser;
        this.macroCode = (LinkedList<String>) macroCode.clone();
    }

    public Macro(String macroName, boolean isNamedByUser) {
        this.macroName = macroName;
        this.isNamedByUser = isNamedByUser;
        this.formalParameters = new ArrayList<>(0);
        this.macroCode = new LinkedList<>();
    }

    public String getIdentification() {
        return macroName;
    }

    public LinkedList<String> getMacroCode() {
        return macroCode;
    }

    public ArrayList<String> getFormalParameters() {
        return formalParameters;
    }

    public boolean isNamedByUser() {
        return isNamedByUser;
    }

    /**
     * Will expand this macro code using given formal parameters.
     *
     * @param realParameters an ArrayList containing the real parameters to replace in the code
     * @return a LinkedList containing the expanded code
     */
    public LinkedList<String> processRealParameters(ArrayList<String> realParameters) throws UndeclaredMacro {
        if (macroCode.isEmpty()) {
            throw new UndeclaredMacro(this.macroName);
        }

        // Validate parameters
        if (realParameters.size() != formalParameters.size()) {
            throw new IllegalArgumentException("INVALID PARAMETERS FOR MACRO " + macroName);
        }

        LinkedList<String> processedCode = new LinkedList<>();

        // Regex pattern and matcher setup
        final String PARAM_REGEX = "#\\d";
        Pattern p = Pattern.compile(PARAM_REGEX);
        Matcher m;

        for (String line : macroCode) {
            m = p.matcher(line);

            // Process line
            while (m.find()) {
                int index = Integer.parseInt(line.substring(m.start() + 1, m.end()));
                line = line.replaceFirst(PARAM_REGEX, String.valueOf(realParameters.get(index)));
                m = p.matcher(line);
            }

            processedCode.add(line);
        }

        return processedCode;
    }
}