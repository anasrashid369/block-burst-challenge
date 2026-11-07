import com.blockburst.model.*;
import com.blockburst.util.*;
import java.awt.Color;

public class GameTest {
    static final BlockPiece ONE = new BlockPiece(new int[][]{{1}}, Color.RED);

    public static void main(String[] args) {
        testPauseResume();
        testTimeAttackTimer();
        testInvalidDropKeepsPiece();
        testValidPlacementScore();
        testTrayRefill();
        testGameOver();
    }

    static void check(String name, boolean ok) {
        System.out.println((ok ? "PASS  " : "FAIL  ") + name);
    }

    // first free spot where the piece fits, or null
    static int[] findSpot(Board b, BlockPiece p) {
        for (int r = 0; r < Constants.BOARD_SIZE; r++)
            for (int c = 0; c < Constants.BOARD_SIZE; c++)
                if (b.canPlace(p, r, c)) return new int[]{r, c};
        return null;
    }

    static void testPauseResume() {
        Game g = new Game();
        g.start(GameMode.CLASSIC);
        g.update(1000);
        check("elapsed is 1000 after 1s", g.getElapsedMs() == 1000);
        g.pause();
        g.update(5000);
        check("time stops while paused", g.getElapsedMs() == 1000);
        check("state is PAUSED", g.getState() == GameState.PAUSED);
        g.resume();
        g.update(500);
        check("resume continues without skipping time", g.getElapsedMs() == 1500);
        check("state is PLAYING again", g.getState() == GameState.PLAYING);
    }

    static void testTimeAttackTimer() {
        Game g = new Game();
        g.start(GameMode.TIME_ATTACK);
        long full = Constants.TIME_ATTACK_SECONDS * 1000L;
        check("starts at full time", g.getTimeLeftMs() == full);
        g.update(1000);
        check("countdown goes down", g.getTimeLeftMs() == full - 1000);
        g.update(full);
        check("time up with low score gives LOST", g.getState() == GameState.LOST);
        check("time left stops at 0", g.getTimeLeftMs() == 0);
    }

    static void testInvalidDropKeepsPiece() {
        Game g = new Game();
        g.start(GameMode.CLASSIC);
        BlockPiece before = g.getTray()[0];
        PlaceResult res = g.tryPlace(0, Constants.BOARD_SIZE, Constants.BOARD_SIZE);
        check("out of bounds is rejected", !res.isValid());
        check("piece stays in tray", g.getTray()[0] == before);
        check("score unchanged", g.getScore() == 0);
        check("bad slot is rejected", !g.tryPlace(5, 0, 0).isValid());
    }

    static void testValidPlacementScore() {
        Game g = new Game();
        g.start(GameMode.CLASSIC);
        BlockPiece p = g.getTray()[0];
        int[] spot = findSpot(g.getBoard(), p);
        PlaceResult res = g.tryPlace(0, spot[0], spot[1]);
        check("valid placement accepted", res.isValid());
        check("score equals cells placed", g.getScore() == p.getCellCount());
        check("piece removed from tray", g.getTray()[0] == null);
    }

    static void testTrayRefill() {
        Game g = new Game();
        g.start(GameMode.CLASSIC);
        for (int slot = 0; slot < 3; slot++) {
            BlockPiece p = g.getTray()[slot];
            int[] spot = findSpot(g.getBoard(), p);
            g.tryPlace(slot, spot[0], spot[1]);
        }
        boolean allFilled = true;
        for (BlockPiece p : g.getTray()) if (p == null) allFilled = false;
        check("new tray of 3 after all pieces used", allFilled);
        check("game still PLAYING", g.getState() == GameState.PLAYING);
    }

    // Checkerboard board: empty cells are isolated, so only a 1x1 piece fits.
    // Need a tray with exactly one 1x1 piece, so after placing it nothing else fits.
    static void testGameOver() {
        Game g = new Game();
        for (int attempt = 0; attempt < 5000; attempt++) {
            g.start(GameMode.CLASSIC);
            BlockPiece[] t = g.getTray();
            int singles = 0, slot = -1;
            for (int i = 0; i < t.length; i++)
                if (t[i].getCellCount() == 1) { singles++; slot = i; }
            if (singles != 1) continue;

            Board b = g.getBoard();
            for (int row = 0; row < Constants.BOARD_SIZE; row++)
                for (int col = 0; col < Constants.BOARD_SIZE; col++)
                    if ((row + col) % 2 == 0) b.place(ONE, row, col);

            PlaceResult res = g.tryPlace(slot, 0, 1);   // (0,1) is an empty cell
            check("single placed on checkerboard", res.isValid());
            check("no piece fits, so GAME_OVER", g.getState() == GameState.GAME_OVER);
            check("tryPlace rejected after game over", !g.tryPlace(0, 0, 0).isValid());
            return;
        }
        System.out.println("SKIP  could not build a tray with exactly one single piece");
    }
}