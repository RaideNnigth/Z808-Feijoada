package z808_gui.components;

import javax.swing.*;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class DependenciesWindow extends JFrame {
    private LinkedList<String> dependenciesPath;
    JTextField txtDependencies;

    public DependenciesWindow(LinkedList<String> dependenciesPath) {
        this.setTitle("Dependências");
        this.setLayout(new BorderLayout());

        this.dependenciesPath = dependenciesPath;

        JLabel lblDependencies = new JLabel("Dependencies");
        txtDependencies = new JTextField();
        //txtDependencies.setText(dependenciesPath.);

        JPanel btnsGroup = new JPanel();
        btnsGroup.setLayout(new BorderLayout());

        JButton btnOk = new JButton("Ok");
        btnOk.addActionListener(e -> this.btnOkClick());
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> this.btnCancelClick());

        btnsGroup.add(btnOk, BorderLayout.WEST);
        btnsGroup.add(btnCancel, BorderLayout.EAST);

        this.add(lblDependencies, BorderLayout.NORTH);
        this.add(txtDependencies, BorderLayout.CENTER);
        this.add(btnsGroup, BorderLayout.SOUTH);

        this.setSize(new Dimension(300, 200));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void btnOkClick() {
        String[] temp = this.txtDependencies.getText().split(";");
        this.dependenciesPath.addAll(List.of(temp));
        this.dispose();
    }

    private void btnCancelClick() {
        this.dispose();
    }
}
