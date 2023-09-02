package z808_gui.utils;

import assembler.Assembler;
import logger.Log;
import logger.LogType;
import logger.Logger;
import macroprocessor.MacroProcessor;
import virtual_machine.VirtualMachine;
import z808_gui.components.AssemblyTextPane;
import z808_gui.components.LogTextArea;
import z808_gui.components.Tabs;
import z808_gui.observerpattern.MessageType;
import z808_gui.observerpattern.ProgramPathEventManager;
import static z808_gui.utils.UIUtils.PROGRAM_PATH;
import static z808_gui.utils.UIUtils.CURRENT_DIRECTORY;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ActionsListeners {
    private static ActionListener saveAL = null;
    private static ActionListener preprocessarAL = null;

    private static ActionListener montarAL = null;
    private static ActionListener runAL = null;

    private static ActionsListeners instance = null;
    private VirtualMachine vm;

    private ActionsListeners(VirtualMachine vm) {
        this.vm = vm;
    }

    private static void processDirectory(File directory) throws Exception {
        for (final File fileEntry : directory.listFiles()) {
            if (fileEntry.isDirectory()) {
                processDirectory(fileEntry);
            } else {
                if (fileEntry.getName().endsWith(".asm")) {
                    String processedCodePath = MacroProcessor.getInstance().parseMacros(fileEntry.getAbsolutePath());
                    Assembler.getInstance().assembleFile(processedCodePath);
                }
            }
        }
    }

    public static ActionsListeners getInstance(VirtualMachine vm) {
        if (instance == null) {
            instance = new ActionsListeners(vm);
        }
        return instance;
    }

    public ActionListener getMontarAL() {
        if (montarAL == null) {
            montarAL = e -> {
                // Save file
                getSaveAL().actionPerformed(e);

                // Reset logs
                Logger.getInstance().reset();

                // Now, process macros, assemble and link
                try {
                    processDirectory(new File(CURRENT_DIRECTORY));
                    // CALL LINKER <- TODO
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "ERRO: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE, null);
                }
            };
        }

        return montarAL;
    }

    public ActionListener getRunAL() {
        if (runAL == null) {
            runAL = e -> {
                // Save file
                getSaveAL().actionPerformed(e);
                // Assemble file
                getMontarAL().actionPerformed(e);

                // .bin file path to load
                String binPath = PROGRAM_PATH.replace(".asm", ".bin");

                try {
                    // Load bin
                    vm.loadProgram(binPath);
                    vm.executeProgram();

                    // Add execution success log
                    Logger.getInstance().addLog(new Log(LogType.INFO, 0, "Programa executado com sucesso!"));

                    var tabbedPane = Tabs.getInstance();
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, "FALHA DE MONTAGEM. VERIFIQUE LOGS.", "Erro", JOptionPane.ERROR_MESSAGE, null);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.ERROR_MESSAGE, null);
                    ex.printStackTrace();
                }
            };
        }

        return runAL;
    }

    public ActionListener getSaveAL() {
        if (saveAL == null) {
            saveAL = e -> {
                // Se PROGRAM_PATH está vazia, o programa não foi salvo
                if (PROGRAM_PATH.isEmpty()) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

                    int result = fileChooser.showSaveDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        var selectedFile = fileChooser.getSelectedFile();

                        // Coloca extensão se não tiver
                        PROGRAM_PATH = selectedFile.getAbsolutePath();
                        CURRENT_DIRECTORY = PROGRAM_PATH.substring(0, PROGRAM_PATH.length() - selectedFile.getName().length() - 1);

                        if (!PROGRAM_PATH.endsWith(".asm")) {
                            PROGRAM_PATH = fileChooser.getSelectedFile().getAbsolutePath() + ".asm";
                        }

                        try (FileWriter fw = new FileWriter(PROGRAM_PATH)) {
                            fw.write(AssemblyTextPane.getInstance().getText());
                        } catch (IOException ex) {
                            System.err.println(ex);
                            System.exit(0);
                        }
                    }
                }
                // Caso contrário, o arquivo existe e será atualizado com o código novo
                else {
                    try (FileWriter fw = new FileWriter(PROGRAM_PATH)) {
                        fw.write(AssemblyTextPane.getInstance().getText());
                    } catch (IOException ex) {
                        System.err.println(ex);
                        System.exit(0);
                    }
                }

                ProgramPathEventManager.getInstance().notifySubscribers(MessageType.PATH_IS_SET);
            };
        }

        return saveAL;
    }

}