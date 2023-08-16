package z808_gui.components;

import assembler.Assembler;
import virtual_machine.VirtualMachine;
import z808_gui.observerpattern.Listener;
import z808_gui.observerpattern.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.CharArrayReader;
import java.io.FileWriter;
import java.io.IOException;

import static z808_gui.utils.UIUtils.*;

public class PlayButton extends JLabel implements Listener {
    public PlayButton(ImageIcon IMG, VirtualMachine vm) {
        super(IMG);
        setPreferredSize(new Dimension(CONTROLS_BUTTON_SIZE, CONTROLS_BUTTON_SIZE));
        setEnabled(false);

        MenuBar.subscribe(this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseExited(e);

                if (isEnabled()) {
                    try {
                        // Salva arquivo
                        try (FileWriter fw = new FileWriter(PROGRAM_PATH)) {
                            // Salva arquivo
                            fw.write(AssemblyTextArea.getInstance().getText());
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }

                        // Monta o programa
                        Assembler.getInstance().assembleFile(PROGRAM_PATH);

                        // binPath
                        String binPath = (PROGRAM_PATH.substring(0, PROGRAM_PATH.length() - 4) + ".bin");

                        // Load bin
                        vm.loadProgram(binPath);
                        vm.executeProgram();

                        var tabbedPane = Tabs.getInstance();
                        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(null, "O arquivo \"" + PROGRAM_PATH + "\" não existe!", "Erro", JOptionPane.ERROR_MESSAGE, null);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.ERROR_MESSAGE, null);
                        System.out.println(ex);
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (getVisibleRect().contains(e.getPoint()))
                    mouseEntered(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setIcon(PLAY_HOVER_IMG);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                setIcon(PLAY_DEFAULT_IMG);
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
