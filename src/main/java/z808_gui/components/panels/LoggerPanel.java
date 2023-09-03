package z808_gui.components.panels;

import logger.Logger;
import utils.Observer;

import javax.swing.*;
import java.awt.*;

import static z808_gui.utils.UIUtils.startDimension;

public class LoggerPanel extends JScrollPane implements Observer {
    private JTextArea logText;

    public LoggerPanel() {
        Logger.getInstance().subscribe(this);

        logText = new JTextArea();
        logText.setFont(new Font("Consolas", Font.PLAIN, 22));
        logText.setLineWrap(true);
        logText.setEditable(false);

        this.setPreferredSize(new Dimension(Short.MAX_VALUE, (int) (startDimension.getHeight() / 4)));
        this.setViewportView(logText);
        this.setVisible(false);
    }

    public String getText() {
        return logText.getText();
    }

    public void appendText(String s) {
        logText.append(String.format("%s\n", s));
    }

    public void clearText() {
        logText.setText("");
    }

    @Override
    public void update(Object obj) {
        this.appendText((String) obj);
    }
}