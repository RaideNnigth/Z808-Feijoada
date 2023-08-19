package z808_gui.utils;

import assembler.Assembler;
import macroprocessor.MacroProcessor;
import virtual_machine.VirtualMachine;
import z808_gui.components.AssemblyTextArea;
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

                // Handle macros
                var macroProc = MacroProcessor.getInstance();
                macroProc.start(PROGRAM_PATH);
                String intermediateFile = macroProc.getOutputFile();

                // Then assemble
                Assembler assembler = Assembler.getInstance();
                try {
                    assembler.assembleFile(intermediateFile);
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
                String binPath = (PROGRAM_PATH.substring(0, PROGRAM_PATH.length() - 4) + ".bin");

                try {
                    // Load bin
                    vm.loadProgram(binPath);
                    vm.executeProgram();

                    var tabbedPane = Tabs.getInstance();
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, "O arquivo \"" + PROGRAM_PATH + "\" não existe!", "Erro", JOptionPane.ERROR_MESSAGE, null);
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
                            fw.write(AssemblyTextArea.getInstance().getText());
                        } catch (IOException ex) {
                            System.err.println(ex);
                            System.exit(0);
                        }
                    }
                }
                // Caso contrário, o arquivo existe e será atualizado com o código novo
                else {
                    try (FileWriter fw = new FileWriter(PROGRAM_PATH)) {
                        fw.write(AssemblyTextArea.getInstance().getText());
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



    public ActionListener getPreprocessarAL() {
        if (preprocessarAL == null) {
            preprocessarAL = e -> {
                getSaveAL().actionPerformed(e);

                // Preprocess file
                MacroProcessor macroProcessor = MacroProcessor.getInstance();
                try {
                    macroProcessor.start(PROGRAM_PATH);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "ERRO: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE, null);
                }
            };
        }

        return preprocessarAL;
    }
}