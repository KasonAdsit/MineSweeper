package window;

import logic.BoardLogic;
import logic.Cell;
import assets.Icons;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

public class Board {

    static boolean isGameOver = false;
    static JLabel flagLabel;
    static int flagsRemaining;
    static Timer gameTimer;
    static JLabel timerLabel;
    static int timeElapsed = 0;
    static JPanel  fullPanel;
    public static final ImageIcon FLAG = Icons.FLAG;
    public static final ImageIcon BOMB = Icons.BOMB;

    public static void show(BoardLogic logicBoard){
        int rows = logicBoard.rows;
        int cols = logicBoard.cols;
        JButton[][] buttons = new JButton[rows][cols];
        flagsRemaining = logicBoard.bombs; // Initialize from logic
        flagLabel = new JLabel("Flags: " + flagsRemaining);
        timerLabel = new JLabel("Time: 0s");
        Font labelFont = new Font("SansSerif", Font.BOLD, 16);
        flagLabel.setFont(labelFont);
        timerLabel.setFont(labelFont);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10)); // 30px horizontal gap, 10px top padding
        topPanel.add(flagLabel);
        topPanel.add(timerLabel);


        JPanel panel = new JPanel(new GridLayout(rows, cols));

        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){                
                JButton btn = new JButton();
                int row = r;
                int col = c;
                btn.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            toggleFlag(logicBoard.grid[row][col], btn);
                            flagLabel.setText("Flags: " + flagsRemaining);

                        } else if (SwingUtilities.isLeftMouseButton(e)) {
                            revealCell(row, col, logicBoard, buttons);
                            checkWin(logicBoard, buttons);
                        }
                    }
                });
                buttons[r][c] = btn;
                panel.add(btn);
            }
        }

        gameTimer = new Timer(1000, e -> {
            timeElapsed++;
            timerLabel.setText("Time: " + timeElapsed + "s");
        });

        fullPanel = new JPanel(new BorderLayout());
        fullPanel.add(topPanel, BorderLayout.NORTH);
        fullPanel.add(panel, BorderLayout.CENTER);

        MakeWindow.setPanel(fullPanel);

    }

    private static void toggleFlag(Cell cell, JButton btn) {
        if (cell.isRevealed) return;

        // Don’t allow more flags than available
        if (!cell.isFlagged && flagsRemaining == 0) return;

        cell.isFlagged = !cell.isFlagged;

       if (cell.isFlagged) {
            btn.setIcon(FLAG);
            flagsRemaining--;
        } 
        else {
            btn.setIcon(null);
            flagsRemaining++;
        }
    }

    private static void revealCell(int row, int col, BoardLogic logicBoard, JButton[][] buttons) {
        Cell cell = logicBoard.grid[row][col];

        if(isGameOver || cell.isRevealed || cell.isFlagged) return;

        if(!logicBoard.bombsPlaced){
            logicBoard.placeBombs(row, col);
            gameTimer.start();
            timeElapsed = 0;
            timerLabel.setText("Time: 0s");
        }

        cell.isRevealed = true;
        JButton btn = buttons[row][col];
        btn.setFocusable(false);
        btn.setContentAreaFilled(false);

        if (!cell.isBomb) {
            logicBoard.revealedSafeCells++;
        }

        if (cell.isBomb) {
            btn.setIcon(BOMB);
            btn.setFocusable(true);
            btn.setContentAreaFilled(true);
            btn.setBackground(Color.RED);
            gameOver(logicBoard, buttons);
            return;
        }

        if (cell.adjacentBombs > 0) {
            btn.setText(String.valueOf(cell.adjacentBombs));
            btn.setForeground(getNumberColor(cell.adjacentBombs));
        } 
        else {
            int rows = logicBoard.rows;
            int cols = logicBoard.cols;

            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr == 0 && dc == 0) continue;
                    int nr = row + dr;
                    int nc = col + dc;
                    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                        revealCell(nr, nc, logicBoard, buttons);
                    }
                }
            }
        }

    }



    private static void gameOver(BoardLogic logicBoard, JButton[][] buttons) {
        isGameOver = true;
        gameTimer.stop();
        
        
        

        for(int r = 0; r < logicBoard.rows; r++){
            for(int c = 0; c < logicBoard.cols; c++){
                Cell cell = logicBoard.grid[r][c];
                JButton btn = buttons[r][c];

                //btn.setEnabled(false);

                if(cell.isBomb){
                    btn.setIcon(BOMB);
                    btn.setBackground(Color.RED);
                }
                else if(cell.isFlagged && !cell.isBomb){
                    btn.setIcon(BOMB);
                    btn.setText("Incorrect Flag");//incorrect flag
                    btn.setBackground(Color.PINK);
                }
            }
        }

        
        // Centered relative to the game panel
        JOptionPane.showMessageDialog(
            fullPanel, // Pass the main panel you created in show()
            "Game Over! You hit a bomb!",
            "Game Over",
            JOptionPane.INFORMATION_MESSAGE
        );

        restartGame();

    
    }



    private static void checkWin(BoardLogic logicBoard, JButton[][] buttons) {
        if (logicBoard.revealedSafeCells == logicBoard.totalSafeCells) {
            for (int r = 0; r < logicBoard.rows; r++) {
                for (int c = 0; c < logicBoard.cols; c++) {
                    buttons[r][c].setEnabled(false);
                }
            }

            isGameOver = true;
            gameTimer.stop();


            // Centered relative to the game panel
            JOptionPane.showMessageDialog(
                fullPanel, // Pass the main panel you created in show()
                "Congratulations! You cleared the board!",
                "You Win!",
                JOptionPane.INFORMATION_MESSAGE
            );
            restartGame();
        }
    }

    private static void restartGame() {
        isGameOver = false;
        timeElapsed = 0;
        if (gameTimer != null) {
            gameTimer.stop();
        }
        Difficulty.chooseDifficulty();
    }

    private static Color getNumberColor(int count) {
        switch (count) {
            case 1: return new Color(3,0,251);
            case 2: return new Color(3,126,7); 
            case 3: return new Color(253,0,2);
            case 4: return new Color(2,1,128);
            case 5: return new Color(126,0,1); 
            case 6: return new Color(3,129,128); 
            case 7: return new Color(1,1,1);
            case 8: return new Color(128,128,128);
            default: return Color.BLACK;
        }
    }

}
