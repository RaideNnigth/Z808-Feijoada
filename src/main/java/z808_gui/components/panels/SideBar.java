package z808_gui.components.panels;

import virtual_machine.VirtualMachine;
import z808_gui.components.buttons.AssembleButton;
import z808_gui.components.buttons.OpenCloseLoggerButton;
import z808_gui.components.buttons.PlayButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static z808_gui.utils.UIUtils.*;

public class SideBar extends JPanel {
    PlayButton playButton;
    AssembleButton assembleButton;
    OpenCloseLoggerButton openCloseLoggerButton;

    public SideBar(VirtualMachine vm) {
        this.playButton = new PlayButton(PLAY_DEFAULT_IMG, vm);
        this.assembleButton = new AssembleButton(ASSEM_DEFAULT_IMG, vm);
        this.openCloseLoggerButton = new OpenCloseLoggerButton();

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

    public PlayButton getPlayButton() {
        return playButton;
    }

    public AssembleButton getAssembleButton() {
        return assembleButton;
    }

    public OpenCloseLoggerButton getClearLogsButton() {
        return openCloseLoggerButton;
    }
}
