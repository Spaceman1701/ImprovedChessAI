package test.chess.move.generator;

import ethan.chess.BitBoardUtil;
import ethan.chess.BoardPosition;
import ethan.chess.Side;
import ethan.chess.move.generator.PawnMoveGenerator;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created by Ethan on 12/31/2016.
 */
public class PawnMoveGeneratorTest {
    @Test
    public void TestGetMoveBitboard() { //actually tests the constructor
        BoardPosition bp = BoardPosition.defaultInitialPosition();
        PawnMoveGenerator mg = new PawnMoveGenerator(bp, Side.WHITE);
        long bitboard = mg.getMoveBitboard();
        int[][] moves = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        assert BitBoardUtil.createBitBoardFromArray(moves) == bitboard : "bitboard = \n" + BitBoardUtil.bitboardString(bitboard);

    }

    @Test
    public void TestGenerateMovesWhite() {
        BoardPosition bp = BoardPosition.defaultInitialPosition();
        PawnMoveGenerator mg = new PawnMoveGenerator(bp, Side.WHITE);
        int size = mg.generateMoves().size();
        Assert.assertTrue("size = " + size, size == 16);
    }

    @Test
    public void TestGenerateMovesBlack() {
        BoardPosition bp = BoardPosition.defaultInitialPosition();
        PawnMoveGenerator mg = new PawnMoveGenerator(bp, Side.BLACK);
        assert mg.generateMoves().size() == 16;
    }
}
