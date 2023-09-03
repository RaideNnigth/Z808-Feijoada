package z808_gui.components.panels;

import virtual_machine.VirtualMachine;

import javax.swing.*;
import java.awt.*;

import static z808_gui.utils.UIUtils.CONTROLS_BUTTON_SIZE;
import static z808_gui.utils.UIUtils.VERTICAL_SEPARATOR;

public class CentralPanel extends JPanel {
    Tabs tabs;
    LoggerPanel loggerPanel;

    public CentralPanel() {
        this.tabs = new Tabs();

        this.loggerPanel = new LoggerPanel();
        this.loggerPanel.setPreferredSize(new Dimension(800, 200));

        this.add(this.tabs, BorderLayout.NORTH);
        this.add(this.loggerPanel, BorderLayout.SOUTH);
    }

    public Tabs getTabs() {
        return tabs;
    }

    public LoggerPanel getLoggerPanel() {
        return loggerPanel;
    }
}
