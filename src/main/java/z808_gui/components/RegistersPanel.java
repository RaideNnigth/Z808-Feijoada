package z808_gui.components;

import virtual_machine.observerpattern.RegObsListener;
import virtual_machine.registers.Registers;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static z808_gui.utils.UIUtils.*;

public class RegistersPanel extends JPanel implements RegObsListener {
    public RegistersPanel() {
        GroupLayout rightPanelLayout = new GroupLayout(this);
        rightPanelLayout.setAutoCreateGaps(true);

        setLayout(rightPanelLayout);

        setPreferredSize(new Dimension((int) startDimension.getWidth() / 3, Short.MAX_VALUE));
        setBackground(Color.white);

        // Inicializando labels
        Font fonteLabels = new Font("Arial", Font.PLAIN, 18);

        for (Registers r : Registers.values()) {
            var newLabel = new JLabel();
            newLabel.setFont(fonteLabels);
            newLabel.setText(r.getlabel());
            registersJLabelsMap.put(r, newLabel);
        }

        // Criando os grupos
        var hGroup = rightPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        var vGroup = rightPanelLayout.createSequentialGroup();

        // Populando com os labels de registradores
        for (Registers r : Registers.values()) {
            hGroup.addComponent(registersJLabelsMap.get(r));
            vGroup.addComponent(registersJLabelsMap.get(r));
        }

        rightPanelLayout.setHorizontalGroup(hGroup);
        rightPanelLayout.setVerticalGroup(vGroup);
    }

    @Override
    public void updatedRegs(HashMap<Registers, Short> workRegs, String flagReg) {
        for (Registers r : Registers.values()) {
            if (r != Registers.SR)
                registersJLabelsMap.get(r).setText(r.getlabel() + " " + workRegs.get(r));
            else
                registersJLabelsMap.get(r).setText(r.getlabel() + " " + flagReg);
        }
    }
}
