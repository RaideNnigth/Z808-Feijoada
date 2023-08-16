package z808_gui.components;

import javax.swing.*;
import java.awt.*;

public class LogTextArea extends JScrollPane {
    private JTextArea logText;
    private static LogTextArea instance = null;

    private LogTextArea() {
        logText = new JTextArea();
        logText.setFont(new Font("Consolas", Font.PLAIN, 22));
        logText.setLineWrap(true);
        logText.setEditable(false);
        setViewportView(logText);
    }

    public static LogTextArea getInstance() {
        if (instance == null)
            instance = new LogTextArea();

        return instance;
    }

    public String getText() {
        return logText.getText();
    }

    public void appendText(String s) {
        logText.append(s);
    }

    public void clearText() {
        logText.setText("");
    }
}
