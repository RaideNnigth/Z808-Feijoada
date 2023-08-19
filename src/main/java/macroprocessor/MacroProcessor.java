package macroprocessor;

import logger.Log;
import logger.LogType;
import logger.Logger;
import macroprocessor.macrotable.Macro;
import macroprocessor.macrotable.MacroTable;
import assembler.utils.AssemblerUtils;

import java.io.*;

import java.util.Arrays;
import java.util.LinkedList;

//Singleton Class
public class MacroProcessor {
    private static MacroProcessor instance;

    // Macro processor
    private final MacroTable macroTable;


    // Handling files utils
    private String currentLine;
    private int lineCounter;
    private final LinkedList<String> lines = new LinkedList<>();

    private String inputFile;
    private String outputFile;

    // Macros defines
    private static final String MACRODEF = "MACRODEF";
    private static final String MACROEND = "ENDM";

    private MacroProcessor() {
        this.macroTable = MacroTable.getInstance();
    }

    public static MacroProcessor getInstance() {
        if (instance == null)
            instance = new MacroProcessor();
        return instance;
    }

    /**
     * Initializes the program with the provided path to the program file.
     * This method sets up the input and output file paths, resets the logger, and resets the macro processor.
     *
     * @param pathToProgram The path to the program file.
     */
    public void start(String pathToProgram) {
        // Handling files utils
        this.inputFile = pathToProgram;
        this.outputFile = String.valueOf(inputFile).replace(".asm", ".pre");

        // Parse whole file for macro declarations
        try {
            parseMacros();
        } catch (RuntimeException e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, 0, String.format("Error while parsing macros: %s", e.getMessage())));
            return;
        }

        // Log success of parse Macro
        Logger.getInstance().addLog(new Log(LogType.INFO, 0, "Macros finished to be parsed!"));

        try {
            replaceAllOcorrencesOfMacros();
        } catch (UndeclaredMacro e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, 0, String.format("Undeclared Macro on code: %s", e.getMessage())));
            return;
        } catch (InvalidMacroParameters e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, 0, String.format("Invalid Macro Parameters: %s", e.getMessage())));
            return;
        }

        // Log success of replace macros
        Logger.getInstance().addLog(new Log(LogType.INFO, 0, "Macros finished to be replaced!"));

        // Write output file
        try {
            writeOutputFile();
        } catch (IOException e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, 0, String.format("IO Error: %s", e.getMessage())));
            return;
        }

        // Log success
        Logger.getInstance().addLog(new Log(LogType.INFO, 0, "Macros finished to be replaced!"));

        // Reset logger
        Logger.getInstance().reset();
        this.resetMacroProcessor();
    }

    private void replaceAllOcorrencesOfMacros() throws UndeclaredMacro, InvalidMacroParameters {
        this.lineCounter = 0;
        for (String line : this.lines) {
            // Get all tokens in line
            String[] tokens = AssemblerUtils.decomposeInTokens(line);

            // Get macro name
            String macroName = tokens[0];

            String[] macroParams;
            if (tokens.length > 1) {
                // Get macro parameters
                macroParams = Arrays.copyOfRange(tokens, 1, tokens.length);
            } else {
                macroParams = new String[0];
            }

            // Check if it is a macro call (not a macro definition and that it is not just a label or instruction)
            // After that we check if the macro exists, in case is valid name, and it is not declared, we throw an exception
            if (AssemblerUtils.isValidMacro(macroName)) {
                if (!this.macroTable.macroExists(macroName)) {
                    throw new UndeclaredMacro(String.format("Macro undeclared: %s", macroName));
                } else {
                    // Replace occurrences with the macroCode defined for it
                    replaceOcorrenceOfMacro(this.lineCounter, this.macroTable.getMacro(macroName), macroParams);
                }
            }

            // Increment line counter
            this.lineCounter += 1;
        }
    }

    private void replaceOcorrenceOfMacro(int startLineIndex, Macro macro, String[] parameters) throws InvalidMacroParameters {

        if (macro.getParameters().length != parameters.length) {
            throw new InvalidMacroParameters(String.format("Invalid number of parameters for macro %s", macro.getIdentification()));
        }

        // get rest of code after macro call
        LinkedList<String> restOfCode = (LinkedList<String>) this.lines.subList(startLineIndex + 1, lines.size() - 1);

        // get macro code
        String macroCode = macro.getMacroCode();

        // replace parameters
        for (int i = 0; i < parameters.length; i++) {
            macroCode = macroCode.replace(macro.getParameters()[i], parameters[i]);
        }

        // split macro code into lines
        LinkedList<String> macroCodeLinkedList = new LinkedList<>(Arrays.asList(macroCode.split("\n")));

        // add macro code to lines
        this.lines.addAll(macroCodeLinkedList);
        // add rest of code to lines
        this.lines.addAll(restOfCode);
    }


    /**
     * Parses macros from the input file, processes them, and writes the resulting lines to an intermediate file.
     * This method reads the input file line by line, checks for macros, and processes them accordingly.
     * If the input file is not found, an error log is added to the logger, and the method returns.
     * If there is an IO error while reading or writing the file, an error log is added, and the macro processor is reset.
     * After processing, the intermediate file with ".pre" extension is generated.
     *
     * @throws RuntimeException if an unexpected exception occurs during processing.
     */
    public void parseMacros() throws RuntimeException {
        // Start fileReader
        FileReader fileReader;
        try {
            fileReader = new FileReader(this.inputFile);
        } catch (FileNotFoundException e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, this.lineCounter, String.format("IO Error: couldn't find file %s", this.inputFile)));
            return;
        }

        // Parse line by line
        try (BufferedReader fileIO = new BufferedReader(fileReader)) {
            // Read file to lines class properties
            readInputFile(fileIO);
            while (this.lineCounter < this.lines.size()) {
                this.currentLine = this.lines.get(this.lineCounter);
                // Check for macros
                parseLine(null);
                // Next Line
                this.lineCounter += 1;
            }
        } catch (IOException e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, this.lineCounter, String.format("IO Error: Error while reading %s (%s)", this.inputFile, e.getMessage())));
            throw new RuntimeException(e);
        } catch (InvalidMacroNameError e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, this.lineCounter, String.format("Invalid macro name: %s", e.getMessage())));
            throw new RuntimeException(e);
        } catch (StackOverflowError e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, this.lineCounter, "Maximum nested macro level reached!"));
            throw new RuntimeException(e);
        } catch (Exception e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, this.lineCounter, "Unknown error: Error while reading " + this.inputFile + "file: " + e.getMessage()));
            throw new RuntimeException(e);
        }

        try {
            this.writeOutputFile();
        } catch (IOException e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, this.lineCounter, "Error while reading " + this.inputFile + "file: " + e.getMessage()));
        }
    }

    // Ai gustavo no cu nao - Ferrao
    // no cu sim! - Gustavo
    // Cala a boca e senta - Bessa
    // Semata - Nicolas
    // Get macro code

    /**
     * Parses a line looking for macros, processes their definitions, and updates the macro table.
     * If the line is not a macro declaration, the method returns immediately.
     * If the macro name is invalid, an InvalidMacroNameError is thrown.
     *
     * @param parentMacro The parent macro, if this is a nested macro.
     * @throws InvalidMacroNameError if the macro name is invalid.
     */
    private void parseLine(Macro parentMacro) throws InvalidMacroNameError, StackOverflowError {
        // All tokens in line
        String[] tokens = AssemblerUtils.decomposeInTokens(this.currentLine);

        // is macro declaration?
        if (!tokens[1].equalsIgnoreCase(MACRODEF))
            return;

        // Macro name is valid?
        if (!AssemblerUtils.isValidMacro(tokens[0])) {
            throw new InvalidMacroNameError(String.format("Invalid name: %s", this.currentLine.split(" ")[0]));
        }

        // Macro name
        String macroName = tokens[0];

        // Macro parameters
        String[] macroParams;
        if (tokens.length > 2) {
            macroParams = Arrays.copyOfRange(tokens, 2, tokens.length);
        } else {
            macroParams = new String[0];
        }

        // Create Macro instance
        this.macroTable.declareMacro(macroName, macroParams);
        Macro currentMacro = this.macroTable.getMacro(macroName);

        // Macro code
        StringBuilder macroCode = new StringBuilder();

        // Start of macro definition
        int startMacroDef = lineCounter;

        //Skip macro definition line
        lineCounter += 1;

        //Check if we are in the end of a macro
        while (!this.lines.get(lineCounter).equals(MACROEND)) {
            // Get current line being processed
            this.currentLine = this.lines.get(lineCounter);

            // Check if it's a nested macro definition
            if (this.currentLine.equalsIgnoreCase(MACRODEF)) {
                parseLine(currentMacro);
            }
            macroCode.append(currentLine).append("\n");
            lineCounter += 1;
        }
        // End of macro definition
        int endMacroDef = lineCounter;

        // Insert new code on macro
        currentMacro.setMacroCode(macroCode.toString());
        currentMacro.setParentMacro(parentMacro);

        // Remove macro definition from lines
        removeLine(startMacroDef, endMacroDef);
    }

    /**
     * Reads and processes the lines from the input file.
     * This method reads each line from the input file, removes comments, trims whitespace,
     * and appends non-empty lines to the lines list.
     *
     * @param fileIO The BufferedReader for reading the input file.
     */
    private void readInputFile(BufferedReader fileIO) {
        // For each line in user file
        for (String line : fileIO.lines().toList()) {
            // Remove comments, picking up just first part of the file
            line = line.split(";")[0].trim();
            // Line empty
            if (!line.isEmpty()) {
                // Append line to lines list
                appendLine(line.toUpperCase());
            }
        }
    }

    /**
     * Writes the processed lines to the output intermediate file.
     * This method creates an output stream to the intermediate file and writes each processed line to it.
     * It formats each line with a newline character.
     *
     * @throws IOException if an IO error occurs during the writing process.
     */
    private void writeOutputFile() throws IOException {
        // Writing intermediate file
        OutputStream outputStream = new FileOutputStream(this.outputFile);
        DataOutputStream dataOutStream = new DataOutputStream(outputStream);
        for (String line : this.lines)
            dataOutStream.writeBytes(String.format("%s%n", line));
    }

    private void appendLine(String line) {
        // Append line
        this.lines.add(line);
    }

    private void removeLine(int startLineIndex, int endLineIndex) {
        // Remove all lines in the interval
        this.lines.subList(startLineIndex, endLineIndex).clear();
    }

    private void resetMacroProcessor() {
        // Macro processor
        this.macroTable.reset();
        // Handling files utils
        this.currentLine = "";
        this.lines.clear();
        // Gay é o henrique
        this.lineCounter = 0;
    }
}