package z808_gui.components;

import assembler.Assembler;
import z808_gui.observerpattern.Listener;
import z808_gui.observerpattern.MessageType;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import static z808_gui.utils.UIUtils.*;

public class MenuBar extends JMenuBar {
    private static LinkedList<Listener> subscribers = new LinkedList<>();

    public MenuBar() {
        // Itens da barra de menus
        JMenu arquivoMenu = new JMenu("Arquivo");
        JMenu executarMenu = new JMenu("Executar");
        JMenu ajudaMenu = new JMenu("Ajuda");

        // --------------------- Sub-itens menu Arquivo ---------------------
        JMenuItem abrirMenItem = new JMenuItem("Abrir");
        // Ação do menu Arquivo -> Abrir
        abrirMenItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                PROGRAM_PATH = selectedFile.getAbsolutePath();
                System.out.println("Selected file: " + PROGRAM_PATH);
                notifySubscribers(MessageType.PATH_IS_SET);
            }
        });

        JMenuItem sairMenItem = new JMenuItem("Sair");
        sairMenItem.addActionListener(e -> System.exit(0));

        arquivoMenu.add(abrirMenItem);
        arquivoMenu.add(sairMenItem);

        // --------------------- Sub-itens menu Executar ---------------------
        JMenuItem montarMenItem = new JMenuItem("Montar código");
        JMenuItem executarTudoMenItem = new JMenuItem("Executar tudo");
        JMenuItem executarInstMenItem = new JMenuItem("Executar instrução");

        // montar action
        montarMenItem.addActionListener(e -> {
            boolean execute = false;

            // Se PROGRAM_PATH está vazia, o programa não foi salvo
            if (PROGRAM_PATH.isEmpty()) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    PROGRAM_PATH = fileChooser.getSelectedFile().getAbsolutePath() + ".asm";
                    try (FileWriter fw = new FileWriter(PROGRAM_PATH)) {
                        fw.write(AssemblyTextArea.getInstance().getText());
                    } catch (IOException ex) {
                        System.err.println(ex);
                    }

                    execute = true;
                }
            } else {
                try (FileWriter fw = new FileWriter(PROGRAM_PATH)) {
                    fw.write(AssemblyTextArea.getInstance().getText());
                } catch (IOException ex) {
                    System.err.println(ex);
                }

                execute = true;
            }

            if (execute) {
                Assembler assembler = Assembler.getInstance();
                try {
                    assembler.assembleFile(PROGRAM_PATH);
                } catch (FileNotFoundException ex) {
                    System.err.println(ex.toString());
                }
            }
        });

        executarMenu.add(montarMenItem);
        executarMenu.add(executarTudoMenItem);
        executarMenu.add(executarInstMenItem);

        // --------------------- Sub-itens menu Sobre ---------------------
        JMenuItem devsMenItem = new JMenuItem("Desenvolvedores");
        JMenuItem sobreMenItem = new JMenuItem("Sobre");
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

    public static void subscribe(Listener l) {
        subscribers.add(l);
    }

    private static void notifySubscribers(MessageType t) {
        for (Listener l : subscribers) {
            l.update(t);
        }
    }


}
