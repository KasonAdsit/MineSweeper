package window;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Image;

public class MakeWindow {

    public static JFrame frame = new JFrame();

    public static void create(){

        int screenWidth;
        int screenHeight;

        //initialize the frame and place panel on it
        frame.setTitle("Minesweeper!");
        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) (screenSize.width * 0.9); // 90% of screen width
        screenHeight = (int) (screenSize.height * 0.9); // 90% of screen height
        frame.setSize(screenWidth, screenHeight);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.add(panel, BorderLayout.CENTER);
        //panel.setBackground(new Color(100,100,100));

        // Load an icon image
        Image icon = Toolkit.getDefaultToolkit().getImage("assets/icon.png");
        frame.setIconImage(icon);  //sets icon for taskbar and title bar

        frame.setVisible(true);
    }

    public static void setPanel(JPanel panel) {
        frame.getContentPane().removeAll();     // clear everything
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.revalidate();  // re-layout the frame
        frame.repaint();     // redraw the frame
    }
    
}