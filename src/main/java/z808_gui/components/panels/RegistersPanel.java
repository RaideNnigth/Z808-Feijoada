package z808_gui.components.panels;

import virtual_machine.observerpattern.RegObsListener;
import virtual_machine.registers.Registers;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import static z808_gui.utils.UIUtils.*;

public class RegistersPanel extends JPanel implements RegObsListener {
    private boolean displayInDecimal = true;
    private final HashMap<String, JLabel> registersHashMap = new HashMap<>();
    private HashMap<Registers, Short> currentWorkRegs;
    private final String[] flagRegs = new String[]{"OF", "SF", "ZF", "IF", "PF", "CF"};

    public RegistersPanel() {
        //this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10,10,10,10));

        // Its size will be fixed 1/3 of the start dimension
        this.setPreferredSize(new Dimension(200, Short.MAX_VALUE));
        //this.setBackground(Color.white);

        JPanel registers = new JPanel();
        GridLayout gridLayout = new GridLayout(14, 2, 20, 15);
        gridLayout.setHgap(0);
        gridLayout.setVgap(0);
        registers.setLayout(gridLayout);

        // Inicializando labels
        Font fonteLabels = new Font("Arial", Font.PLAIN, 14);

        for (Registers r : Registers.values()) {
            if (!r.getLabel().equals("SR")) {
                JLabel lblReg = new JLabel(r.getLabel());
                lblReg.setHorizontalAlignment(JLabel.CENTER);
                lblReg.setPreferredSize(new Dimension(80, 20));
                lblReg.setFont(fonteLabels);
                lblReg.setBorder(new EtchedBorder());
                registers.add(lblReg);

                JLabel lblValue = new JLabel("0");
                lblValue.setHorizontalAlignment(JLabel.CENTER);
                lblValue.setPreferredSize(new Dimension(80, 20));
                lblValue.setFont(fonteLabels);
                lblValue.setBorder(new EtchedBorder());
                registers.add(lblValue);

                registersHashMap.put(r.getLabel(), lblValue);
            }
        }

        for (String regFlag : this.flagRegs) {
            JLabel lblReg = new JLabel(regFlag);
            lblReg.setHorizontalAlignment(JLabel.CENTER);
            lblReg.setPreferredSize(new Dimension(80, 20));
            lblReg.setFont(fonteLabels);
            lblReg.setBorder(new EtchedBorder());
            registers.add(lblReg);

            JLabel lblValue = new JLabel("0");
            lblValue.setHorizontalAlignment(JLabel.CENTER);
            lblValue.setPreferredSize(new Dimension(80, 20));
            lblValue.setFont(fonteLabels);
            lblValue.setBorder(new EtchedBorder());
            registers.add(lblValue);

            this.registersHashMap.put(regFlag, lblValue);
        }


        // Criando os grupos
        //var hGroup = rightPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        //var vGroup = rightPanelLayout.createSequentialGroup();

        // Populando os grupos com os labels de registradores
        //for (Registers r : Registers.values()) {
        //    hGroup.addComponent(registersJLabelsMap.get(r));
        //    vGroup.addComponent(registersJLabelsMap.get(r));
        //}

        // ------------------- Creating and configuring radio buttons of Hex and Decimal -------------------
        JRadioButton decimalRButton = new JRadioButton("Decimal");
        decimalRButton.setMnemonic(KeyEvent.VK_D);
        decimalRButton.setSelected(true);
        decimalRButton.setHorizontalAlignment(SwingConstants.CENTER);

        JRadioButton hexRButton = new JRadioButton("Hex");
        hexRButton.setMnemonic(KeyEvent.VK_H);
        hexRButton.setHorizontalAlignment(SwingConstants.CENTER);

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
        radioButtonsArea.setLayout(new BorderLayout());

        JPanel radioButtonsContainer = new JPanel();

        var rbLayout = new FlowLayout(FlowLayout.CENTER);
        radioButtonsContainer.setLayout(rbLayout);

        radioButtonsContainer.add(decimalRButton);
        radioButtonsContainer.add(hexRButton);

        radioButtonsArea.add(BorderLayout.SOUTH, radioButtonsContainer);

        this.add(registers);
        this.add(radioButtonsArea);
        //this.add(registers, BorderLayout.NORTH);
        //this.add(radioButtonsArea, BorderLayout.SOUTH);

        // Adding radio buttons to RegistersPanel
        //hGroup.addComponent(radioButtonsArea);
        //vGroup.addComponent(radioButtonsArea);

        //rightPanelLayout.setHorizontalGroup(hGroup);
        //rightPanelLayout.setVerticalGroup(vGroup);
    }

    @Override
    public void updatedRegs(HashMap<Registers, Short> workRegs, HashMap<String, Short> flagRegs) {
        currentWorkRegs = workRegs;

        for (Registers r : Registers.values()) {
            if (r != Registers.SR) {
                this.registersHashMap.get(r.getLabel()).setText(displayInDecimal ? String.valueOf(workRegs.get(r)) : Integer.toHexString(workRegs.get(r)).toUpperCase());
            }
        }

        for (String flagRegName : this.flagRegs) {
            this.registersHashMap.get(flagRegName).setText(String.valueOf(flagRegs.get(flagRegName)));
        }
    }

    private void convertValues() {
        if (displayInDecimal) {
            for (Registers r : Registers.values()) {
                if (r != Registers.SR) {
                    this.registersHashMap.get(r.getLabel()).setText(String.valueOf(this.currentWorkRegs.get(r)));
                }
            }
        } else {
            for (Registers r : Registers.values()) {
                if (r != Registers.SR) {
                    this.registersHashMap.get(r.getLabel()).setText(Integer.toHexString(currentWorkRegs.get(r)).toUpperCase());
                }
            }
        }
    }
}
