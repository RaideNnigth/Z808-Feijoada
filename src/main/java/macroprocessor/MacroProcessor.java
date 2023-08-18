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
    private LinkedList<String> lines;

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

        this.parseMacros();

        Logger.getInstance().reset();
        this.resetMacroProcessor();
    }

    /**
     * Parses macros from the input file, processes them, and writes the resulting lines to an intermediate file.
     * This method reads the input file line by line, checks for macros, and processes them accordingly.
     * If the input file is not found, an error log is added to the logger, and the method returns.
     * If there is an IO error while reading or writing the file, an error log is added, and the macro processor is reset.
     * After processing, the intermediate file with ".pre" extension is generated.
     *
     * @return The path to the generated intermediate file.
     * @throws RuntimeException if an unexpected exception occurs during processing.
     */
    public void parseMacros() {
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
            while (lineCounter < lines.size()) {
                currentLine = lines.get(lineCounter);
                // Check for macros
                parseLine(null);
                // Next Line
                lineCounter += 1;
            }
        } catch (IOException e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, String.format("IO Error: Error while reading %s (%s)", this.inputFile, e.getMessage())));
            return;
        } catch (InvalidMacroNameError e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, String.format("Invalid macro name: %s", e.getMessage())));
            return;
        } catch (Exception e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "Unknown error: Error while reading " + this.inputFile + "file: " + e.getMessage()));
            return;
        }

        try {
            this.writeOutputFile();
        } catch (IOException e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "Error while reading " + this.inputFile + "file: " + e.getMessage()));
            return;
        }

        Logger.getInstance().addLog(new Log(LogType.INFO, 0, "Macros finished to be replaced!"));
    }

    // Ai gustavo no cu nao - Ferrao
    // no cu sim! - Gustavo
    // Cala a boca e senta - Bessa
    // Semata - Nicolas
    // Get macro code

    /**
     * Parses a line looking for macros, processes their definitions, and updates the macro table.
     * If the line is a macro declaration, the method returns immediately.
     * If the macro name is invalid, an InvalidMacroNameError is thrown.
     *
     * @param parentMacro The parent macro, if this is a nested macro.
     * @throws InvalidMacroNameError if the macro name is invalid.
     */
    public void parseLine(Macro parentMacro) throws InvalidMacroNameError {
        // All tokens in line
        String[] tokens = AssemblerUtils.decomposeInTokens(this.currentLine);

        // is macro declaration?
        if (tokens[1].equalsIgnoreCase(MACRODEF))
            return;

        // Macro name is valid?
        if (!AssemblerUtils.isValidMacro(this.currentLine)) {
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
        this.macroTable.declareMacro(macroName, null, macroParams, null);
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
                try {
                    parseLine(currentMacro);
                } catch (StackOverflowError e) {
                    Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "Maximum nested macro level reached!"));
                }
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

    private void writeOutputFile() throws FileNotFoundException, IOException {
        // Writing intermediate file

        OutputStream outputStream = new FileOutputStream(this.outputFile);

        DataOutputStream dataOutStream = new DataOutputStream(outputStream)
        for (String line : this.lines)
            dataOutStream.writeBytes(line + "\n");
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