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
        long board = 0L;
        for(int i = 0; i < 64 ; i++) {
            int value = array[i / array.length][i % array.length];
            if(value > 1 || value < 0) {
                throw new RuntimeException("illegal argument - bitboards must be made of 1s and 0s");
            }
            if (value == 1) {
                board += 1L << i;
            }
        }

        return Long.reverseBytes(board);
    }

    public static String bitboardString(long bitboard) {
        StringBuilder sb = new StringBuilder();
        long output = Long.reverseBytes(bitboard);
        for(int i = 0; i < 64; i++) {
            if((output & (1L << i)) != 0) {
                sb.append("X ");
            } else {
                sb.append("0 ");
            }
            if(i % 8 == 7) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }

}
