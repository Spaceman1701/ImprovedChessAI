package ethan.chess;

/**
 * Created by Ethan on 12/28/2016.
 */
public class Piece {
    private final PieceType type;
    private int position;
    private Side side;

    public Piece(PieceType type, Side side, int position) {
        this.type = type;
        this.position = position;
        this.side = side;
    }

    public Piece(Piece other) {
        this.type = other.type;
        this.position = other.position;
        this.side = other.side;
    }

    public int getPosition() {
        return position;
    }

    public PieceType getType() {
        return type;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Side getSide() {
        return side;
    }

}
