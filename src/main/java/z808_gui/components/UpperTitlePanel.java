package z808_gui.components;

import z808_gui.utils.UIUtils;

import javax.swing.*;
import java.awt.*;

public class UpperTitlePanel extends JPanel {
    public UpperTitlePanel() {
        this.setPreferredSize(new Dimension(0, 100));
        this.setBackground(BROWN_COLOR);
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.add(Box.createRigidArea(H_SPACER));

        // Criando o logo
        Image feijoadaLogoIMG = UIUtils.resizeImage(Z808_FEIJOADA_LOGO_IMG_PATH, FEIJOADA_LOGO_SIZE_X, FEIJOADA_LOGO_SIZE_Y, Image.SCALE_SMOOTH);
        JLabel Z808_LOGO = new JLabel(new ImageIcon(feijoadaLogoIMG));
        Z808_LOGO.setPreferredSize(new Dimension(FEIJOADA_LOGO_SIZE_X, FEIJOADA_LOGO_SIZE_X));

        // Adicionando o logo
        this.add(Z808_LOGO);
    }
}
