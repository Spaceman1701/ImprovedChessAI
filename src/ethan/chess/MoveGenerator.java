package ethan.chess;

/**
 * Created by Ethan on 12/21/2016.
 */
public class MoveGenerator {

    public static int createMove(int start, int end, boolean capture, boolean pawnStart, boolean pawnPromote) {
        int move = 0;
        move += start << 24;
        move += end << 16;
        if(capture) {
            move += 1 << 15;
        }
        if(pawnStart) {
            move += 1 << 14;
        }
        if(pawnPromote) {
            move += 1 << 13;
        }
        return move;
    }

    public static boolean isCapture(int move) {
        return (move & (1 << 15)) != 0;
    }

    public static boolean isPawnStart(int move) {
        return (move & (1 << 14)) != 0;
    }

    public static boolean isPawnPromote(int move) {
        return (move & (1 << 13)) != 0;
    }

    public static int getStartPosition(int move) {
        return (move >>> 24) & 0xFF;
    }

    public static int getEndPosition(int move) {
        return (move >>> 16) & 0xFF;
    }
}
