package com.blockburst.model;

import java.awt.Color;
import com.blockburst.util.Constants;

public class Board {
   private final int size = Constants.BOARD_SIZE;
    private final Color[][] grid = new Color[size][size];   // null = empty 

    public boolean canPlace(BlockPiece p, int row, int col) {
        int[][] cells = p.getCells();
        for (int r = 0; r < p.getRows(); r++)
            for (int c = 0; c < p.getCols(); c++) {
                if (cells[r][c] == 0) continue;
                int br = row + r, bc = col + c;
                if (br < 0 || bc < 0 || br >= size || bc >= size) return false; // bounds
                if (grid[br][bc] != null) return false;                         // overlap
            }
        return true;
    }

    public void place(BlockPiece p, int row, int col) {
        if (!canPlace(p, row, col))
            throw new IllegalArgumentException("Invalid placement");
        int[][] cells = p.getCells();
        for (int r = 0; r < p.getRows(); r++)
            for (int c = 0; c < p.getCols(); c++)
                if (cells[r][c] == 1) grid[row + r][col + c] = p.getColor();
    }

        // Find ALL full rows and cols first, then clear together
    // so an intersection cell is cleared (and counted) only once.
    public ClearResult clearCompleted() {
        boolean[] fullRow = new boolean[size];
        boolean[] fullCol = new boolean[size];
        for (int i = 0; i < size; i++) { fullRow[i] = true; fullCol[i] = true; }

        // pass 1: any empty cell makes its row and its column not full
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                if (grid[r][c] == null) { fullRow[r] = false; fullCol[c] = false; }

        ClearResult result = new ClearResult();
        for (int r = 0; r < size; r++) if (fullRow[r]) result.addRow();
        for (int c = 0; c < size; c++) if (fullCol[c]) result.addCol();

        // pass 2: collect each cleared cell once
        boolean[][] mark = new boolean[size][size];
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                if ((fullRow[r] || fullCol[c]) && !mark[r][c]) {
                    mark[r][c] = true;
                    result.addCell(r, c);
                }

        // pass 3: only now actually empty the cells
        for (int[] cell : result.getClearedCells())
            grid[cell[0]][cell[1]] = null;

        return result;
    }
   

    public boolean canFitAnywhere(BlockPiece p) {
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                if (canPlace(p, r, c)) return true;
        return false;
    }

    public Color getCell(int r, int c) { return grid[r][c]; }

    public void reset() {
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++) grid[r][c] = null;
    }
}