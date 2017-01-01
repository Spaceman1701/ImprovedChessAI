package ethan.chess.move;

import ethan.chess.BoardPosition;
import ethan.chess.Side;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 12/30/2016.
 */
public abstract class SlidingMoveGenerator implements MoveGenerator {
    private final BoardPosition bp;
    private final Side side;

    protected SlidingMoveGenerator(BoardPosition bp, Side side) {
        this.bp = bp;
        this.side = side;
    }

    public List<Move> generateMoves() {
        long occupied = bp.getOccupied();
        List<Move> list = new ArrayList<>();
        for (int i = 0; i < BoardPosition.BOARD_SIZE; i++) {
            if ((getMoveBitboard() & (1L << i)) != 0) {
                list.add(new Move(side.isWhite(), getPieceSquare(), i, ((1L << i) & occupied) != 0, false, false));
            }
        }

        return list;
    }

    public abstract byte getPieceSquare();
}
