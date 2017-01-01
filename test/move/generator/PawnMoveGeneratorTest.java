package move.generator;

import ethan.chess.BoardPosition;
import ethan.chess.Side;
import ethan.chess.move.generator.PawnMoveGenerator;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Ethan on 12/31/2016.
 */
public class PawnMoveGeneratorTest {
    @Test
    public void TestGetMoveBitboard() { //actually tests the constructor
        BoardPosition bp = BoardPosition.defaultInitialPosition();
        PawnMoveGenerator mg = new PawnMoveGenerator(bp, Side.WHITE);
        long bitboard = mg.getMoveBitboard();


    }

    @Test
    public void TestGenerateMovesWhite() {
        BoardPosition bp = BoardPosition.defaultInitialPosition();
        PawnMoveGenerator mg = new PawnMoveGenerator(bp, Side.WHITE);
        int size = mg.generateMoves().size();
        assertTrue("size = " + size, size == 16);
    }

    @Test
    public void TestGenerateMovesBlack() {
        BoardPosition bp = BoardPosition.defaultInitialPosition();
        PawnMoveGenerator mg = new PawnMoveGenerator(bp, Side.BLACK);
        assert mg.generateMoves().size() == 16;
    }
}
