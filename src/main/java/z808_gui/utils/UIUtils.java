package z808_gui.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UIUtils {
    public static Image resizeImage(File img, int width, int height, int algorithm) {
        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(img);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return bufferedImage.getScaledInstance(width, height, algorithm);
    }
}
