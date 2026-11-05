package com.blockburst.model;

import java.awt.Color;

public class Board {
    private final int size = 8;   // TODO: switch to Constants.BOARD_SIZE when util is pushed
    private final Color[][] grid = new Color[size][size];   // null = empty  // null = empty

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