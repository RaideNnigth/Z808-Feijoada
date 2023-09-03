package z808_gui.components.buttons;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static z808_gui.utils.UIUtils.*;

public class OpenCloseLoggerButton extends JLabel {
    public OpenCloseLoggerButton() {
        super(CLEAR_LOGS_IMG);
        this.setToolTipText("Clear logs");
        this.setPreferredSize(new Dimension(CONTROLS_BUTTON_SIZE, CONTROLS_BUTTON_SIZE));
    }

    public void setActionListener(ActionListener al) {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseExited(e);

                if (isEnabled()) {
                    al.actionPerformed(null);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (getVisibleRect().contains(e.getPoint())) mouseEntered(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }
}
