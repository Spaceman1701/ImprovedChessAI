package test.chess.move.generator;

import ethan.chess.BitBoardUtil;
import ethan.chess.BoardPosition;
import ethan.chess.Side;
import ethan.chess.move.generator.QueenMoveGenerator;
import org.junit.Test;

/**
 * Created by Ethan on 1/1/2017.
 */
public class QueenMoveGeneratorTest {
    @Test
    public void getMoveBitboardTest() {
        BoardPosition bp = BoardPosition.defaultInitialPosition();
        QueenMoveGenerator mg = new QueenMoveGenerator(bp, Side.WHITE, (byte)32);
        long moves = mg.getMoveBitboard();
        int[][] expected = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        long expectedBitboard = BitBoardUtil.createBitBoardFromArray(expected);
        assert moves == expectedBitboard;
    }

    @Test
    public void generateMovesTest() {
        BoardPosition bp = BoardPosition.defaultInitialPosition();
        QueenMoveGenerator mg = new QueenMoveGenerator(bp, Side.WHITE, (byte)32);
        int size = mg.generateMoves().size();
        assert size == 15 : "size = " + size;
    }
}
