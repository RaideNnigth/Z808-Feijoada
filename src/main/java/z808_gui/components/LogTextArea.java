package z808_gui.components;

import utils.Observer;

import javax.swing.*;
import java.awt.*;

public class LogTextArea extends JScrollPane implements Observer {
    private JTextArea logText;
    //private static LogTextArea instance = null;

    public LogTextArea() {
        logText = new JTextArea();
        logText.setFont(new Font("Consolas", Font.PLAIN, 22));
        logText.setLineWrap(true);
        logText.setEditable(false);
        setViewportView(logText);
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
