package z808_gui.utils;

import virtual_machine.registers.Registers;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class UIUtils {
    public static final Dimension startDimension = new Dimension(1200, 800);
    // IMGs paths
    public static final String PLAY_DEFAULT_IMG_PATH = "src/main/java/z808_gui/imgs/graoFeijao.png";
    public static final String PLAY_ACTIVE_IMG_PATH = "src/main/java/z808_gui/imgs/graoFeijaoActive.png";
    public static final String Z808_FEIJOADA_LOGO_IMG_PATH = "src/main/java/z808_gui/imgs/Z808FEIJOADALOGO.png";
    public static final String STEP_DEFAULT_IMG_PATH = "src/main/java/z808_gui/imgs/stepFeijao.png";
    public static final String STEP_ACTIVE_IMG_PATH = "src/main/java/z808_gui/imgs/stepFeijaoActive.png";

    // Play and Step icon
    public static final int CONTROLS_BUTTON_SIZE = 60;
    public static final ImageIcon PLAY_DEFAULT_IMG = new ImageIcon(UIUtils.resizeImage(PLAY_DEFAULT_IMG_PATH, CONTROLS_BUTTON_SIZE, CONTROLS_BUTTON_SIZE, Image.SCALE_SMOOTH));
    public static final ImageIcon PLAY_HOVER_IMG = new ImageIcon(UIUtils.resizeImage(PLAY_ACTIVE_IMG_PATH, CONTROLS_BUTTON_SIZE, CONTROLS_BUTTON_SIZE, Image.SCALE_SMOOTH));
    public static final ImageIcon STEP_DEFAULT_IMG = new ImageIcon(UIUtils.resizeImage(STEP_DEFAULT_IMG_PATH, CONTROLS_BUTTON_SIZE, CONTROLS_BUTTON_SIZE, Image.SCALE_SMOOTH));
    public static final ImageIcon STEP_ACTIVE_IMG = new ImageIcon(UIUtils.resizeImage(STEP_ACTIVE_IMG_PATH, CONTROLS_BUTTON_SIZE, CONTROLS_BUTTON_SIZE, Image.SCALE_SMOOTH));

    // Logo Z808
    public static final int FEIJOADA_LOGO_SIZE_X = 455;
    public static final int FEIJOADA_LOGO_SIZE_Y = 75;
    public static final Color BROWN_COLOR = new Color(0x5a473d);

    // Path to active program
    public static String PROGRAM_PATH = "";

    // Registers labels and stuff
    public static final HashMap<Registers, JLabel> registersJLabelsMap = new HashMap<>();
    //public static final RegFlags flagRegister = VirtualMachine.getFlagsRegister();

    // Spacers
    public static final Dimension H_SPACER = new Dimension(10, 0);
    public static final JSeparator verticalSep = new JSeparator(SwingConstants.VERTICAL);


    public static Image resizeImage(String imgPath, int width, int height, int algorithm) {
        File img = new File(imgPath);
        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(img);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return bufferedImage.getScaledInstance(width, height, algorithm);
    }

    public static boolean isPathValid(String programPath) {
        File programBin = new File(programPath);

        if (programPath.isEmpty()) {
            return false;
        }

        return true;
    }
}
