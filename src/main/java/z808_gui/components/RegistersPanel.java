package z808_gui.components;

import javax.swing.*;
import java.awt.*;

import static z808_gui.utils.UIUtils.*;
import static z808_gui.utils.UIUtils.flagRegisterJLabel;

public class RegistersPanel extends JPanel {
    public RegistersPanel() {
        GroupLayout rightPanelLayout = new GroupLayout(this);
        rightPanelLayout.setAutoCreateGaps(true);

        setLayout(rightPanelLayout);

        setPreferredSize(new Dimension((int) startDimension.getWidth() / 3, Short.MAX_VALUE));
        setBackground(Color.white);

        // Inicializando labels
        Font fonteLabels = new Font("Arial", Font.PLAIN, 18);

        for (int i = 0; i < noWorkRegs; ++i) {
            var newLabel = new JLabel();
            newLabel.setFont(fonteLabels);
            workRegistersJLabels.add(newLabel);
        }
        flagRegisterJLabel.setFont(fonteLabels);

        for (int i = 0; i < noWorkRegs; ++i) {
            workRegistersJLabels.get(i).setText(workRegistersID.get(i));
        }
        flagRegisterJLabel.setText(flagRegisterID);

        // Populando com os labels de registradores
        var hGroup = rightPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        var vGroup = rightPanelLayout.createSequentialGroup();

        for (JLabel wReg : workRegistersJLabels) {
            hGroup.addComponent(wReg);
            vGroup.addComponent(wReg);
        }
        hGroup.addComponent(flagRegisterJLabel);
        vGroup.addComponent(flagRegisterJLabel);

        rightPanelLayout.setHorizontalGroup(hGroup);
        rightPanelLayout.setVerticalGroup(vGroup);
    }

}
