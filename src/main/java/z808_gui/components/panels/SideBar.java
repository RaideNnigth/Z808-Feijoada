package z808_gui.components.panels;

import virtual_machine.VirtualMachine;
import z808_gui.components.buttons.AssembleButton;
import z808_gui.components.buttons.OpenCloseLoggerButton;
import z808_gui.components.buttons.PlayButton;
import z808_gui.utils.UIUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static z808_gui.utils.UIUtils.*;

public class SideBar extends JPanel {
    PlayButton playButton;
    AssembleButton assembleButton;
    OpenCloseLoggerButton openCloseLoggerButton;

    CentralPanel centralPanel;
    VirtualMachine vm;

    public SideBar(CentralPanel centralPanel, VirtualMachine vm) {
        this.centralPanel = centralPanel;
        this.vm = vm;

        this.playButton = new PlayButton();
        this.playButton.setActionListener(e -> UIUtils.runFile(this.centralPanel.getTabs(), this.vm));
        this.assembleButton = new AssembleButton();
        this.assembleButton.setActionListener(e -> UIUtils.assembleFile(this.centralPanel.getTabs()));
        this.openCloseLoggerButton = new OpenCloseLoggerButton();
        this.openCloseLoggerButton.setActionListener(e -> UIUtils.openCloseLogger(this.centralPanel));

        this.setBorder(new EmptyBorder(0, 10, 0, 10));

        this.add(Box.createRigidArea(V_SPACER));
        this.add(this.assembleButton);
        this.add(Box.createRigidArea(V_SPACER));
        this.add(this.playButton);
        this.add(Box.createRigidArea(V_SPACER));
        this.add(this.openCloseLoggerButton);

        this.setPreferredSize(new Dimension(this.assembleButton.getPreferredSize().width + 20, 80));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setVisible(true);
    }
}
