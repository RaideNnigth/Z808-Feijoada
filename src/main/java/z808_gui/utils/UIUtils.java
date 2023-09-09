package z808_gui.utils;

import assembler.Assembler;
import linker.Linker;
import logger.Log;
import logger.LogType;
import logger.Logger;
import macroprocessor.MacroProcessor;
import virtual_machine.VirtualMachine;
import virtual_machine.registers.Registers;
import z808_gui.components.DependenciesWindow;
import z808_gui.components.panels.AssemblyTextPane;
import z808_gui.components.panels.CentralPanel;
import z808_gui.components.panels.LoggerPanel;
import z808_gui.observerpattern.MessageType;
import z808_gui.observerpattern.ProgramPathEventManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class UIUtils {
    public static final Dimension startDimension = new Dimension(1200, 800);
    // IMGs paths
    public static final String PLAY_DEFAULT_IMG_PATH = "src/main/java/z808_gui/imgs/graoFeijao.png";
    public static final String PLAY_ACTIVE_IMG_PATH = "src/main/java/z808_gui/imgs/graoFeijaoActive.png";
    public static final String Z808_FEIJOADA_LOGO_IMG_PATH = "src/main/java/z808_gui/imgs/Z808FEIJOADALOGO.png";
    public static final String ASSEM_DEFAULT_IMG_PATH = "src/main/java/z808_gui/imgs/assemFeijao.png";
    public static final String ASSEM_ACTIVE_IMG_PATH = "src/main/java/z808_gui/imgs/assemFeijaoActive.png";
    public static final String CLEAR_LOGS_IMG_PATH = "src/main/java/z808_gui/imgs/clearLogsImg.png";

    // Play and Step icon
    public static final int CONTROLS_BUTTON_SIZE = 45;
    public static final ImageIcon PLAY_DEFAULT_IMG = new ImageIcon(UIUtils.resizeImage(PLAY_DEFAULT_IMG_PATH, CONTROLS_BUTTON_SIZE, CONTROLS_BUTTON_SIZE, Image.SCALE_SMOOTH));
    public static final ImageIcon PLAY_HOVER_IMG = new ImageIcon(UIUtils.resizeImage(PLAY_ACTIVE_IMG_PATH, CONTROLS_BUTTON_SIZE, CONTROLS_BUTTON_SIZE, Image.SCALE_SMOOTH));
    public static final ImageIcon ASSEM_DEFAULT_IMG = new ImageIcon(UIUtils.resizeImage(ASSEM_DEFAULT_IMG_PATH, CONTROLS_BUTTON_SIZE, CONTROLS_BUTTON_SIZE, Image.SCALE_SMOOTH));
    public static final ImageIcon ASSEM_ACTIVE_IMG = new ImageIcon(UIUtils.resizeImage(ASSEM_ACTIVE_IMG_PATH, CONTROLS_BUTTON_SIZE, CONTROLS_BUTTON_SIZE, Image.SCALE_SMOOTH));
    public static final ImageIcon CLEAR_LOGS_IMG = new ImageIcon(UIUtils.resizeImage(CLEAR_LOGS_IMG_PATH, CONTROLS_BUTTON_SIZE, CONTROLS_BUTTON_SIZE, Image.SCALE_SMOOTH));

    // Logo Z808
    public static final int FEIJOADA_LOGO_SIZE_X = 455;
    public static final int FEIJOADA_LOGO_SIZE_Y = 75;
    public static final Color BROWN_COLOR = new Color(0x5a473d);

    // Registers labels and stuff
    public static final HashMap<Registers, JLabel> registersJLabelsMap = new HashMap<>();
    //public static final RegFlags flagRegister = VirtualMachine.getFlagsRegister();

    // Spacers
    public static final Dimension H_SPACER = new Dimension(10, 0);
    public static final Dimension V_SPACER = new Dimension(0, 10);
    public static final JSeparator VERTICAL_SEPARATOR = new JSeparator(SwingConstants.VERTICAL);


    public static Image resizeImage(String imgPath, int width, int height, int algorithm) {
        File img = new File(imgPath);
        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(img);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return bufferedImage.getScaledInstance(width, height, algorithm);
    }

    public static boolean isPathValid(String programPath) {
        File programBin = new File(programPath);

        if (programPath.isEmpty()) {
            return false;
        }

        return true;
    }

    public static String getFileNameNoExtension(String path) {
        int ind = path.lastIndexOf(File.separatorChar);
        String fileName = path.substring(ind + 1);

        return fileName.split("\\.")[0];
    }

    public static void newFile(JTabbedPane tabs) {
        AssemblyTextPane assemblyEditor = new AssemblyTextPane();
        tabs.add("*new file", assemblyEditor);
        tabs.setSelectedIndex(tabs.getTabCount() - 1);
    }

    public static void openFile(JTabbedPane tabs) {
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

                // Carrega texto no textarea
                try {
                    assemblyEditor.setText(Files.readString(Paths.get(assemblyEditor.getFilepath())));
                } catch (IOException ex) {
                    Logger.getInstance().error(ex.getMessage());
                }

                // Notifica inscritos
                ProgramPathEventManager.getInstance().notifySubscribers(MessageType.PATH_IS_SET);
            } else {
                JOptionPane.showMessageDialog(null, "Você só pode abrir arquivos Assembly (.asm)!", "Erro", JOptionPane.ERROR_MESSAGE, null);
            }

            String[] filepathTokens = assemblyEditor.getFilepath().split("/");
            tabs.add(filepathTokens[filepathTokens.length - 1], assemblyEditor);
            tabs.setSelectedIndex(tabs.getTabCount() - 1);
        }
    }

    public static void assembleFile(JTabbedPane tabs) {
        AssemblyTextPane assemblyEditor = (AssemblyTextPane) tabs.getSelectedComponent();

        if (!assemblyEditor.getFilepath().isEmpty()) {
            // Save file
            UIUtils.saveFile(tabs);

            // Now, process macros, assemble and link
            try {
                /*for (String dpPath : assemblyEditor.getDependeciesPath()) {
                    File file = new File(dpPath);
                    String processedCodePath = MacroProcessor.getInstance().parseMacros(file.getAbsolutePath());
                    Assembler.getInstance().assembleFile(processedCodePath);
                }*/

                String output_filepath = assemblyEditor.getFilepath().replace(".asm", ".bin");

                File file = new File(assemblyEditor.getFilepath());

                String processedCodePath = MacroProcessor.getInstance().parseMacros(file.getAbsolutePath());
                Assembler.getInstance().assembleFile(processedCodePath);

                // CALL LINKER <- TODO
                //Linker.getInstance().link(output_filepath);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "ERRO: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE, null);
            }
        }
    }

    public static void runFile(JTabbedPane tabs, VirtualMachine vm) {
        AssemblyTextPane assemblyEditor = (AssemblyTextPane) tabs.getSelectedComponent();

        // Save file
        UIUtils.saveFile(tabs);
        // Assemble file
        UIUtils.assembleFile(tabs);

        // .bin file path to load
        String binPath = assemblyEditor.getFilepath().replace(".asm", ".bin");

        try {
            // Load bin
            vm.loadProgram(binPath);
            vm.executeProgram();

            // Add execution success log
            Logger.getInstance().addLog(new Log(LogType.INFO, 0, "Programa executado com sucesso!"));
        } catch (IOException ioException) {
            Logger.getInstance().error(0, ioException.getMessage());
            JOptionPane.showMessageDialog(null, "FALHA DE MONTAGEM. VERIFIQUE LOGS.", "Erro", JOptionPane.ERROR_MESSAGE, null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.ERROR_MESSAGE, null);
            ex.printStackTrace();
        }
    }

    public static void saveFile(JTabbedPane tabs) {
        AssemblyTextPane assemblyEditor = (AssemblyTextPane) tabs.getSelectedComponent();

        // Se PROGRAM_PATH está vazia, o programa não foi salvo
        if (assemblyEditor.getFilepath().isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                if (selectedFile.getAbsolutePath().endsWith(".asm")) {
                    assemblyEditor.setFilepath(selectedFile.getAbsolutePath());
                } else {
                    assemblyEditor.setFilepath(fileChooser.getSelectedFile().getAbsolutePath() + ".asm");
                }

                try (FileWriter fw = new FileWriter(assemblyEditor.getFilepath())) {
                    fw.write(assemblyEditor.getText());
                } catch (IOException ex) {
                    Logger.getInstance().error(String.format("Couldn't write to asm file: %s", ex.getMessage()));
                }

                String[] filepathTokens = assemblyEditor.getFilepath().split("/");
                tabs.add(filepathTokens[filepathTokens.length-1], assemblyEditor);

                ProgramPathEventManager.getInstance().notifySubscribers(MessageType.PATH_IS_SET);
            }
        }
        // Caso contrário, o arquivo existe e será atualizado com o código novo
        else {
            try (FileWriter fw = new FileWriter(assemblyEditor.getFilepath())) {
                fw.write(assemblyEditor.getText());
            } catch (IOException ex) {
                Logger.getInstance().error(String.format("Couldn't write to asm file: %s", ex.getMessage()));
            }

            ProgramPathEventManager.getInstance().notifySubscribers(MessageType.PATH_IS_SET);
        }
    }

    public static void closeFile(JTabbedPane tabs) {
        int assemblyEditorIndex = tabs.getSelectedIndex();

        saveFile(tabs);

        tabs.removeTabAt(assemblyEditorIndex);
    }

    public static void exportMemoryData(JTabbedPane tabs, VirtualMachine vm) {
        AssemblyTextPane assemblyEditor = (AssemblyTextPane) tabs.getSelectedComponent();
        vm.exportMemoryData(assemblyEditor.getFilepath());
    }

    public static void openCloseLogger(CentralPanel centralPanel) {
        if (centralPanel.getDividerLocation() >= centralPanel.getMaximumDividerLocation()) {
            centralPanel.setDividerLocation(centralPanel.getLastDividerLocation());
        } else {
            centralPanel.setDividerLocation(1.0);
        }
    }

    public static void clearLoggerText(LoggerPanel loggerPanel) {
        loggerPanel.clearText();
    }

    public static void setDependecies(JTabbedPane tabs) {
        AssemblyTextPane assemblyEditor = (AssemblyTextPane) tabs.getSelectedComponent();

        DependenciesWindow dpWin = new DependenciesWindow(assemblyEditor.getDependeciesPath());
    }
}
