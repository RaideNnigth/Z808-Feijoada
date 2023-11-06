package macroprocessor;

import macroprocessor.entities.Macro;
import assembler.utils.AssemblerUtils;
import macroprocessor.utils.MacroUtils;
import macroprocessor.utils.PairList;

import logger.Logger;
import logger.Log;
import logger.LogType;

import java.io.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class MacroProcessor {
    // Macro processor
    private final HashMap<String, Macro> macroTable;
    private int currentMacroNestLevel = 0;

    // Lists of lines
    private final LinkedList<String> inputLines;
    private final LinkedList<String> outputLines;

    // Handling files utils
    private String currentLine;
    private int lineCounter;

    // Macros defines
    private static final String MACRODEF = "MACRODEF";
    private static final String MACROEND = "ENDM";
    private static final String MACROCALL = "CALLM";

    // Singleton stuff
    private static MacroProcessor instance = null;

    private MacroProcessor() {
        macroTable = new HashMap<>();
        inputLines = new LinkedList<>();
        outputLines = new LinkedList<>();
    }

    public static MacroProcessor getInstance() {
        if (instance == null) {
            instance = new MacroProcessor();
        }
        return instance;
    }

    /**
     * Process macros from assembly source file and writes the expanded code to a new file with .pre extension.
     *
     * @param pathToProgram
     * @return path to intermediate file
     * @throws Exception in case of bad sintax or undeclared macro
     */
    public String parseMacros(String pathToProgram) throws Exception {
        lineCounter = 0;
        Logger.getInstance().reset();

        // Read entire file to linked list ignoring comments and blank lines
        try {
            FileReader fileReader = new FileReader(pathToProgram);
            BufferedReader fileIO = new BufferedReader(fileReader);

            for (String s : fileIO.lines().toList()) {
                s = s.trim();
                if (!s.isEmpty()) {
                    if (s.trim().charAt(0) != ';') {
                        this.inputLines.add(s);
                    }
                }
            }

            // Close file readers
            fileReader.close();
            fileIO.close();

            // Read first line
            currentLine = inputLines.isEmpty() ? "" : inputLines.pop();

            // Start parsing in MODE 1 -> Copy mode
            while (!currentLine.isEmpty()) {
                // Decompose in tokens
                String[] tokens = AssemblerUtils.decomposeInTokens(currentLine);

                // Check if it's a macro declaration or macro call
                if (tokens.length > 1 && tokens[1].equalsIgnoreCase(MACRODEF)) {
                    // Macro declaration
                    parseMacroDeclaration();
                } else if (tokens[0].equalsIgnoreCase(MACROCALL)) {
                    // Macro call (expand)
                    parseMacroCall();
                } else {
                    // Copy line to output (no macro declaration nor call)
                    outputLines.add(currentLine);
                }

                // Get next line
                currentLine = inputLines.isEmpty() ? "" : inputLines.pop();
            }
        } catch (IOException e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "MacroParser: Error while reading " + pathToProgram + " file: " + e.getMessage()));
            resetMacroProcessor();
            throw e;
        } catch (Exception e) {
            resetMacroProcessor();
            Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, e.getMessage()));
            throw new MacroProcessorError();
        }

        // Removes ".asm" extension and append ".pre"
        pathToProgram = pathToProgram.replace(".asm", ".pre");

        // Writing intermediate file (this will be sent to Assembler)
        OutputStream outputStream = new FileOutputStream(pathToProgram);
        DataOutputStream dataOutStream = new DataOutputStream(outputStream);

        try {
            for (String line : outputLines)
                dataOutStream.writeBytes(line + "\n");
        } catch (IOException e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "MacroParser: Error while writing " + pathToProgram + "file: " + e.getMessage()));
            resetMacroProcessor();
            throw e;
        } finally {
            outputStream.close();
            dataOutStream.close();
            resetMacroProcessor();
        }

        // Log success message
        Logger.getInstance().addLog(new Log(LogType.INFO, 0, "Macros expanded!"));

        return pathToProgram;
    }

    /**
     * Parse macro declaration and add it to macro table.
     *
     * @throws Exception
     */
    private void parseMacroDeclaration() throws Exception {
        // Get all tokens in line
        String[] tokens = AssemblerUtils.decomposeInTokens(this.currentLine);

        // Increment nest level to 1
        currentMacroNestLevel += 1;

        // Ai gustavo no cu nao - Ferrao
        // no cu sim! - Gustavo
        // Cala a boca e senta - Bessa
        // Semata - Nicolas
        //Vão se fuder - Vinigperuzzi (Monitor de SO "vinigperuzzi.github.io/PersonalPage" [Eu sei de tudo e estou em todos os lugares, Ferrão!!])

        // Get macro name
        String outerMacroName = tokens[0];

        // Validate name if this macro was defined by user (it will be named by user if it's not already in macro table)
        if (!macroTable.containsKey(outerMacroName) && !MacroUtils.isValidMacroName(outerMacroName)) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "INVALID MACRO NAME: " + outerMacroName));
            throw new MacroProcessorError();
        }

        // Get formal parameters if they exist
        ArrayList<String> formalParameters = extractParametersFromMacro(tokens);
        // Ignore list (for nested macros)
        PairList<String, Integer> ignoreList = new PairList<>();

        // Create linked list to store macro code
        LinkedList<String> macroCode = new LinkedList<>();
        HashMap<String, String> nestedMacroNames = new HashMap<>();

        // Process macro code until end of macro declaration (endm)
        while (true) {
            // Get next line and its tokens
            currentLine = inputLines.pop();
            tokens = AssemblerUtils.decomposeInTokens(currentLine);

            // Macro declaration (nested macro)
            if (tokens.length > 1 && tokens[1].equalsIgnoreCase(MACRODEF)) {
                // Will only add to macro table 2 levels of nesting
                if (currentMacroNestLevel == 1) {
                    // Check for valid name before renaming
                    if (!MacroUtils.isValidMacroName(tokens[0])) {
                        Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "INVALID MACRO NAME: " + tokens[0]));
                        resetMacroProcessor();
                        throw new MacroProcessorError();
                    }

                    // Rename nested macro
                    String newNestedMacroName = MacroUtils.renameNestedMacro(outerMacroName, tokens[0]);
                    // Add nested macro declaration to macro code
                    currentLine = currentLine.replaceFirst(tokens[0], newNestedMacroName);

                    // Add to nested macro names, where OldName ->map-> NewName
                    nestedMacroNames.put(tokens[0], newNestedMacroName);
                    // Add to macro table with new name
                    macroTable.put(newNestedMacroName, new Macro(newNestedMacroName, false));

                    // Add its formal parameters to ignoreList
                    ArrayList<String> newFormalParameters = extractParametersFromMacro(tokens);
                    for (String fp : newFormalParameters) {
                        ignoreList.add(fp, currentMacroNestLevel);
                    }
                }

                // Add macro declaration line to outer macro code
                macroCode.add(currentLine);
                // Increase nest level
                currentMacroNestLevel += 1;
                continue;
            }
            // Check if it is a macro call (validate only to outer macro)
            else if (tokens.length > 1 && currentMacroNestLevel == 1 && tokens[0].equalsIgnoreCase(MACROCALL)) {
                // Get name of called macro
                String macroCalled = tokens[1];

                // Verify if it's a call for a nested macro
                if (nestedMacroNames.containsKey(macroCalled)) {
                    // Rename macro call
                    currentLine = currentLine.replaceFirst(macroCalled, nestedMacroNames.get(macroCalled));
                    macroCalled = nestedMacroNames.get(macroCalled);
                }

                // Check if called macro exists in macro table
                if (macroTable.containsKey(macroCalled)) {
                    macroCode.add(currentLine);
                    continue;
                }
                // Calling undeclared macro
                else {
                    Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "MACRO NOT FOUND: " + macroCalled));
                    resetMacroProcessor();
                    throw new UndeclaredMacro(macroCalled);
                }
            }

            // Check if it is end of macro declaration
            if (tokens[0].equalsIgnoreCase(MACROEND)) {
                ignoreList.removeAllKeysWithValue(currentMacroNestLevel);
                currentMacroNestLevel -= 1;

                if (currentMacroNestLevel == 0) {
                    break;
                } else {
                    macroCode.add(currentLine);
                }
            }
            // No nested macro declaration, call or end macro. Just regular code
            else {
                // Replace formal parameters with #i
                for (int i = 0; i < formalParameters.size(); i += 1) {
                    if (ignoreList.containsKey(formalParameters.get(i)))
                        continue;
                    currentLine = currentLine.replaceAll("\\b" + formalParameters.get(i) + "\\b", "#" + i);
                }
                macroCode.add(currentLine);
            }
        }

        // Create macro
        Macro newMacro = new Macro(outerMacroName, macroCode, formalParameters, true);
        macroTable.put(outerMacroName, newMacro);
    }

    /**
     * Parse macro call and expand it.
     *
     * @throws UndeclaredMacro
     */
    private void parseMacroCall() throws UndeclaredMacro {
        // Get all tokens in line
        String[] tokens = AssemblerUtils.decomposeInTokens(this.currentLine);

        // Get macro name
        String macroName = tokens[1];

        // Get real parameters
        ArrayList<String> realParameters = extractParametersFromMacro(tokens);

        // Process nested macros calls
        boolean isNestedMacro = false;
        if (macroName.contains(".")) {
            macroName = macroName.replace(".", "#");
            isNestedMacro = true;
        }

        // Check if macro exists
        if (macroTable.containsKey(macroName)) {
            var macroToExpand = macroTable.get(macroName);
            LinkedList<String> expandedMacro;

            try {
                // Expand macro with real parameters
                expandedMacro = macroToExpand.processRealParameters(realParameters);
            } catch (IllegalArgumentException e) {
                Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, e.getMessage() + " IN: \"" + currentLine + "\""));
                resetMacroProcessor();
                throw e;
            } catch (UndeclaredMacro e) {
                Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, e.getMessage()));
                resetMacroProcessor();
                throw e;
            }

            // Add expanded code in input lines to be processed when back to MODE 1
            while (!expandedMacro.isEmpty()) {
                inputLines.addFirst(expandedMacro.removeLast());
            }
        }
        // Macro does not exist
        else {
            String message;
            if (isNestedMacro)
                message = "NESTED MACRO WAS NOT CALLED: ";
            else
                message = "MACRO NOT FOUND: ";

            Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, message + macroName));
            throw new UndeclaredMacro(macroName);
        }
    }

    private ArrayList<String> extractParametersFromMacro(String[] tokens) {
        if (tokens.length > 2) {
            return new ArrayList<>(Arrays.stream(tokens, 2, tokens.length).toList());
        } else {
            return new ArrayList<>(0);
        }
    }

    /**
     * Reset macro processor to initial state.
     */
    private void resetMacroProcessor() {
        this.macroTable.clear();
        this.inputLines.clear();
        this.outputLines.clear();
        this.currentLine = "";
        this.lineCounter = 0;
        this.currentMacroNestLevel = 0;
    }
}