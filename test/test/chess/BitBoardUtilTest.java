package test.chess;

import ethan.chess.BitBoardUtil;
import org.junit.Test;

/**
 * Created by Ethan on 12/31/2016.
 */
public class BitBoardUtilTest {

    @Test
    public void createBitBoardFromArrayTest() {
        int[][] array = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0}
        };

        long bitboard = BitBoardUtil.createBitBoardFromArray(array);
        assert bitboard == 0x1 : "value = " + bitboard;
    }
}
