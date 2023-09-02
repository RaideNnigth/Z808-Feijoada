package z808_gui.components.panels;

import virtual_machine.VirtualMachine;
import z808_gui.components.Tabs;

import javax.swing.*;
import java.awt.*;

import static z808_gui.utils.UIUtils.VERTICAL_SEPARATOR;

public class CentralPanel extends JPanel {
    GroupLayout centralPanelLayout;
    RegistersPanel rightRegistersPanel;
    Tabs tabs;

    public CentralPanel(VirtualMachine vm) {
        this.centralPanelLayout = new GroupLayout(this);
        this.setLayout(centralPanelLayout);
        this.setBackground(Color.white);

        this.centralPanelLayout.setAutoCreateGaps(true);
        this.centralPanelLayout.setAutoCreateContainerGaps(true);

        // ------------------------------ Red Panel (registradores) ------------------------------
        this.rightRegistersPanel = new RegistersPanel();
        vm.subscribe(this.rightRegistersPanel);
        vm.notifySubscribers(); // Display default values

        // ------------------------------ Criando Abas ------------------------------
        this.tabs = new Tabs();

        this.populate();
    }

    private void populate() {
        // ------------------------------ Populando região central ------------------------------
        this.centralPanelLayout.setHorizontalGroup(
                this.centralPanelLayout.createSequentialGroup()
                        .addComponent(this.tabs, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                        .addComponent(VERTICAL_SEPARATOR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(this.rightRegistersPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        this.centralPanelLayout.setVerticalGroup(
                this.centralPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(this.tabs)
                        .addComponent(VERTICAL_SEPARATOR)
                        .addComponent(this.rightRegistersPanel)
        );
    }

    public Tabs getTabs() {
        return tabs;
    }
}
