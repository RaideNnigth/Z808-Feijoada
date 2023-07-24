package z808_gui;

import virtual_machine.VirtualMachine;
import z808_gui.components.LowerCommandsPanel;
import z808_gui.components.MenuBar;

import javax.swing.*;
import java.awt.*;

import z808_gui.components.*;

import static z808_gui.utils.UIUtils.*;

public class MainWindow extends JFrame {
    private VirtualMachine vm;

    public MainWindow(VirtualMachine virtualMachine) {
        this.vm = virtualMachine;

        setTitle("Z808 - Feijoada Edition");
        setIconImage((new ImageIcon(PLAY_DEFAULT_IMG_PATH)).getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.white);

        // Creates menu
        JMenuBar menuBar = new MenuBar();
        setJMenuBar(menuBar);

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
        AssemblyTextArea assemblyArea = new AssemblyTextArea();

        // ------------------------------ Red Panel (registradores) ------------------------------
        RegistersPanel rightRegistersPanel = new RegistersPanel();
        vm.subscribe(rightRegistersPanel);
        vm.notifySubscribers(); // Display default values

        // ------------------------------ Criando Abas ------------------------------
        Tabs tabs = new Tabs();
        tabs.addTab("Programa", null, assemblyArea, "Escreva seu programa em FeijoadaZ808 Assembly");
        tabs.addTab("Memória", null, new JLabel("Aba de memória..."), "Memória do programa montado");

        // Its size will be at least 2/3 of the start dimension
        tabs.setPreferredSize(new Dimension((int) ((startDimension.getWidth() / 3) * 2), Short.MAX_VALUE));

        // ------------------------------ Populando região central ------------------------------
        centralPanelLayout.setHorizontalGroup(
                centralPanelLayout.createSequentialGroup()
                        .addComponent(tabs, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                        .addComponent(verticalSep, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(rightRegistersPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        centralPanelLayout.setVerticalGroup(
                centralPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(tabs)
                        .addComponent(verticalSep)
                        .addComponent(rightRegistersPanel)
        );

        // Adicionando os painéis
        this.add(upperTitle, BorderLayout.NORTH);
        this.add(lowerCommands, BorderLayout.SOUTH);
        this.add(centralPanel, BorderLayout.CENTER);

        // Packing UI
        this.pack();
        this.setSize(this.getWidth(), (int) startDimension.getHeight());

        // Center window
        this.setLocationRelativeTo(null);

        // Appear
        this.setVisible(true);
    }
}
