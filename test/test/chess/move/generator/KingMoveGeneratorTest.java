package test.chess.move.generator;

import ethan.chess.BitBoardUtil;
import ethan.chess.BoardPosition;
import ethan.chess.Side;
import ethan.chess.move.generator.KingMoveGenerator;
import ethan.chess.move.generator.PawnMoveGenerator;
import org.junit.Test;

/**
 * Created by Ethan on 1/2/2017.
 */
public class KingMoveGeneratorTest {

    public static final String[][] TEST_POSITION = {
            {"br", "bn", "bb", "bq", "bk", "bb", "bn", "br"},
            {"bp", "bp", "bp", "bp", "bp", "bp", "bp", "bp"},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"wp", "wp", "wp", "wp", "wp", "wp", "wp", "wp"},
            {"wr", "wn", "wb", "wq", "wk", "wb", "wn", "wr"}
    };
    @Test
    public void getMoveBitboardTest() {
        BoardPosition bp = BoardPosition.defaultInitialPosition();
        PawnMoveGenerator pmg = new PawnMoveGenerator(bp, Side.BLACK);
        long attack = pmg.getAttackBitboard();
        KingMoveGenerator mg = new KingMoveGenerator(bp, Side.WHITE, (byte)34, attack);
        long moves = mg.getMoveBitboard();
        int[][] expectedArray = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        long expected = BitBoardUtil.createBitBoardFromArray(expectedArray);
        assert moves == expected;
    }
}
