package z808_gui;

import virtual_machine.VirtualMachine;
import z808_gui.components.panels.*;
import z808_gui.components.MenuBar;

import javax.swing.*;
import java.awt.*;

import z808_gui.observerpattern.ProgramPathListener;
import z808_gui.observerpattern.MessageType;
import z808_gui.observerpattern.ProgramPathEventManager;
import z808_gui.utils.SyntaxHighlightingProfile;
import z808_gui.utils.UIUtils;

import static z808_gui.utils.UIUtils.*;

public class MainWindow extends JFrame implements ProgramPathListener {
    private final VirtualMachine vm;
    private static final String TITLE = "Z808 - Feijoada Edition";

    private SyntaxHighlightingProfile defaultProfile;

    JMenuBar menuBar;

    // Paineis
    UpperTitlePanel upperTitlePanel;
    CentralPanel centralPanel;
    SideBar sideBar;

    RegistersPanel registersPanel;


    public MainWindow(VirtualMachine virtualMachine) {
        this.vm = virtualMachine;

        this.defaultProfile = new SyntaxHighlightingProfile();

        this.setTitle(TITLE);
        this.setIconImage((new ImageIcon(PLAY_DEFAULT_IMG_PATH)).getImage());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);

        ProgramPathEventManager ppm = ProgramPathEventManager.getInstance();
        ppm.subscribe(this);

        this.centralPanel = new CentralPanel();

        this.menuBar = new MenuBar(this.centralPanel, this.vm);
        this.setJMenuBar(this.menuBar);

        // Painel superior com o título
        this.upperTitlePanel = new UpperTitlePanel();

        this.sideBar = new SideBar(this.centralPanel, this.vm);

        this.registersPanel = new RegistersPanel();
        vm.subscribe(this.registersPanel);
        vm.notifySubscribers();

        // Adicionando os painéis
        this.add(this.upperTitlePanel, BorderLayout.NORTH);
        this.add(this.sideBar, BorderLayout.WEST);
        this.add(this.registersPanel, BorderLayout.EAST);
        this.add(this.centralPanel, BorderLayout.CENTER);

        UIUtils.newFile(this.centralPanel.getTabs());

        // Packing UI
        this.pack();
        this.setSize(this.getWidth(), (int) startDimension.getHeight());

        // Center window
        this.setLocationRelativeTo(null);

        // Appear
        this.setVisible(true);
    }

    @Override
    public void update(MessageType type) {
        switch (type) {
            case PATH_IS_SET -> {
                //setTitle(TITLE + " - " + UIUtils.getFileNameNoExtension(((AssemblyTextPane) this.centralPanel.getTabs().getSelectedComponent()).getFilepath()));
            }
            case PATH_NOT_SET -> {
                setTitle(TITLE);
            }
        }
    }
}
