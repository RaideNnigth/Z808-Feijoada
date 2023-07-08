package z808_gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static z808_gui.utils.UIUtils.CONTROLS_BUTTON_SIZE;

public class StepButton extends JLabel {
    public StepButton() {
        //JLabel this = new JLabel(STEP_DEFAULT_IMG);
        this.setPreferredSize(new Dimension(CONTROLS_BUTTON_SIZE, CONTROLS_BUTTON_SIZE));
    }
}
