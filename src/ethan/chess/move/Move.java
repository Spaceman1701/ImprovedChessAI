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


    private int move;

    public Move(boolean side, int start, int end, boolean capture, boolean pawnStart, boolean pawnPromote) {
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
    }

    public Move(int move) {
        this.move = move;
    }

    public int getMove() {
        return move;
    }

    public boolean isCapture() {
        return (move & (1 << CAPTURE)) != 0;
    }

    public boolean isPawnStart() {
        return (move & (1 << PAWN_START_BIT)) != 0;
    }

    public boolean isPawnPromote() {
        return (move & (1 << PAWN_PROMOTE)) != 0;
    }

    public int getStartPosition() {
        return (move >>> START_BIT) & 0xFF;
    }

    public int getEndPosition() {
        return (move >>> END_BIT) & 0xFF;
    }

    public boolean getSide() {
        return (move & 0x1) != 0; //if change side bit change this
    }
}
