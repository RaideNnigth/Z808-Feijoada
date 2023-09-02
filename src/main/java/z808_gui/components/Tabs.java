package z808_gui.components;

import logger.Logger;
import z808_gui.components.panels.AssemblyTextPane;

import javax.swing.*;
import java.awt.*;

import static z808_gui.utils.UIUtils.startDimension;

public class Tabs extends JTabbedPane {
    AssemblyTextPane assemblyArea;
    LogTextArea logArea;

    public Tabs() {
        this.setPreferredSize(new Dimension((int) ((startDimension.getWidth() / 3) * 2), Short.MAX_VALUE));

        this.assemblyArea = new AssemblyTextPane();
        this.logArea = new LogTextArea();
        Logger.getInstance().subscribe(this.logArea);

        this.addTab("Programa", null, this.assemblyArea, "Escreva seu programa em FeijoadaZ808 Assembly");
        this.addTab("Log", null, this.logArea, "Logs de montagem");
    }

    public AssemblyTextPane getAssemblyArea() {
        return assemblyArea;
    }

    public LogTextArea getLogArea() {
        return logArea;
    }
}
