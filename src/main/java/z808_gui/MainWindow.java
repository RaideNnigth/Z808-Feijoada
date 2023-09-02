package z808_gui;

import virtual_machine.VirtualMachine;
import z808_gui.components.panels.CentralPanel;
import z808_gui.components.panels.LowerCommandsPanel;
import z808_gui.components.MenuBar;

import javax.swing.*;
import java.awt.*;

import z808_gui.components.panels.UpperTitlePanel;
import z808_gui.observerpattern.ProgramPathListener;
import z808_gui.observerpattern.MessageType;
import z808_gui.observerpattern.ProgramPathEventManager;
import z808_gui.utils.ActionsListeners;
import z808_gui.utils.UIUtils;

import static z808_gui.utils.UIUtils.*;

public class MainWindow extends JFrame implements ProgramPathListener {
    private final VirtualMachine vm;
    private static final String TITLE = "Z808 - Feijoada Edition";

    JMenuBar menuBar;

    // Paineis
    UpperTitlePanel upperTitlePanel;
    CentralPanel centralPanel;
    LowerCommandsPanel lowerCommandsPanel;

    ActionsListeners actionsListeners;


    public MainWindow(VirtualMachine virtualMachine) {
        this.vm = virtualMachine;

        this.setTitle(TITLE);
        this.setIconImage((new ImageIcon(PLAY_DEFAULT_IMG_PATH)).getImage());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);

        ProgramPathEventManager ppm = ProgramPathEventManager.getInstance();
        ppm.subscribe(this);

        // Creates menu

        // Painel superior com o título
        this.upperTitlePanel = new UpperTitlePanel();

        // Painel inferior com os botões
        this.lowerCommandsPanel = new LowerCommandsPanel(vm);

        // ------------------------------ Criando painel central ------------------------------
        this.centralPanel = new CentralPanel(vm);

        // Adicionando os painéis
        this.add(this.upperTitlePanel, BorderLayout.NORTH);
        this.add(this.lowerCommandsPanel, BorderLayout.SOUTH);
        this.add(this.centralPanel, BorderLayout.CENTER);

        this.actionsListeners = new ActionsListeners(vm, this.centralPanel.getTabs(), this.centralPanel.getTabs().getAssemblyArea(), this.centralPanel.getTabs().getLogArea());
        this.createMenuBar();
        this.lowerCommandsPanel.getAssembleButton().setActionListener(this.actionsListeners.getMontarAL());
        this.lowerCommandsPanel.getPlayButton().setActionListener(this.actionsListeners.getRunAL());
        this.lowerCommandsPanel.getClearLogsButton().setActionListener(this.actionsListeners.getClearLogTextAL());

        // Packing UI
        this.pack();
        this.setSize(this.getWidth(), (int) startDimension.getHeight());

        // Center window
        this.setLocationRelativeTo(null);

        // Appear
        this.setVisible(true);
    }

    private void createMenuBar() {
        this.menuBar = new MenuBar(this.actionsListeners, this.centralPanel.getTabs().getAssemblyArea());
        this.setJMenuBar(this.menuBar);
    }

    @Override
    public void update(MessageType type) {
        switch (type) {
            case PATH_IS_SET -> {
                setTitle(TITLE + " - " + UIUtils.getFileNameNoExtension(PROGRAM_PATH));
            }
            case PATH_NOT_SET -> {
                setTitle(TITLE);
            }
        }
    }
}
