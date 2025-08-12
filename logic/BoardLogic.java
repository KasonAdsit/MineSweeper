package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BoardLogic {
    public Cell[][] grid;
    public int rows, cols, bombs;
    public boolean bombsPlaced = false;
    public int revealedSafeCells = 0;
    public int totalSafeCells;

    public BoardLogic(int rows, int cols, int bombs){
        this.rows = rows;
        this.cols = cols;
        this.bombs = bombs;
        this.totalSafeCells = rows * cols - bombs;
        grid = new Cell[rows][cols];

        //initilize
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                grid[i][j] = new Cell();
            }
        }
    }

    private int countAdjacent(int r, int c){
        int count = 0;
        for (int dr = -1; dr <=1; dr++){
            for(int dc = -1; dc <= 1; dc++){
                int nr = r + dr;
                int nc = c + dc;
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc].isBomb){
                    count++;
                }
            }
        }
        return count;
    }

    

    public void placeBombs(int safeRow, int safeCol){
        Random rand = new Random();
        int placed = 0;

        while(placed < bombs){
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);

            if(r == safeRow && c == safeCol || grid[r][c].isBomb){
                continue;
            }

            else if(rows * cols <= 9 || ((rows * cols) - 9) < bombs){
                grid[r][c].isBomb = true;
                placed++;
                System.out.println("Placed = " + placed);
                continue;
            }

            else if(isInSafeZone(r, c, safeRow, safeCol)){
                continue;
            }

            grid[r][c].isBomb = true;
            placed++;

        }

        //count adjacent bombs
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(!grid[i][j].isBomb){
                    grid[i][j].adjacentBombs = countAdjacent(i,j);
                }
            }
        }

        bombsPlaced = true;
    }

    

    /* 

    public void placeBombs(int safeRow, int safeCol) {
        Random rand = new Random();

        // Dynamically determine safe zone size
        int safeRadius = 1;
        if (rows < 3 || cols < 3) {
            safeRadius = 0;
        }

        // Generate list of all possible bomb positions excluding the safe zone
        List<int[]> availablePositions = new ArrayList<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                boolean inSafeZone = Math.abs(r - safeRow) <= safeRadius && Math.abs(c - safeCol) <= safeRadius;
                if (!inSafeZone) {
                    availablePositions.add(new int[]{r, c});
                }
            }
        }

        // Shuffle and select bomb positions
        Collections.shuffle(availablePositions);

        int bombsToPlace = bombs;
        for (int i = 0; i < availablePositions.size() && bombsToPlace > 0; i++) {
            int[] pos = availablePositions.get(i);
            int r = pos[0];
            int c = pos[1];
            grid[r][c].isBomb = true;
            bombsToPlace--;
        }

        // Update adjacent bomb counts
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!grid[r][c].isBomb) {
                    grid[r][c].adjacentBombs = countAdjacent(r, c);
                }
            }
        }

        bombsPlaced = true;
    }

    */

    private boolean isInSafeZone(int r, int c, int safeRow, int safeCol){
        return Math.abs(r - safeRow) <= 1 && Math.abs(c - safeCol) <= 1;
    }
}