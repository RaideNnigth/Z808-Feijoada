package z808_gui;

import virtual_machine.VirtualMachine;
import virtual_machine.registers.RegWork;
import virtual_machine.registers.Registers;
import z808_gui.components.LowerCommands;
import z808_gui.components.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;

import z808_gui.components.*;
import static z808_gui.utils.UIUtils.*;

public class MainWindow extends JFrame implements Observer {
    private VirtualMachine vm;
    private JPanel upperPanel;
    private JPanel lowerPanel;
    private JPanel centralPanel;
    private JPanel registersPanel;
    private JTabbedPane tabs;
    private JTextArea assemblyTextEditor;
    private JLabel playButton;
    private JLabel stepButton;
    private final JSeparator verticalSep = new JSeparator(SwingConstants.VERTICAL);

    public MainWindow(VirtualMachine vm) {
        this.vm = vm;

        setTitle("Z808 - Feijoada Edition");
        setIconImage((new ImageIcon(PLAY_DEFAULT_IMG_PATH)).getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(startDimension);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        // Appear in center
        setLocationRelativeTo(null);

        upperPanel = new UpperTitlePanel();
        centralPanel = new CentralPanel();
        lowerPanel = new LowerCommands();

        JMenuBar menuBar = new Menu();

        this.setJMenuBar(menuBar);

        // Spacers
        final Dimension H_SPACER = new Dimension(10, 0);

        // Painel superior com o título
        UpperTitlePanel upperTitle = new UpperTitlePanel();

        // Painel inferior com os botões
        LowerCommands lowerCommands = new LowerCommands();

        PlayButton playButton = new PlayButton();
        StepButton stepButton = new StepButton();

        // Populando lowerCommands
        lowerCommands.add(Box.createRigidArea(H_SPACER));
        lowerCommands.add(playButton);
        lowerCommands.add(Box.createRigidArea(H_SPACER));
        lowerCommands.add(stepButton);

        // ------------------------------ Criando painel central ------------------------------


        // ------------------------------ Criando a area de texto para o Assembly ------------------------------
        JTextArea assemblyTextEditor = new JTextArea();
        assemblyTextEditor.setFont(new Font("Consolas", Font.PLAIN, 22));
        JScrollPane assemblyArea = new JScrollPane(assemblyTextEditor); // Permite ter scroll no TextArea

        // ------------------------------ Red Panel (registradores) ------------------------------
        RegistersPanel leftRegistersPanel = new RegistersPanel();

        GroupLayout leftPanelLayout = new GroupLayout(leftRegistersPanel);
        leftPanelLayout.setAutoCreateGaps(true);

        leftRegistersPanel.setLayout(leftPanelLayout);

        leftRegistersPanel.setPreferredSize(new Dimension((int) startDimension.getWidth() / 3, Short.MAX_VALUE));
        leftRegistersPanel.setBackground(Color.white);

        // Inicializando labels
        Font fonteLabels = new Font("Arial", Font.PLAIN, 18);

        for (int i = 0; i < noWorkRegs; ++i) {
            var newLabel = new JLabel();
            newLabel.setFont(fonteLabels);
            workRegistersJLabels.add(newLabel);
        }
        flagRegisterJLabel.setFont(fonteLabels);

        for (int i = 0; i < noWorkRegs; ++i) {
            workRegistersJLabels.get(i).setText(workRegistersID.get(i));
        }
        flagRegisterJLabel.setText(flagRegisterID);

        // Populando com os labels de registradores
        var hGroup = leftPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        var vGroup = leftPanelLayout.createSequentialGroup();

        for (JLabel wReg : workRegistersJLabels) {
            hGroup.addComponent(wReg);
            vGroup.addComponent(wReg);
        }
        hGroup.addComponent(flagRegisterJLabel);
        vGroup.addComponent(flagRegisterJLabel);

        leftPanelLayout.setHorizontalGroup(hGroup);
        leftPanelLayout.setVerticalGroup(vGroup);

        // ------------------------------ Criando Abas ------------------------------
        Tabs tabs = new Tabs();
        tabs.addTab("Programa", null, assemblyArea, "Escreva seu programa em FeijoadaZ808 Assembly");
        tabs.addTab("Memória", null, new JLabel("Aba de memória..."), "Memória do programa montado");

        // ------------------------------ Populando região central ------------------------------
        centralPanelLayout.setHorizontalGroup(centralPanelLayout.createSequentialGroup().addComponent(tabs, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE).addComponent(separador, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(leftRegistersPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));

        centralPanelLayout.setVerticalGroup(centralPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(tabs).addComponent(separador).addComponent(leftRegistersPanel));

        // Adicionando os painéis
        this.add(upperTitle, BorderLayout.NORTH);
        this.add(lowerCommands, BorderLayout.SOUTH);
        this.add(centralPannel, BorderLayout.CENTER);

        // Appear
        this.setVisible(true);
    }

    private void setAdapters() {
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseExited(e);

                if (PROGRAM_PATH.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Abra um arquivo primeiro!", "Erro", JOptionPane.ERROR_MESSAGE, null);
                    return;
                }

                try {
                    vm.loadProgram(PROGRAM_PATH);
                    vm.executeProgram();
                    updateWorkRegsLabels();
                }
                catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, "O arquivo \"" + PROGRAM_PATH + "\" não existe!", "Erro", JOptionPane.ERROR_MESSAGE, null);
                }
                finally {
                    PROGRAM_PATH = "";
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (playButton.getVisibleRect().contains(e.getPoint()))
                    mouseEntered(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                playButton.setIcon(PLAY_HOVER_IMG);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                playButton.setIcon(PLAY_DEFAULT_IMG);
            }
        });
    }

    private void setStepButtonAdapters() {
        stepButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseExited(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (stepButton.getVisibleRect().contains(e.getPoint())) mouseEntered(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
//                System.out.println(e.getX() + " " + e.getX());
//                System.out.println(this.getVisibleRect());
                stepButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                stepButton.setIcon(STEP_ACTIVE_IMG);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                stepButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                stepButton.setIcon(STEP_DEFAULT_IMG);
            }
        });
    }

    // Update registers labels
    private void updateWorkRegsLabels() {
        Registers[] regsKeys = Registers.values();
        HashMap<Registers, RegWork> regMap = getWorkRegisters();

        // Update work registers
        for (int i = 0; i < noWorkRegs; i++) {
            workRegistersJLabels.get(i).setText(workRegistersID.get(i) + regMap.get(regsKeys[i]).getValue());
        }
        // Upadte flags register
        // to do ...
    }
}
