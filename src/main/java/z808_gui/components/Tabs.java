package z808_gui.components;

import logger.Logger;
import z808_gui.components.panels.AssemblyTextPane;
import z808_gui.components.panels.LogTextArea;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

import static z808_gui.utils.UIUtils.startDimension;

public class Tabs extends JTabbedPane {

    final LinkedList<AssemblyTextPane> editors = new LinkedList<AssemblyTextPane>();

    AssemblyTextPane assemblyArea;
    LogTextArea logArea;

    public Tabs() {
        this.setPreferredSize(new Dimension((int) ((startDimension.getWidth() / 3) * 2), Short.MAX_VALUE));

        this.assemblyArea = new AssemblyTextPane();
        this.logArea = new LogTextArea();

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
