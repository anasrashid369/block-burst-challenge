package com.blockburst.model;

import java.awt.Color;
import java.util.Random;

public class PieceGenerator {
    private static final int[][][] SHAPES = {
        {{1}},                                   // 0 single
        {{1, 1}},                                // 1 line 2
        {{1, 1, 1}},                             // 2 line 3
        {{1, 1, 1, 1}},                          // 3 line 4
        {{1, 1, 1, 1, 1}},                       // 4 line 5
        {{1, 1}, {1, 1}},                        // 5 square 2x2
        {{1, 1, 1}, {1, 1, 1}},                  // 6 rectangle 2x3
        {{1, 0}, {1, 0}, {1, 1}},                // 7 L
        {{1, 1, 1}, {0, 1, 0}},                  // 8 T
        {{0, 1, 1}, {1, 1, 0}},                  // 9 S
        {{1, 1, 0}, {0, 1, 1}}                   // 10 Z
    };

    private static final Color[] COLORS = {
        new Color(231, 76, 60),  new Color(230, 126, 34), new Color(241, 196, 15),
        new Color(46, 204, 113), new Color(26, 188, 156), new Color(52, 152, 219),
        new Color(155, 89, 182), new Color(233, 30, 99),  new Color(0, 150, 136),
        new Color(121, 85, 72),  new Color(96, 125, 139)
    };

    private final Random random = new Random();

    public BlockPiece generatePiece() {
        int i = random.nextInt(SHAPES.length);
        BlockPiece p = new BlockPiece(SHAPES[i], COLORS[i]);
        int turns = random.nextInt(4);          // 0-3 rotations
        for (int t = 0; t < turns; t++) p = p.rotated();
        return p;
    }

    public BlockPiece[] generateTray() {
        BlockPiece[] tray = new BlockPiece[3];
        for (int i = 0; i < 3; i++) tray[i] = generatePiece();
        return tray;
    }
}