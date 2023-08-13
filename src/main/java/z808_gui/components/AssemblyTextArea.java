package z808_gui.components;

import javax.swing.*;
import java.awt.*;

public class AssemblyTextArea extends JScrollPane {
    private JTextArea assemblyTextEditor;
    private static AssemblyTextArea instance = null;

    private AssemblyTextArea() {
        assemblyTextEditor = new JTextArea();
        assemblyTextEditor.setFont(new Font("Consolas", Font.PLAIN, 22));
        setViewportView(assemblyTextEditor);
    }

    public static AssemblyTextArea getInstance() {
        if (instance == null)
            instance = new AssemblyTextArea();

        return instance;
    }

    public String getText() {
        return assemblyTextEditor.getText();
    }

    public void setText(String s) {
        assemblyTextEditor.setText(s);
    }
}
