package z808_gui.components;

import javax.swing.*;
import java.io.File;

import static z808_gui.utils.UIUtils.*;

public class Menu extends JMenuBar {
    public Menu() {
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
            }
        });

        JMenuItem sairMenItem = new JMenuItem("Sair");
        sairMenItem.addActionListener(e -> System.exit(0));

        arquivoMenu.add(abrirMenItem);
        arquivoMenu.add(sairMenItem);

        // --------------------- Sub-itens menu Executar ---------------------
        JMenuItem executarTudoMenItem = new JMenuItem("Executar tudo");
        JMenuItem executarInstMenItem = new JMenuItem("Executar instrução");

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
}
