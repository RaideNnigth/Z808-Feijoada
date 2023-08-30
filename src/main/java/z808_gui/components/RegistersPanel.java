package z808_gui.components;

import virtual_machine.observerpattern.RegObsListener;
import virtual_machine.registers.Registers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import static z808_gui.utils.UIUtils.*;

public class RegistersPanel extends JPanel implements RegObsListener {
    private boolean displayInDecimal = true;
    private HashMap<Registers, Short> currentWorkRegs;

    public RegistersPanel() {
        GroupLayout rightPanelLayout = new GroupLayout(this);
        rightPanelLayout.setAutoCreateGaps(true);

        setLayout(rightPanelLayout);

        // Its size will be fixed 1/3 of the start dimension
        setPreferredSize(new Dimension((int) (startDimension.getWidth() / 3), Short.MAX_VALUE));
        setBackground(Color.white);

        // Inicializando labels
        Font fonteLabels = new Font("Consolas", Font.PLAIN, 22);

        for (Registers r : Registers.values()) {
            var newLabel = new JLabel();
            newLabel.setFont(fonteLabels);
            registersJLabelsMap.put(r, newLabel);
        }

        // Criando os grupos
        var hGroup = rightPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        var vGroup = rightPanelLayout.createSequentialGroup();

        // Populando os grupos com os labels de registradores
        for (Registers r : Registers.values()) {
            hGroup.addComponent(registersJLabelsMap.get(r));
            vGroup.addComponent(registersJLabelsMap.get(r));
        }

        // ------------------- Creating and configuring radio buttons of Hex and Decimal -------------------
        JRadioButton decimalRButton = new JRadioButton("Decimal");
        decimalRButton.setMnemonic(KeyEvent.VK_D);
        decimalRButton.setSelected(true);
        decimalRButton.setHorizontalAlignment(SwingConstants.CENTER);
        decimalRButton.setBackground(Color.white);

        JRadioButton hexRButton = new JRadioButton("Hex");
        hexRButton.setMnemonic(KeyEvent.VK_H);
        hexRButton.setHorizontalAlignment(SwingConstants.CENTER);
        hexRButton.setBackground(Color.white);

        ButtonGroup radioButtonsGroup = new ButtonGroup();
        radioButtonsGroup.add(decimalRButton);
        radioButtonsGroup.add(hexRButton);

        // Adding action listeners to radio buttons
        decimalRButton.addActionListener(e -> {
            displayInDecimal = true;
            convertValues();
        });
        hexRButton.addActionListener(e -> {
            displayInDecimal = false;
            convertValues();
        });

        // ------------ Configuring radio buttons area ------------
        JPanel radioButtonsArea = new JPanel();
        radioButtonsArea.setBackground(Color.white);
        radioButtonsArea.setLayout(new BorderLayout());

        JPanel radioButtonsContainer = new JPanel();
        radioButtonsContainer.setBackground(Color.white);

        var rbLayout = new FlowLayout(FlowLayout.CENTER);
        radioButtonsContainer.setLayout(rbLayout);

        radioButtonsContainer.add(decimalRButton);
        radioButtonsContainer.add(hexRButton);

        radioButtonsArea.add(BorderLayout.SOUTH, radioButtonsContainer);

        // Adding radio buttons to RegistersPanel
        hGroup.addComponent(radioButtonsArea);
        vGroup.addComponent(radioButtonsArea);

        rightPanelLayout.setHorizontalGroup(hGroup);
        rightPanelLayout.setVerticalGroup(vGroup);
    }

    @Override
    public void updatedRegs(HashMap<Registers, Short> workRegs, String flagReg) {
        currentWorkRegs = workRegs;

        for (Registers r : Registers.values()) {
            if (r != Registers.SR) {
                registersJLabelsMap.get(r).setText(r.getLabel() + " " + (displayInDecimal ? workRegs.get(r) : Integer.toHexString(workRegs.get(r))));
            } else
                registersJLabelsMap.get(r).setText("<html>" + r.getLabel() + "<br>" + flagReg);
        }
    }

    private void convertValues() {
        if (displayInDecimal) {
            for (Registers r : Registers.values()) {
                if (r != Registers.SR) {
                    registersJLabelsMap.get(r).setText(r.getLabel() + " " + currentWorkRegs.get(r));
                }
            }
        } else {
            for (Registers r : Registers.values()) {
                if (r != Registers.SR) {
                    registersJLabelsMap.get(r).setText(r.getLabel() + " " + Integer.toHexString(currentWorkRegs.get(r)));
                }
            }
        }
    }
}
