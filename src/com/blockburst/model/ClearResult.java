package com.blockburst.model;

import java.util.ArrayList;
import java.util.List;

public class ClearResult {
    private int rows, cols;
    private final List<int[]> cells = new ArrayList<>();

    void addRow() { rows++; }
    void addCol() { cols++; }
    void addCell(int r, int c) { cells.add(new int[]{r, c}); }

    public int getRowsCleared() { return rows; }
    public int getColsCleared() { return cols; }
    public int getLineCount() { return rows + cols; }
    public List<int[]> getClearedCells() { return cells; }
    public boolean isEmpty() { return cells.isEmpty(); }
}