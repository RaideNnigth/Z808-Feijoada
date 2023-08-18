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

public class MacroProcessor {
    // Macro processor
    private final MacroTable macroTable;
    private boolean isInsideMacroDefinition = false;
    private int macroAlignmentLevel = 0;

    // New file output lines for assembler
    private final LinkedList<String> newFileLines = new LinkedList<>();

    // Handling files utils
    private String currentLine;
    private int lineCounter;
    private LinkedList<String> lines;

    // Macros defines
    private static final String MACRODEF = "MACRODEF";
    private static final String MACROEND = "ENDM";

    public MacroProcessor() {
        this.macroTable = MacroTable.getInstance();
    }

    /*
    public String parseMacros(String pathToProgram) throws Exception {
        FileReader fileReader = new FileReader(pathToProgram);
        Logger.getInstance().reset();

        // Parse line by line
        try (BufferedReader fileIO = new BufferedReader(fileReader)) {
            for (String s : fileIO.lines().toList()) {
                if (!s.isEmpty()) {
                    if (s.trim().charAt(0) != ';') {
                        this.lines.add(s);
                    }
                }
            }

            while (lineCounter < lines.length && (this.currentLine = lines.get(this.lineCounter)) != null) {
                // Check for macros
                parseLine();
                // Next Line
                lineCounter += 1;
            }
        } catch (IOException e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "Error while reading " + pathToProgram + "file: " + e.getMessage()));
            Logger.getInstance().printLogs();
            resetMacroProcessor();
        }

        // Removes ".asm" extension and append ".pre"
        pathToProgram = pathToProgram.replace(".asm", ".pre");

        // Writing intermediate file
        OutputStream outputStream = new FileOutputStream(pathToProgram);
        try (DataOutputStream dataOutStream = new DataOutputStream(outputStream)) {
            for (String line : newFileLines)
                dataOutStream.writeBytes(line + "\n");
        } catch (IOException e) {
            Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "Error while writing " + pathToProgram + "file: " + e.getMessage()));
            Logger.getInstance().printLogs();
            resetMacroProcessor();
        }

        // Log success message
        Logger.getInstance().addLog(new Log(LogType.INFO, 0, "Macros finished to be replaced!"));
        Logger.getInstance().printLogs();

        return pathToProgram;
    }

    public boolean parseLine() throws Exception {
        // All tokens in line
        String[] tokens = AssemblerUtils.decomposeInTokens(this.currentLine);

        // is macro declaration?
        if (tokens[1].equalsIgnoreCase(MACRODEF))
            return false;

        // Macro name is valid?
        if (!AssemblerUtils.isValidMacro(this.currentLine)) {
            throw new Exception("Invalid macro name");
            return false;
        }

        // Macro name
        String macroName = tokens[0];

        if (isInsideMacroDefinition) {

        }

        // If macro does not exist
        if (!macroTable.macroExists(macroName)) {
            // Macro parameters
            String[] macroParams;
            if (tokens.length > 2) {
                macroParams = Arrays.copyOfRange(tokens, 2, tokens.length);
            } else {
                macroParams = new String[0];
            }

            // Ai gustavo no cu nao - Ferrao
            // no cu sim! - Gustavo
            // Cala a boca e senta - Bessa
            // Semata - Nicolas
            // Get macro code

            int startMacroDef = lineCounter;
            StringBuilder macroCode = new StringBuilder();
            while (!lines[lineCounter].equals(MACROEND)) {
                this.currentLine = lines[lineCounter];

                // Check if it's a nested macro definition
                if (this.currentLine.equalsIgnoreCase(MACRODEF)) {
                    this.macroAlignmentLevel += 1;
                    try {
                        parseLine();
                    } catch (StackOverflowError e) {
                        Logger.getInstance().addLog(new Log(LogType.ERROR, lineCounter, "Maximum nested macro level reached!"));
                    }
                }
                macroCode.append(lines[lineCounter]).append("\n");
                lineCounter += 1;
            }
            int endMacroDef = lineCounter;
            

            // Macro being added to the macro table
            Macro macro = new Macro(macroName, macroCode.toString(), macroParams, );

        } else {
            throw new Exception("Macro already exists");
        }

        return true;
    }*/

    private void resetMacroProcessor() {
        // Macro processor
        this.macroTable.reset();
        this.isInsideMacroDefinition = false;
        // New file output lines for assembler
        this.newFileLines.clear();
        // Handling files utils
        this.currentLine = "";
        this.lines.clear();
        // Gay é o henrique
        this.lineCounter = 0;
    }
}