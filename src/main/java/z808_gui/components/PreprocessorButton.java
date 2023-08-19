package z808_gui.components;

import virtual_machine.VirtualMachine;
import z808_gui.observerpattern.ProgramPathListener;
import z808_gui.observerpattern.MessageType;
import z808_gui.observerpattern.ProgramPathEventManager;
import z808_gui.utils.ActionsListeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static z808_gui.utils.UIUtils.*;

public class PreprocessorButton extends JLabel implements ProgramPathListener {
        public PreprocessorButton(ImageIcon IMG, VirtualMachine vm) {
            super(IMG);
            this.setPreferredSize(new Dimension(CONTROLS_BUTTON_SIZE, CONTROLS_BUTTON_SIZE));
            setEnabled(false);

            ProgramPathEventManager.getInstance().subscribe(this);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    mouseExited(e);

                    if (isEnabled()) {
                        ActionsListeners.getInstance(vm).getPreprocessarAL().actionPerformed(null);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (getVisibleRect().contains(e.getPoint())) mouseEntered(e);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    setIcon(ASSEM_ACTIVE_IMG);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    setIcon(ASSEM_DEFAULT_IMG);
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


