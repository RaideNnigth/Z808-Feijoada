package z808_gui.components;

import virtual_machine.registers.Registers;

import javax.swing.*;
import java.awt.*;

import static javax.swing.text.StyleConstants.setBackground;
import static z808_gui.utils.UIUtils.registersJLabelsMap;
import static z808_gui.utils.UIUtils.startDimension;

/*
public class MemoryPane extends JScrollPane {
    public MemoryPane() {
        setPreferredSize(new Dimension((int) startDimension.getWidth() / 3, Short.MAX_VALUE));
        setBackground(Color.white);

        // Inicializando labels
        Font fonteLabels = new Font("Arial", Font.PLAIN, 18);

        for (Registers r : Registers.values()) {
            var newLabel = new JLabel();
            newLabel.setFont(fonteLabels);
            newLabel.setText(r.getlabel());
            registersJLabelsMap.add(newLabel);
        }

        // Criando os grupos
        var hGroup = rightPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        var vGroup = rightPanelLayout.createSequentialGroup();

        // Populando com os labels de registradores
        for (JLabel reg : registersJLabels) {
            hGroup.addComponent(reg);
            vGroup.addComponent(reg);
        }

        rightPanelLayout.setHorizontalGroup(hGroup);
        rightPanelLayout.setVerticalGroup(vGroup);
    }
}
*/