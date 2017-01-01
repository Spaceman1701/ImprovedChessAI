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
    private static final String[][] PAWN_MOVE_TEST = {
            {"br", "bn", "bb", "bq", "bk", "bb", "bn", "br"},
            {"bp", "bp", "bp", "bp", "bp", "bp", "bp", "bp"},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"  ", "  ", "  ", "wp", "  ", "  ", "  ", "  "},
            {"wp", "wp", "wp", "wp", "wp", "wp", "wp", "wp"},
            {"wr", "wn", "wb", "wq", "wk", "wb", "wn", "wr"}
    };
    @Test
    public void TestGetMoveBitboardWhite() { //actually tests the constructor
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

        assert BitBoardUtil.createBitBoardFromArray(moves) == bitboard : "bitboard = \n" +
                BitBoardUtil.bitboardString(bitboard);

    }

    @Test
    public void TestGetMoveBitboardBlack() { //actually tests the constructor
        BoardPosition bp = BoardPosition.defaultInitialPosition();
        PawnMoveGenerator mg = new PawnMoveGenerator(bp, Side.BLACK);
        long bitboard = mg.getMoveBitboard();
        int[][] moves = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        assert BitBoardUtil.createBitBoardFromArray(moves) == bitboard : "bitboard = \n" +
                BitBoardUtil.bitboardString(bitboard);

    }

    @Test
    public void TestGenerateMovesIrregular() {
        BoardPosition bp = BoardPosition.fromArray(PAWN_MOVE_TEST);
        PawnMoveGenerator mg = new PawnMoveGenerator(bp, Side.WHITE);
        int size = mg.generateMoves().size();
        assert size == 15 : "size = " + size;
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
        int size = mg.generateMoves().size();
        assert size == 16 : "size = " + size;
    }
}
