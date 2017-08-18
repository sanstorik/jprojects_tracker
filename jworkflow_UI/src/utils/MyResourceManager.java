package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Executable;

public final class MyResourceManager {

    public static final String RES_PATH = "D:\\BusinessGitProjects\\jworkflow_tracker\\jworkflow_UI\\res\\";
    private MyResourceManager() {}

    public static File loadFile(String path) {
        return new File(RES_PATH + path);
    }

    public static Image loadImage(String path) {
        Image img = null;
        try {
            img = ImageIO.read(loadFile(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return img;
    }
}
