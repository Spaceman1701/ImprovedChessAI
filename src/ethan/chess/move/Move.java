package ethan.chess.move;

/**
 * Created by Ethan on 12/21/2016.
 */
public class Move {
    private static int SIDE_BIT = 0;
    private static int START_BIT = 1;
    private static int END_BIT = 9;
    private static int PAWN_START_BIT = 17;
    private static int PAWN_PROMOTE = 18;
    private static int CAPTURE = 19;

    public static int createMove(boolean side, int start, int end, boolean capture, boolean pawnStart, boolean pawnPromote) {
        int move = 0;
        move += start << START_BIT;
        move += end << END_BIT;
        if(capture) {
            move += 1 << CAPTURE;
        }
        if(pawnStart) {
            move += 1 << PAWN_START_BIT;
        }
        if(pawnPromote) {
            move += 1 << PAWN_PROMOTE;
        }
        if (side) {
            move += 1 << SIDE_BIT;
        }
        return move;
    }

    public static boolean isCapture(int move) {
        return (move & (1 << CAPTURE)) != 0;
    }

    public static boolean isPawnStart(int move) {
        return (move & (1 << PAWN_START_BIT)) != 0;
    }

    public static boolean isPawnPromote(int move) {
        return (move & (1 << PAWN_PROMOTE)) != 0;
    }

    public static int getStartPosition(int move) {
        return (move >>> START_BIT) & 0xFF;
    }

    public static int getEndPosition(int move) {
        return (move >>> END_BIT) & 0xFF;
    }

    public static boolean getSide(int move) {
        return (move & 0x1) != 0; //if change side bit change this
    }
}
