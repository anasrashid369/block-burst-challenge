package com.blockburst.model;

import java.awt.Color;
import java.util.Random;

public class PieceGenerator {
    // The 20 shapes from the assignment sheet (1 = filled cell)
    private static final int[][][] SHAPES = {
        {{1}},                                  // 0  single
        {{1, 1}},                               // 1  line 1x2 (horizontal)
        {{1}, {1}},                             // 2  line 2x1 (vertical)
        {{1, 1, 1}},                            // 3  line 1x3 (horizontal)
        {{1}, {1}, {1}},                        // 4  line 3x1 (vertical)
        {{1, 1, 1, 1}},                         // 5  line 1x4 (horizontal)
        {{1}, {1}, {1}, {1}},                   // 6  line 4x1 (vertical)
        {{1, 1}, {1, 1}},                       // 7  square 2x2
        {{1, 1, 1}, {1, 1, 1}},                 // 8  rectangle 3x2 (horizontal)
        {{1, 1}, {1, 1}, {1, 1}},               // 9  rectangle 2x3 (vertical)
        {{1, 1}, {1, 0}, {1, 0}},               // 10 L (top-right)
        {{1, 1}, {0, 1}, {0, 1}},               // 11 L (top-left)
        {{1, 0}, {1, 0}, {1, 1}},               // 12 L (bottom-right)
        {{0, 1}, {0, 1}, {1, 1}},               // 13 L (bottom-left)
        {{1, 1, 1}, {0, 1, 0}},                 // 14 T (top)
        {{0, 1, 0}, {1, 1, 1}},                 // 15 T (down)
        {{1, 0}, {1, 1}, {1, 0}},               // 16 T (left)
        {{0, 1}, {1, 1}, {0, 1}},               // 17 T (right)
        {{0, 1, 1}, {1, 1, 0}},                 // 18 S (horizontal)
        {{1, 0}, {1, 1}, {0, 1}}                // 19 S (vertical)
    };

    private static final Color RED    = new Color(231, 76, 60);
    private static final Color ORANGE = new Color(230, 126, 34);
    private static final Color YELLOW = new Color(241, 196, 15);
    private static final Color GREEN  = new Color(46, 204, 113);
    private static final Color CYAN   = new Color(26, 188, 220);
    private static final Color BLUE   = new Color(52, 152, 219);
    private static final Color PURPLE = new Color(155, 89, 182);
    private static final Color PINK   = new Color(233, 100, 160);
    private static final Color TEAL   = new Color(0, 188, 160);

    // same order as SHAPES
    private static final Color[] COLORS = {
        RED, BLUE, GREEN, YELLOW, PURPLE, ORANGE, CYAN,
        RED, PINK, PURPLE,
        YELLOW, BLUE, GREEN, RED,
        CYAN, ORANGE, PURPLE, PINK,
        GREEN, TEAL
    };

    private final Random random = new Random();

    public BlockPiece generatePiece() {
        int i = random.nextInt(SHAPES.length);
        return new BlockPiece(SHAPES[i], COLORS[i]);
    }

    public BlockPiece[] generateTray() {
        BlockPiece[] tray = new BlockPiece[3];
        for (int i = 0; i < 3; i++) tray[i] = generatePiece();
        return tray;
    }
}