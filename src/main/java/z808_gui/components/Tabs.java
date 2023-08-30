package z808_gui.components;

import javax.swing.*;
import java.awt.*;

import static z808_gui.utils.UIUtils.startDimension;

public class Tabs extends JTabbedPane {
    private static Tabs instance = null;

    private Tabs() {
        this.setPreferredSize(new Dimension((int) (startDimension.getWidth() * 2 / 3), Short.MAX_VALUE));
    }

    public static Tabs getInstance() {
        if (instance == null)
            instance = new Tabs();
        return instance;
    }
}
