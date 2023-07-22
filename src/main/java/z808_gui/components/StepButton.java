package z808_gui.components;

import virtual_machine.VirtualMachine;
import z808_gui.observerpattern.Listener;
import z808_gui.observerpattern.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static z808_gui.utils.UIUtils.*;

public class StepButton extends JLabel implements Listener {
    public StepButton(ImageIcon IMG, VirtualMachine vm) {
        super(IMG);
        this.setPreferredSize(new Dimension(CONTROLS_BUTTON_SIZE, CONTROLS_BUTTON_SIZE));
        setEnabled(false);

        MenuBar.subscribe(this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseExited(e);

                if (isEnabled()) {
                    try {
                        vm.loadProgram(PROGRAM_PATH);
                        vm.executeNextInstruction();
                        //updateWorkRegsLabels();
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(null, "O arquivo \"" + PROGRAM_PATH + "\" não existe!", "Erro", JOptionPane.ERROR_MESSAGE, null);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (getVisibleRect().contains(e.getPoint())) mouseEntered(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setIcon(STEP_ACTIVE_IMG);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                setIcon(STEP_DEFAULT_IMG);
            }
        });
    }

    @Override
    public void update(MessageType type) {
        switch (type) {
            case PATH_IS_SET -> {
                setEnabled(true);
            }
            case PATH_NOT_SET -> {
                setEnabled(false);
            }
        }
    }
}
