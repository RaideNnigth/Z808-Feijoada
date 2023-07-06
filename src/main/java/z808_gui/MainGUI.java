package z808_gui;

import z808_gui.utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;

public class MainGUI extends JFrame {
    // IMGs paths
    private final static String feijaozinhoIMGPath = "src/main/java/z808_gui/imgs/graoFeijao.png";
    private final static String poteFeijaoIMGPath = "src/main/java/z808_gui/imgs/poteFeijoada.png";
    private static String pathProgramaZ808 = "";

    // Create basic window
    public MainGUI() {
        setTitle("Z808 - Feijoada Edition");
        setIconImage((new ImageIcon(feijaozinhoIMGPath)).getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(new BorderLayout());
        setBackground(Color.white);

        // Appear in center
        setLocationRelativeTo(null);
    }

    // Menu bar
    private static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Itens da barra de menus
        JMenu arquivoMenu = new JMenu("Arquivo");
        JMenu executarMenu = new JMenu("Executar");
        JMenu sobreMenu = new JMenu("Sobre");

        // --------------------- Sub-itens menu Arquivo ---------------------
        JMenuItem abrirMenItem = new JMenuItem("Abrir");
        // Ação do menu Arquivo -> Abrir
        abrirMenItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                pathProgramaZ808 = selectedFile.getAbsolutePath();
                System.out.println("Selected file: " + pathProgramaZ808);
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
        JMenuItem ajudaMenItem = new JMenuItem("Ajuda");
        ajudaMenItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "Te vira.", "Ajuda", JOptionPane.INFORMATION_MESSAGE, null));

        sobreMenu.add(devsMenItem);
        sobreMenu.add(ajudaMenItem);

        // Adicionando itens da barra principal
        menuBar.add(arquivoMenu);
        menuBar.add(executarMenu);
        menuBar.add(sobreMenu);

        return menuBar;
    }

    public static void main(String[] args) {
        MainGUI z808UI = new MainGUI();

        z808UI.setJMenuBar(createMenuBar());

        // Painel superior com o título
        JPanel upperTitle = new JPanel();
        upperTitle.setPreferredSize(new Dimension(0, 100));
        upperTitle.setBackground(Color.lightGray);
        upperTitle.setLayout(new BoxLayout(upperTitle, BoxLayout.LINE_AXIS));
        upperTitle.add(Box.createRigidArea(new Dimension(10, 0)));

        // Criando o logo
        final int FEIJOADA_LOGO_SIZE = 75;
        Image feijoadaLogoIMG = UIUtils.resizeImage(new File(poteFeijaoIMGPath), FEIJOADA_LOGO_SIZE, FEIJOADA_LOGO_SIZE, Image.SCALE_SMOOTH);
        JLabel Z808_LOGO = new JLabel(new ImageIcon(feijoadaLogoIMG));
        Z808_LOGO.setIconTextGap(15);
        Z808_LOGO.setFont(new Font("Century Gothic", Font.PLAIN, 42));
        Z808_LOGO.setText("Z808 Feijoada EDITION");
        Z808_LOGO.setPreferredSize(new Dimension(FEIJOADA_LOGO_SIZE, FEIJOADA_LOGO_SIZE));

        // Adicionando o logo
        upperTitle.add(Z808_LOGO);

        // Painel inferior com o título
        JPanel lowerCommands = new JPanel();
        lowerCommands.setPreferredSize(new Dimension(0, 80));
        lowerCommands.setLayout(new BoxLayout(lowerCommands, BoxLayout.LINE_AXIS));
        lowerCommands.add(Box.createRigidArea(new Dimension(10, 0)));

        // Criando o botão
        final int PLAY_BUTTON_SIZE = 60;
        Image playButtonIMG = UIUtils.resizeImage(new File(feijaozinhoIMGPath), PLAY_BUTTON_SIZE, PLAY_BUTTON_SIZE, Image.SCALE_SMOOTH);
        JLabel playButton = new JLabel(new ImageIcon(playButtonIMG));
        playButton.setIconTextGap(15);
        playButton.setPreferredSize(new Dimension(FEIJOADA_LOGO_SIZE, FEIJOADA_LOGO_SIZE));

        // Botao play actions
        playButton.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Rectangle butBounds = playButton.getBounds();
                if (butBounds.contains(new Point(e.getX(), e.getY())))
                    playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                else
                    playButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        // Adicionando botao
        lowerCommands.add(playButton);

        // Adicionando o painel
        z808UI.add(upperTitle, BorderLayout.NORTH);
        z808UI.add(lowerCommands, BorderLayout.SOUTH);

        // Appear
        z808UI.setVisible(true);
    }
}
