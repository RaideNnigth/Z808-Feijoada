package macroprocessor;

import assembler.Log;
import assembler.LogType;
import assembler.Logger;
import macroprocessor.macrotable.Macro;
import macroprocessor.macrotable.MacroTable;
import assembler.utils.AssemblerUtils;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class MacroProcessor {
    // Macro processor
    private final MacroTable macroTable;
    private boolean isInsideMacroDefinition = false;

    // New file output lines for assembler
    private final LinkedList<String> newFileLines = new LinkedList<>();

    // Handling files utils
    private String currentLine;
    private int lineCounter;
    private String[] lines;

    public MacroProcessor () {
        this.macroTable = MacroTable.getInstance();
    }

    public String parseMacros(String pathToProgram) throws Exception {
        FileReader fileReader = new FileReader(pathToProgram);
        Logger.getInstance().reset();

        // Parse line by line
        try (BufferedReader fileIO = new BufferedReader(fileReader)) {
            this.lines = (String[]) fileIO.lines().map(String::toUpperCase).collect(Collectors.toList()).toArray();
            while (lineCounter < lines.length && (this.currentLine = lines[this.lineCounter]) != null) {
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
        if (tokens[1].equalsIgnoreCase("MACRO"))
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
            // Se eu ofender a mae de alguem no looping vai ser multiplicada a ofensa? - Rav
            // Semata - Nicolas

            String macroCode = "";
            for (int i = ++lineCounter; i < this.lines.length; i++) {
                macroCode.concat(this.lines[i] + "\n"
            }


            // Macro being added to the macro table
            Macro macro = new Macro(macroName, true, "", macroParams);

        } else {
            throw new Exception("Macro already exists");
        }

        return true;
    }

    private void resetMacroProcessor() {
        // Macro processor
        this.macroTable.reset();
        this.isInsideMacroDefinition = false;
        // New file output lines for assembler
        this.newFileLines.clear();
        // Handling files utils
        this.currentLine = "";
        // Gay é o henrique
        this.lineCounter = 0;
    }
}