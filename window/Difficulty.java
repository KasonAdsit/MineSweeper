package window;

import logic.BoardLogic;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class Difficulty {
    public static void chooseDifficulty() {
        JPanel panel = new JPanel();
        JButton easy = new JButton("Easy");
        JButton medium = new JButton("Medium");
        JButton hard = new JButton("Hard");
        JButton custom = new JButton("Custom");

        panel.setBackground(new Color(54,121,50));
        panel.setLayout(new GridBagLayout());
        // creates a layout on the panel for the buttons. It is anchored to the center with 30px spaces between the buttons
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 0, 30, 0);
        gbc.fill = GridBagConstraints.NONE;

        easy.setFocusable(false);
        easy.setFont(new Font("Dialog", Font.PLAIN, 15));
        easy.setPreferredSize(new Dimension(200, 75));
        easy.setBackground(new Color(0, 115, 150));
        gbc.gridy = 1;
        panel.add(easy, gbc);
        easy.addActionListener(e -> {

            BoardLogic logicBoard = new BoardLogic(8,10,10);
            Board.show(logicBoard);
            
        });

        medium.setFocusable(false);
        medium.setFont(new Font("Dialog", Font.PLAIN, 15));
        medium.setPreferredSize(new Dimension(200, 75));
        medium.setBackground(new Color(160, 208, 72));
        gbc.gridy = 2;
        panel.add(medium, gbc);
        medium.addActionListener(e -> {

            BoardLogic logicBoard = new BoardLogic(14,18,40);
            Board.show(logicBoard);
            
        });

        hard.setFocusable(false);
        hard.setFont(new Font("Dialog", Font.PLAIN, 15));
        hard.setPreferredSize(new Dimension(200, 75));
        hard.setBackground(new Color(170, 33, 27));
        gbc.gridy = 3;
        panel.add(hard, gbc);
        hard.addActionListener(e -> {

            BoardLogic logicBoard = new BoardLogic(20,24,99);
            Board.show(logicBoard);
            
        });

        custom.setFocusable(false);
        custom.setFont(new Font("Dialog", Font.PLAIN, 15));
        custom.setPreferredSize(new Dimension(200, 75));
        custom.setBackground(new Color(230, 194, 158));
        gbc.gridy = 4;
        panel.add(custom, gbc);
        custom.addActionListener(e -> {
            JTextField rowField = new JTextField(5);
            JTextField colField = new JTextField(5);
            JTextField bombField = new JTextField(5);

            JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
            inputPanel.add(new JLabel("Rows:"));
            inputPanel.add(rowField);
            inputPanel.add(new JLabel("Columns:"));
            inputPanel.add(colField);
            inputPanel.add(new JLabel("Bombs:"));
            inputPanel.add(bombField);

            int result = JOptionPane.showConfirmDialog(
                null, inputPanel, 
                "Custom Game Settings", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                try {
                    int rows = Integer.parseInt(rowField.getText());
                    int cols = Integer.parseInt(colField.getText());
                    int bombs = Integer.parseInt(bombField.getText());

                    // Validation checks
                    if (rows <= 0 || cols <= 0) {
                        JOptionPane.showMessageDialog(null, "Rows and columns must be positive integers.");
                        return;
                    }

                    if (rows * cols == 1) {
                        JOptionPane.showMessageDialog(null, "Board size too small.");
                        return;
                    }

                    if (bombs <= 0 || bombs >= rows * cols) {
                        JOptionPane.showMessageDialog(null, "Number of bombs must be positive and less than the number of tiles.");
                        return;
                    }

                    BoardLogic logicBoard = new BoardLogic(rows, cols, bombs);
                    Board.show(logicBoard);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid integers.");
                }
            }
        });

        MakeWindow.setPanel(panel);
    }

}