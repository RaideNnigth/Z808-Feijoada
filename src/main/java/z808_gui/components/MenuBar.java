package z808_gui.components;

import assembler.Assembler;
import z808_gui.observerpattern.Listener;
import z808_gui.observerpattern.MessageType;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        // Novo
        JMenuItem novoMenItem = new JMenuItem("Novo");

        novoMenItem.addActionListener(e -> {
            PROGRAM_PATH = "";
            AssemblyTextArea.getInstance().setText("");
            notifySubscribers(MessageType.PATH_NOT_SET);
        });

        // Abrir
        JMenuItem abrirMenItem = new JMenuItem("Abrir");

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
                    System.out.println("Selected file: " + PROGRAM_PATH);

                    // Carrega texto no textarea
                    try {
                        AssemblyTextArea.getInstance().setText(Files.readString(Paths.get(PROGRAM_PATH)));
                    } catch (IOException ex) {
                        System.out.println(ex);
                        System.exit(0);
                    }

                    // Notifica inscritos
                    notifySubscribers(MessageType.PATH_IS_SET);
                } else {
                    JOptionPane.showMessageDialog(null, "Você só pode abrir arquivos Assembly (.asm)!", "Erro", JOptionPane.ERROR_MESSAGE, null);
                }

            }
        });

        // Salvar
        JMenuItem salvarMenItem = new JMenuItem("Salvar");

        var saveAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Se PROGRAM_PATH está vazia, o programa não foi salvo
                if (PROGRAM_PATH.isEmpty()) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

                    int result = fileChooser.showSaveDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        // Coloca extensão se não tiver
                        PROGRAM_PATH = fileChooser.getSelectedFile().getAbsolutePath();
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

                notifySubscribers(MessageType.PATH_IS_SET);
            }
        };
        salvarMenItem.addActionListener(saveAction);

        // Sair
        JMenuItem sairMenItem = new JMenuItem("Sair");

        sairMenItem.addActionListener(e -> System.exit(0));

        // Adicionando itens em Arquivo
        arquivoMenu.add(novoMenItem);
        arquivoMenu.add(abrirMenItem);
        arquivoMenu.add(salvarMenItem);
        arquivoMenu.add(sairMenItem);

        // --------------------- Sub-itens menu Executar ---------------------
        JMenuItem montarMenItem = new JMenuItem("Montar código");
        JMenuItem executarTudoMenItem = new JMenuItem("Executar tudo");
        JMenuItem executarInstMenItem = new JMenuItem("Executar instrução");

        // montar action
        montarMenItem.addActionListener(e -> {
            // Salva arquivo .asm
            saveAction.actionPerformed(e);

            Assembler assembler = Assembler.getInstance();

            try {
                assembler.assembleFile(PROGRAM_PATH);
            } catch (FileNotFoundException ex) {
                System.err.println(ex.toString());
            } catch (Exception ex) {
                System.err.println(ex.toString());
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
