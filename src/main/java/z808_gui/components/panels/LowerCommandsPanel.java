package z808_gui.components.panels;

import virtual_machine.VirtualMachine;
import z808_gui.components.buttons.AssembleButton;
import z808_gui.components.buttons.ClearLogsButton;
import z808_gui.components.buttons.PlayButton;

import javax.swing.*;
import java.awt.*;

import static z808_gui.utils.UIUtils.*;

public class LowerCommandsPanel extends JPanel {
    PlayButton playButton;
    AssembleButton assembleButton;
    ClearLogsButton clearLogsButton;

    public LowerCommandsPanel(VirtualMachine vm) {
        this.playButton = new PlayButton(PLAY_DEFAULT_IMG, vm);
        this.assembleButton = new AssembleButton(ASSEM_DEFAULT_IMG, vm);
        this.clearLogsButton = new ClearLogsButton();

        this.add(Box.createRigidArea(H_SPACER));
        this.add(this.assembleButton);
        this.add(Box.createRigidArea(H_SPACER));
        this.add(this.playButton);
        this.add(Box.createRigidArea(H_SPACER));
        this.add(this.clearLogsButton);

        this.setPreferredSize(new Dimension(0, 80));
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
    }

    public PlayButton getPlayButton() {
        return playButton;
    }

    public AssembleButton getAssembleButton() {
        return assembleButton;
    }

    public ClearLogsButton getClearLogsButton() {
        return clearLogsButton;
    }
}
