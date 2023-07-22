package z808_gui;

import virtual_machine.VirtualMachine;
import z808_gui.components.LowerCommandsPanel;
import z808_gui.components.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.LinkedList;

import z808_gui.components.*;
import z808_gui.observerpattern.Listener;
import z808_gui.observerpattern.Observer;

import static z808_gui.utils.UIUtils.*;

public class MainWindow extends JFrame implements Observer {
    private VirtualMachine vm;
    private JPanel upperPanel;
    private JPanel lowerPanel;
    private JLabel playButton;
    private JLabel stepButton;

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
        lowerPanel = new LowerCommandsPanel();

        JMenuBar menuBar = new MenuBar();

        this.setJMenuBar(menuBar);

        // Painel superior com o título
        UpperTitlePanel upperTitle = new UpperTitlePanel();

        // Painel inferior com os botões
        LowerCommandsPanel lowerCommands = new LowerCommandsPanel();

        PlayButton playButton = new PlayButton(PLAY_DEFAULT_IMG, vm);
        StepButton stepButton = new StepButton(STEP_DEFAULT_IMG, vm);

        // Populando lowerCommands
        lowerCommands.add(Box.createRigidArea(H_SPACER));
        lowerCommands.add(playButton);
        lowerCommands.add(Box.createRigidArea(H_SPACER));
        lowerCommands.add(stepButton);

        // ------------------------------ Criando painel central ------------------------------
        JPanel centralPanel = new JPanel();
        GroupLayout centralPanelLayout = new GroupLayout(centralPanel);
        centralPanel.setLayout(centralPanelLayout);
        centralPanel.setBackground(Color.white);

        centralPanelLayout.setAutoCreateGaps(true);
        centralPanelLayout.setAutoCreateContainerGaps(true);

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
        centralPanelLayout.setHorizontalGroup(
                centralPanelLayout.createSequentialGroup()
                        .addComponent(tabs, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                        .addComponent(verticalSep, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(leftRegistersPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        centralPanelLayout.setVerticalGroup(
                centralPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(tabs)
                        .addComponent(verticalSep)
                        .addComponent(leftRegistersPanel)
        );

        // Adicionando os painéis
        this.add(upperTitle, BorderLayout.NORTH);
        this.add(lowerCommands, BorderLayout.SOUTH);
        this.add(centralPanel, BorderLayout.CENTER);

        // Appear
        this.setVisible(true);
    }

    /*
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

     */
}
