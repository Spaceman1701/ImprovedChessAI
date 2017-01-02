package ethan.chess;

/**
 * Created by Ethan on 12/20/2016.
 */
public final class SidePosition {
    public static final int PAWN_VALUE = 100;
    public static final int ROOK_VALUE = 500;
    public static final int BISHOP_VALUE = 300;
    public static final int KNIGHT_VALUE = 300;
    public static final int QUEEN_VALUE = 900;


    public final Side side;

    public long pawn;
    public long rook;
    public long knight;
    public long bishop;
    public long queen;
    public long king;

    public SidePosition(Side side) {
        this.side = side;
    }

    public SidePosition(SidePosition other) {
        this.side = other.side;
        this.pawn = other.pawn;
        this.rook = other.rook;
        this.knight = other.knight;
        this.bishop = other.bishop;
        this.queen = other.queen;
        this.king = other.king;
    }

    public long getOccupied() {
        return pawn | rook | knight | bishop | queen | king;
    }

    public long getNotOccupied() {
        return ~getOccupied();
    }

    public int getMaterialValue() {
        return Long.bitCount(pawn) * PAWN_VALUE + Long.bitCount(rook) * ROOK_VALUE +
                Long.bitCount(bishop) * BISHOP_VALUE + Long.bitCount(knight) * KNIGHT_VALUE +
                Long.bitCount(queen) * QUEEN_VALUE;
    }
}
