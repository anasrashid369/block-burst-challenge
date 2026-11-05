import com.blockburst.model.*;

public class GeneratorTest {
    public static void main(String[] args) {
        PieceGenerator g = new PieceGenerator();
        for (BlockPiece p : g.generateTray()) {
            for (int[] row : p.getCells()) {
                StringBuilder sb = new StringBuilder();
                for (int v : row) sb.append(v == 1 ? "# " : ". ");
                System.out.println(sb);
            }
            System.out.println();
        }
    }
}