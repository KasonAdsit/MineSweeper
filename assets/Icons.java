package assets;

import javax.swing.*;
import java.awt.*;

public class Icons {

    public static final ImageIcon FLAG;
    public static final ImageIcon BOMB;

    static {
        FLAG = loadIcon("assets/flag.png", 24, 24);
        BOMB = loadIcon("assets/bomb.png", 24, 24);
    }

    private static ImageIcon loadIcon(String path, int w, int h) {
        ImageIcon raw = new ImageIcon(path);
        Image scaled = raw.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}