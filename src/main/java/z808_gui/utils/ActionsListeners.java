package z808_gui.utils;

import assembler.Assembler;
import logger.Log;
import logger.LogType;
import logger.Logger;
import macroprocessor.MacroProcessor;
import virtual_machine.VirtualMachine;
import z808_gui.MainWindow;
import z808_gui.components.panels.AssemblyTextPane;
import z808_gui.components.panels.LogTextArea;
import z808_gui.components.Tabs;
import z808_gui.observerpattern.MessageType;
import z808_gui.observerpattern.ProgramPathEventManager;
import static z808_gui.utils.UIUtils.PROGRAM_PATH;
import static z808_gui.utils.UIUtils.CURRENT_DIRECTORY;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ActionsListeners {
    private ActionListener openAL;
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

        this.setOpenAL();
        this.setSaveAL();
        this.setMontalAL();
        this.setRunAL();
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

    private void setOpenAL() {
        this.openAL = e -> {
            AssemblyTextPane assemblyEditor = new AssemblyTextPane();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

            // Filtro de itens
            var assemblyFilter = new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    } else {
                        return f.getName().toLowerCase().endsWith(".asm");
                    }
                }

                @Override
                public String getDescription() {
                    return "Assembly source file (*.asm)";
                }
            };

            fileChooser.addChoosableFileFilter(assemblyFilter);
            fileChooser.setFileFilter(assemblyFilter);

            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                // Validando arquivo uwu
                if (selectedFile.getName().endsWith(".asm")) {
                    // Seta PROGRAM_PATH com o endereço do arquivo selecionado
                    assemblyEditor.setFilepath(selectedFile.getAbsolutePath());
                    //System.out.println("Selected file: " + PROGRAM_PATH);
                    //System.out.println("Dir path to selected file: " + CURRENT_DIRECTORY);

                    // Carrega texto no textarea
                    try {
                        assemblyEditor.setText(Files.readString(Paths.get(assemblyEditor.getFilepath())));
                    } catch (IOException ex) {
                        System.out.println(ex);
                        System.exit(0);
                    }

                    // Notifica inscritos
                    ProgramPathEventManager.getInstance().notifySubscribers(MessageType.PATH_IS_SET);
                } else {
                    JOptionPane.showMessageDialog(null, "Você só pode abrir arquivos Assembly (.asm)!", "Erro", JOptionPane.ERROR_MESSAGE, null);
                }

            }

            String[] filepathTokens = assemblyEditor.getFilepath().split("/");
            this.tabs.add(filepathTokens[filepathTokens.length-1], assemblyEditor);
        };
    }

    private void setMontalAL() {
        this.montarAL = e -> {
            AssemblyTextPane assemblyEditor = (AssemblyTextPane) this.tabs.getSelectedComponent();

            if (!assemblyEditor.getFilepath().isEmpty()) {
                // Save file
                this.getSaveAL().actionPerformed(e);

                // Reset logs
                Logger.getInstance().reset();

                // Now, process macros, assemble and link
                try {
                    //processDirectory(new File(CURRENT_DIRECTORY));

                    File file = new File(assemblyEditor.getFilepath());
                    String processedCodePath = MacroProcessor.getInstance().parseMacros(file.getAbsolutePath());
                    Assembler.getInstance().assembleFile(processedCodePath);

                    // CALL LINKER <- TODO
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "ERRO: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE, null);
                }
            }
        };
    }

    private void setRunAL() {
        this.runAL = e -> {
            AssemblyTextPane assemblyEditor = (AssemblyTextPane) this.tabs.getSelectedComponent();

            // Save file
            this.getSaveAL().actionPerformed(e);
            // Assemble file
            this.getMontarAL().actionPerformed(e);

            // .bin file path to load
            String binPath = assemblyEditor.getFilepath().replace(".asm", ".bin");

            try {
                // Load bin
                vm.loadProgram(binPath);
                vm.executeProgram();

                // Add execution success log
                Logger.getInstance().addLog(new Log(LogType.INFO, 0, "Programa executado com sucesso!"));

                this.tabs.setSelectedIndex(this.tabs.getTabCount() - 1);
            } catch (IOException ioException) {
                Logger.getInstance().error(0, ioException.getMessage());
                JOptionPane.showMessageDialog(null, "FALHA DE MONTAGEM. VERIFIQUE LOGS.", "Erro", JOptionPane.ERROR_MESSAGE, null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.ERROR_MESSAGE, null);
                ex.printStackTrace();
            }
        };
    }

    public void setSaveAL() {
        this.saveAL = e -> {
            AssemblyTextPane assemblyEditor = (AssemblyTextPane) this.tabs.getSelectedComponent();

            // Se PROGRAM_PATH está vazia, o programa não foi salvo
            if (assemblyEditor.getFilepath().isEmpty()) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    var selectedFile = fileChooser.getSelectedFile();

                    // Coloca extensão se não tiver

                    //CURRENT_DIRECTORY = PROGRAM_PATH.substring(0, PROGRAM_PATH.length() - selectedFile.getName().length() - 1);

                    if (assemblyEditor.getFilepath().endsWith(".asm")) {
                        assemblyEditor.setFilepath(selectedFile.getAbsolutePath());
                    } else {
                        assemblyEditor.setFilepath(fileChooser.getSelectedFile().getAbsolutePath() + ".asm");
                    }

                    try (FileWriter fw = new FileWriter(assemblyEditor.getFilepath())) {
                        fw.write(assemblyEditor.getText());
                    } catch (IOException ex) {
                        System.err.println(ex);
                        System.exit(0);
                    }
                }
            }
            // Caso contrário, o arquivo existe e será atualizado com o código novo
            else {
                try (FileWriter fw = new FileWriter(assemblyEditor.getFilepath())) {
                    fw.write(assemblyEditor.getText());
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

    public ActionListener getOpenAL() { return openAL; }

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