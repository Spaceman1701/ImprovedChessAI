package ethan.chess;

/**
 * Created by Ethan on 12/21/2016.
 */
public class BitBoardUtil {
    private BitBoardUtil() {}
    /**
     *
     * @param array bitboard in little endian format
     * @return
     */
    public static long createBitBoardFromArray(int[][] array) {
        if(array.length != array[0].length) {
            throw new RuntimeException("illegal argument - bitboards must be square");
        }
        if(array.length > 64) {
            throw new RuntimeException("illegal argument - bitboards have a max size of 64");
        }
        long board = 0L;
        for(int i = 0; i < array.length * array.length; i++) {
            int value = array[i / array.length][i % array.length];
            if(value > 1 || value < 0) {
                throw new RuntimeException("illegal argument - bitboards must be made of 1s and 0s");
            }
            board += 1L << i;
        }

        return board;
    }
}
