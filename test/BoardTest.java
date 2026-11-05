import com.blockburst.model.*;
import java.awt.Color;

public class BoardTest {
    static BlockPiece one = new BlockPiece(new int[][]{{1}}, Color.RED);

    public static void main(String[] args) {
        testNoClear();
        testSingleRow();
        testRowAndColumn();
        testRejectOverlapAndEdge();
    }

    static void testNoClear() {
        Board b = new Board();
        b.place(one, 0, 0);
        ClearResult r = b.clearCompleted();
        System.out.println("no clear      -> lines=" + r.getLineCount() + " (expect 0)");
    }

    static void testSingleRow() {
        Board b = new Board();
        for (int c = 0; c < 8; c++) b.place(one, 3, c);
        ClearResult r = b.clearCompleted();
        System.out.println("single row    -> rows=" + r.getRowsCleared()
                + " cols=" + r.getColsCleared()
                + " cells=" + r.getClearedCells().size() + " (expect 1, 0, 8)");
        System.out.println("row is empty  -> " + (b.getCell(3, 0) == null) + " (expect true)");
    }

    static void testRowAndColumn() {
        Board b = new Board();
        for (int i = 1; i < 8; i++) { b.place(one, 0, i); b.place(one, i, 0); }
        b.place(one, 0, 0);   // completes row 0 and col 0 together
        ClearResult r = b.clearCompleted();
        System.out.println("row + column -> rows=" + r.getRowsCleared()
                + " cols=" + r.getColsCleared()
                + " cells=" + r.getClearedCells().size() + " (expect 1, 1, 15)");
    }

    static void testRejectOverlapAndEdge() {
        Board b = new Board();
        b.place(one, 2, 2);
        System.out.println("overlap       -> " + b.canPlace(one, 2, 2) + " (expect false)");
        System.out.println("out of bounds -> " + b.canPlace(one, 8, 0) + " (expect false)");
        System.out.println("last cell     -> " + b.canPlace(one, 7, 7) + " (expect true)");
    }
}