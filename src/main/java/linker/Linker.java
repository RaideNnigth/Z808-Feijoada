package linker;

import linker.tables.DefinitionsTable;
import linker.tables.GlobalSymbolTable;
import linker.tables.UsageTable;
import linker.tables.exceptions.AlreadyDeclaredPublicSymbolException;
import linker.tables.exceptions.NotDeclaredPublicSymbolException;
import linker.entities.Module;
import utils.BinaryUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class Linker {
    // Global symbol table
    private final GlobalSymbolTable globalSymbolTable;

    // Modules map: moduleName -> module
    private final HashMap<String, Module> modulesMap = new HashMap<>();
    private final LinkedList<String> modulesNamesOrder = new LinkedList<>();

    private int finalProgramSize = 0;
    private final int headerSizeBytes = 12;


    // Singleton pattern stuff
    private static Linker instance = null;
    private Linker() {
        globalSymbolTable = GlobalSymbolTable.getInstance();
    }
    public static Linker getInstance() {
        if (instance == null) {
            instance = new Linker();
        }
        return instance;
    }

    public void link(String finalProgramName) throws IOException, NotDeclaredPublicSymbolException, AlreadyDeclaredPublicSymbolException {
        // Thanks to Gustavo for clean code tips. This monstrosity was over 100 lines long.
        var modulesSet = modulesMap.values();

        // First, let's populate the global symbol table (1st pass)
        for (Module m : modulesSet) {
            var definitionsTable = m.definitionsTable;
            globalSymbolTable.addDefinitionTableToGlobalMap(definitionsTable);
        }

        // Now, let's read all modules code and store in its respective module
        readAllModulesCode(modulesSet);

        // Now we have all modules code in memory, let's link them,
        linkModulesCodes(modulesSet);

        // Now we have all modules linked, let's write the final program
        writeLinkedModulesToBinFile(finalProgramName);

        // Reset linker
        reset();
    }

    private void reset() {
        globalSymbolTable.reset();
        modulesMap.clear();
        modulesNamesOrder.clear();
        finalProgramSize = 0;
    }

    private void writeLinkedModulesToBinFile(String finalProgramName) throws IOException {
        // First, let's create the final program array
        byte[] finalProgramCode = new byte[finalProgramSize];

        // Header stuff
        final int csStart = headerSizeBytes;
        int csEnd = csStart;
        int dsStart = 0;
        int dsEnd = 0;
        final int stStart = 0;
        final int stEnd = -1;

        // Now, let's write the code segment from all modules in the final program
        for (String moduleName : modulesNamesOrder) {
            var module = modulesMap.get(moduleName);
            var moduleCode = module.getModuleCode();

            // Write module CODE SEGMENT in final program
            System.arraycopy(moduleCode, headerSizeBytes, finalProgramCode, csEnd, module.getCodeSegmentSize());

            // Update header stuff
            csEnd += module.getCodeSegmentSize();
        }
        // Adjust to csEnd point to the last byte of code segment
        csEnd -= 1;

        // Adjust to dsStart point to the first byte of data segment
        dsStart = csEnd + 1;
        dsEnd = dsStart;

        // Now, let's write the data segment from all modules in the final program
        for (String moduleName : modulesNamesOrder) {
            var module = modulesMap.get(moduleName);
            var moduleCode = module.getModuleCode();

            // Write module DATA SEGMENT in final program
            System.arraycopy(moduleCode, headerSizeBytes + module.getCodeSegmentSize(), finalProgramCode, dsEnd, module.getDataSegmentSize());

            // Update header stuff
            dsEnd += module.getDataSegmentSize();
        }
        // Adjust to dsEnd point to the last byte of data segment
        dsEnd -= 1;

        // Now, lets write the header data in the final program
        BinaryUtils.writeShortInByteArrayLE(finalProgramCode, 0, (short) csStart);
        BinaryUtils.writeShortInByteArrayLE(finalProgramCode, 2, (short) csEnd);
        BinaryUtils.writeShortInByteArrayLE(finalProgramCode, 4, (short) dsStart);
        BinaryUtils.writeShortInByteArrayLE(finalProgramCode, 6, (short) dsEnd);
        BinaryUtils.writeShortInByteArrayLE(finalProgramCode, 8, (short) stStart);
        BinaryUtils.writeShortInByteArrayLE(finalProgramCode, 10, (short) stEnd);

        // Now, let's write the final program to a file
        var fileOutputStream = new FileOutputStream(finalProgramName);
        fileOutputStream.write(finalProgramCode);
        fileOutputStream.close();
    }

    private void linkModulesCodes(Collection<Module> modulesSet) throws NotDeclaredPublicSymbolException {
        // We will start iterating over the usage table of each module
        for (Module m : modulesSet) {
            // Get usage table of this module
            var usageTable = m.usageTable;

            // Iterate over all symbols in this usage table
            for (String symbolName : usageTable.getSymbolNames()) {
                if (globalSymbolTable.containsSymbol(symbolName)) {
                    // Get all occurrences of this symbol in this module
                    var symbolOccurrences = usageTable.getSymbolOccurrences(symbolName);
                    // Get symbol value from global symbol table
                    var symbolValue = globalSymbolTable.getSymbol(symbolName).getValue();

                    // Calculate offset of data segment
                    int offset = 0;
                    for(var mod: modulesNamesOrder) {
                        if(modulesMap.get(mod) == m) {
                            break;
                        }
                        offset += modulesMap.get(mod).getDataSegmentSize();
                    }

                    for (var occurrence : symbolOccurrences) {
                        m.writeInModuleCode((short)((2 * occurrence.address) + headerSizeBytes), (short)(symbolValue + offset));
                    }
                }
                // Symbol is not declared in any module, throw exception
                else {
                    throw new NotDeclaredPublicSymbolException("THE SYMBOL: " + symbolName + " IS NOT DECLARED IN ANY MODULE");
                }
            }
        }
    }

    private void readAllModulesCode(Collection<Module> modulesSet) throws IOException {
        // Read all modules code
        try {
            int currentModuleSize;

            for (Module m : modulesSet) {
                var fileInputStream = new FileInputStream(m.binPath);

                currentModuleSize = (int) (new File(m.binPath)).length();
                finalProgramSize += currentModuleSize - headerSizeBytes;

                var moduleCode = new byte[currentModuleSize];
                fileInputStream.read(moduleCode);

                m.setModuleCode(moduleCode);

                fileInputStream.close();
            }

            // Add header just once
            finalProgramSize += headerSizeBytes;
        } catch (IOException e) {
            throw e;
        }
    }

    public void addBinPath(String moduleName, String binPath) {
        modulesMap.get(moduleName).binPath = binPath;
    }

    public void createModule(String moduleName) {
        modulesMap.put(moduleName, new Module());
        modulesNamesOrder.add(moduleName);
    }

    public DefinitionsTable getDefinitionsTable(String moduleName) {
        return modulesMap.get(moduleName).definitionsTable;
    }

    public UsageTable getUsageTable(String moduleName) {
        return modulesMap.get(moduleName).usageTable;
    }
}
