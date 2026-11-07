package com.blockburst.model;

import com.blockburst.util.Constants;
import com.blockburst.util.GameMode;
import com.blockburst.util.GameState;
import com.blockburst.util.ScoreManager;

public class Game {
    private final Board board = new Board();
    private final PieceGenerator generator = new PieceGenerator();
    private final ScoreManager scoreManager = new ScoreManager();

    private BlockPiece[] tray = new BlockPiece[3];
    private GameState state = GameState.MENU;
    private GameMode mode = GameMode.CLASSIC;
    private int score;
    private int bestScore;
    private long elapsedMs;
    private long timeLeftMs;

    public void start(GameMode m) {
        mode = m;
        board.reset();
        tray = generator.generateTray();
        score = 0;
        elapsedMs = 0;
        timeLeftMs = Constants.TIME_ATTACK_SECONDS * 1000L;
        bestScore = scoreManager.loadBest();
        state = GameState.PLAYING;
    }

    // The ONLY place time advances
    public void update(long deltaMs) {
        if (state != GameState.PLAYING) return;
        elapsedMs += deltaMs;
        if (mode == GameMode.TIME_ATTACK) {
            timeLeftMs -= deltaMs;
            if (timeLeftMs <= 0) {
                timeLeftMs = 0;
                finish(score >= Constants.TIME_ATTACK_TARGET ? GameState.WON : GameState.LOST);
            }
        }
    }

    private void finish(GameState end) {
        state = end;
        if (score > bestScore) {
            bestScore = score;
            scoreManager.saveBest(score);
        }
    }

    public void pause()   { if (state == GameState.PLAYING) state = GameState.PAUSED; }
    public void resume()  { if (state == GameState.PAUSED)  state = GameState.PLAYING; }
    public void restart() { start(mode); }

    public int getScore() { return score; }
    public int getBestScore() { return Math.max(bestScore, score); }
    public long getElapsedMs() { return elapsedMs; }
    public long getTimeLeftMs() { return timeLeftMs; }
    public GameState getState() { return state; }
    public GameMode getMode() { return mode; }
    public BlockPiece[] getTray() { return tray; }
    public Board getBoard() { return board; }
}