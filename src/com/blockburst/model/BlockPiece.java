package com.blockburst.model;

import java.awt.Color;

public class BlockPiece {
    private final int[][] cells;   // 1 = filled, 0 = empty
    private final Color color;

    public BlockPiece(int[][] cells, Color color) {
        if (cells == null || cells.length == 0 || cells[0].length == 0)
            throw new IllegalArgumentException("Piece cannot be empty");
        int cols = cells[0].length;
        int filled = 0;
        this.cells = new int[cells.length][cols];
        for (int r = 0; r < cells.length; r++) {
            if (cells[r].length != cols)
                throw new IllegalArgumentException("Piece must be rectangular");
            for (int c = 0; c < cols; c++) {
                this.cells[r][c] = cells[r][c] != 0 ? 1 : 0;
                filled += this.cells[r][c];
            }
        }
        if (filled == 0) throw new IllegalArgumentException("Piece has no cells");
        if (!isConnected()) throw new IllegalArgumentException("Piece must be connected");
        this.color = color;
    }

    // Flood fill from the first filled cell; every filled cell must be reached.
    private boolean isConnected() {
        int rows = getRows(), cols = getCols();
        boolean[][] seen = new boolean[rows][cols];
        int total = 0, startR = -1, startC = -1;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (cells[r][c] == 1) {
                    total++;
                    if (startR < 0) { startR = r; startC = c; }
                }
        return flood(startR, startC, seen) == total;
    }

    private int flood(int r, int c, boolean[][] seen) {
        if (r < 0 || c < 0 || r >= getRows() || c >= getCols()) return 0;
        if (cells[r][c] == 0 || seen[r][c]) return 0;
        seen[r][c] = true;
        return 1 + flood(r + 1, c, seen) + flood(r - 1, c, seen)
                 + flood(r, c + 1, seen) + flood(r, c - 1, seen);
    }

    public int[][] getCells() {            // defensive copy
        int[][] copy = new int[getRows()][getCols()];
        for (int r = 0; r < getRows(); r++)
            copy[r] = cells[r].clone();
        return copy;
    }

    public int getRows() { return cells.length; }
    public int getCols() { return cells[0].length; }
    public Color getColor() { return color; }

    public int getCellCount() {
        int n = 0;
        for (int[] row : cells) for (int v : row) n += v;
        return n;
    }

    // Rotate 90 degrees clockwise: (r, c) -> (c, rows-1-r)
    public BlockPiece rotated() {
        int rows = getRows(), cols = getCols();
        int[][] out = new int[cols][rows];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                out[c][rows - 1 - r] = cells[r][c];
        return new BlockPiece(out, color);
    }
}