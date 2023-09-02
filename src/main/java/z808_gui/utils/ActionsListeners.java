package z808_gui.utils;

import assembler.Assembler;
import logger.Log;
import logger.LogType;
import logger.Logger;
import macroprocessor.MacroProcessor;
import virtual_machine.VirtualMachine;
import z808_gui.components.panels.AssemblyTextPane;
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
    private ActionListener saveAL;
    private ActionListener montarAL;
    private ActionListener runAL;
    private ActionListener clearLogTextAL;

    private VirtualMachine vm;

    private Tabs tabs;
    private AssemblyTextPane assemblyTextPane;
    private LogTextArea logTextArea;


    public ActionsListeners(VirtualMachine vm, Tabs tabs, AssemblyTextPane assemblyTextPane, LogTextArea logTextArea) {
        this.vm = vm;

        this.tabs = tabs;
        this.assemblyTextPane = assemblyTextPane;
        this.logTextArea = logTextArea;

        this.initSaveAL();
        this.initMontalAL();
        this.initRunAL();
        this.initClearLogTextAL();
    }

    private void processDirectory(File directory) throws Exception {
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

    private void initMontalAL() {
        this.montarAL = e -> {
            // Save file
            this.getSaveAL().actionPerformed(e);

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

    private void initRunAL() {
        this.runAL = e -> {
            // Save file
            this.getSaveAL().actionPerformed(e);
            // Assemble file
            this.getMontarAL().actionPerformed(e);

            // .bin file path to load
            String binPath = PROGRAM_PATH.replace(".asm", ".bin");

            try {
                // Load bin
                vm.loadProgram(binPath);
                vm.executeProgram();

                // Add execution success log
                Logger.getInstance().addLog(new Log(LogType.INFO, 0, "Programa executado com sucesso!"));

                this.tabs.setSelectedIndex(this.tabs.getTabCount() - 1);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "FALHA DE MONTAGEM. VERIFIQUE LOGS.", "Erro", JOptionPane.ERROR_MESSAGE, null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.ERROR_MESSAGE, null);
                ex.printStackTrace();
            }
        };
    }

    public void initSaveAL() {
        this.saveAL = e -> {
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
                        fw.write(this.assemblyTextPane.getText());
                    } catch (IOException ex) {
                        System.err.println(ex);
                        System.exit(0);
                    }
                }
            }
            // Caso contrário, o arquivo existe e será atualizado com o código novo
            else {
                try (FileWriter fw = new FileWriter(PROGRAM_PATH)) {
                    fw.write(this.assemblyTextPane.getText());
                } catch (IOException ex) {
                    System.err.println(ex);
                    System.exit(0);
                }
            }

            ProgramPathEventManager.getInstance().notifySubscribers(MessageType.PATH_IS_SET);
        };
    }

    private void initClearLogTextAL() {
        this.clearLogTextAL = e -> {
            this.logTextArea.clearText();
        };
    }

    public ActionListener getSaveAL() {
        return saveAL;
    }

    public ActionListener getMontarAL() {
        return montarAL;
    }

    public ActionListener getRunAL() {
        return runAL;
    }

    public ActionListener getClearLogTextAL() {
        return clearLogTextAL;
    }
}