package z808_gui.components;

import javax.swing.*;
import java.awt.*;

public class AssemblyTextArea extends JScrollPane {
    public AssemblyTextArea() {
        JTextArea assemblyTextEditor = new JTextArea();
        assemblyTextEditor.setFont(new Font("Consolas", Font.PLAIN, 22));
        setViewportView(assemblyTextEditor);
    }
}
