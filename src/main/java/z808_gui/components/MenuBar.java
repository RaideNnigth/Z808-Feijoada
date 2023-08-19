package z808_gui.components;

import assembler.Assembler;
import virtual_machine.VirtualMachine;
import z808_gui.observerpattern.Listener;
import z808_gui.observerpattern.MessageType;
import z808_gui.observerpattern.ProgramPathEventManager;
import z808_gui.utils.ActionsListeners;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

import static z808_gui.utils.UIUtils.*;

public class MenuBar extends JMenuBar {
    public MenuBar(ActionsListeners al) {
        ProgramPathEventManager ppm = ProgramPathEventManager.getInstance();

        // Itens da barra de menus
        JMenu arquivoMenu = new JMenu("Arquivo");
        JMenu executarMenu = new JMenu("Executar");
        JMenu ajudaMenu = new JMenu("Ajuda");

        // Control key mask
        final var CTRL_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

        // --------------------- Sub-itens menu Arquivo ---------------------
        // Novo
        JMenuItem novoMenItem = new JMenuItem("Novo");
        novoMenItem.setAccelerator(KeyStroke.getKeyStroke('N', CTRL_MASK));

        novoMenItem.addActionListener(e -> {
            PROGRAM_PATH = "";
            CURRENT_DIRECTORY = "";
            AssemblyTextArea.getInstance().setText("");
            ppm.notifySubscribers(MessageType.PATH_NOT_SET);
        });

        // Abrir
        JMenuItem abrirMenItem = new JMenuItem("Abrir");
        abrirMenItem.setAccelerator(KeyStroke.getKeyStroke('O', CTRL_MASK));

        abrirMenItem.addActionListener(e -> {
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
                    PROGRAM_PATH = selectedFile.getAbsolutePath();
                    CURRENT_DIRECTORY = PROGRAM_PATH.substring(0, PROGRAM_PATH.length() - selectedFile.getName().length() - 1);
                    System.out.println("Selected file: " + PROGRAM_PATH);
                    System.out.println("Dir path to selected file: " + CURRENT_DIRECTORY);

                    // Carrega texto no textarea
                    try {
                        AssemblyTextArea.getInstance().setText(Files.readString(Paths.get(PROGRAM_PATH)));
                    } catch (IOException ex) {
                        System.out.println(ex);
                        System.exit(0);
                    }

                    // Notifica inscritos
                    ppm.notifySubscribers(MessageType.PATH_IS_SET);
                } else {
                    JOptionPane.showMessageDialog(null, "Você só pode abrir arquivos Assembly (.asm)!", "Erro", JOptionPane.ERROR_MESSAGE, null);
                }

            }
        });

        // Salvar
        JMenuItem salvarMenItem = new JMenuItem("Salvar");
        salvarMenItem.setAccelerator(KeyStroke.getKeyStroke('S', CTRL_MASK));

        salvarMenItem.addActionListener(al.getSaveAL());

        // Sair
        JMenuItem sairMenItem = new JMenuItem("Sair");
        sairMenItem.setAccelerator(KeyStroke.getKeyStroke('Q', CTRL_MASK));

        sairMenItem.addActionListener(e -> System.exit(0));

        // Adicionando itens em Arquivo
        arquivoMenu.add(novoMenItem);
        arquivoMenu.add(abrirMenItem);
        arquivoMenu.add(salvarMenItem);
        arquivoMenu.add(sairMenItem);

        // --------------------- Sub-itens menu Executar ---------------------
        JMenuItem preprocessMenItem = new JMenuItem("Processar macros");
        preprocessMenItem.setAccelerator(KeyStroke.getKeyStroke('R', CTRL_MASK));
        preprocessMenItem.addActionListener(al.getPreprocessarAL());

        JMenuItem montarMenItem = new JMenuItem("Montar código");
        montarMenItem.setAccelerator(KeyStroke.getKeyStroke('M', CTRL_MASK));
        montarMenItem.addActionListener(al.getMontarAL());

        JMenuItem executarTudoMenItem = new JMenuItem("Executar tudo");
        executarTudoMenItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        executarTudoMenItem.addActionListener(al.getRunAL());

        executarMenu.add(preprocessMenItem);
        executarMenu.add(montarMenItem);
        executarMenu.add(executarTudoMenItem);

        // --------------------- Sub-itens menu Sobre ---------------------
        JMenuItem devsMenItem = new JMenuItem("Desenvolvedores");
        JMenuItem sobreMenItem = new JMenuItem("Sobre");
        sobreMenItem.setMnemonic(KeyEvent.VK_F1);
        JMenuItem comoUsarMenItem = new JMenuItem("Como usar");
        comoUsarMenItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "Te vira.", "Como usar", JOptionPane.INFORMATION_MESSAGE, null));

        ajudaMenu.add(devsMenItem);
        ajudaMenu.add(sobreMenItem);
        ajudaMenu.add(new JSeparator());
        ajudaMenu.add(comoUsarMenItem);

        // Adicionando itens da barra principal
        this.add(arquivoMenu);
        this.add(executarMenu);
        this.add(ajudaMenu);
    }
}
