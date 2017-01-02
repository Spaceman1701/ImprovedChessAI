package test.chess.move.generator;

import ethan.chess.BitBoardUtil;
import ethan.chess.BoardPosition;
import ethan.chess.Side;
import ethan.chess.move.generator.KnightMoveGenerator;
import org.junit.Test;

/**
 * Created by Ethan on 1/1/2017.
 */
public class KnightMoveGeneratorTest {

    @Test
    public void getMoveBitboardTest() {
        BoardPosition bp = BoardPosition.defaultInitialPosition();
        KnightMoveGenerator mg = new KnightMoveGenerator(bp, Side.WHITE);
        long moves = mg.getMoveBitboard();
        int[][] expectedArray = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 1, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        System.out.println(BitBoardUtil.bitboardString(moves));
        long expected = BitBoardUtil.createBitBoardFromArray(expectedArray);
        assert moves == expected;
    }

    @Test
    public void generateMovesTest() {
        BoardPosition bp = BoardPosition.defaultInitialPosition();
        KnightMoveGenerator mg = new KnightMoveGenerator(bp, Side.WHITE);
        int size = mg.generateMoves().size();
        assert size == 4 : "size = " + size;
    }
}
