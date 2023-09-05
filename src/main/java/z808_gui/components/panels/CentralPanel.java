package z808_gui.components.panels;

import virtual_machine.VirtualMachine;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;

import static z808_gui.utils.UIUtils.CONTROLS_BUTTON_SIZE;
import static z808_gui.utils.UIUtils.VERTICAL_SEPARATOR;

public class CentralPanel extends JSplitPane {
    JTabbedPane tabs;
    LoggerPanel loggerPanel;

    public CentralPanel() {
        super(JSplitPane.VERTICAL_SPLIT);
        this.tabs = new JTabbedPane();

        this.loggerPanel = new LoggerPanel();
        //this.loggerPanel.setPreferredSize(new Dimension(800, 200));

        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        //this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(this.tabs);
        this.add(this.loggerPanel);

        this.setDividerLocation(0.25);
    }

    public JTabbedPane getTabs() {
        return tabs;
    }

    public LoggerPanel getLoggerPanel() {
        return loggerPanel;
    }
}
