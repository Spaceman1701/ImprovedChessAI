package ethan.chess;

/**
 * Created by Ethan on 12/28/2016.
 */
public class Piece {
    private final PieceType type;
    private int position;

    public Piece(PieceType type, int position) {
        this.type = type;
        this.position = position;
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

}
