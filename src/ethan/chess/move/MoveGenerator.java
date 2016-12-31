package ethan.chess.move;

import ethan.chess.BoardPosition;
import ethan.chess.Side;

import java.util.List;

/**
 * Created by Ethan on 12/30/2016.
 */
public abstract class MoveGenerator {
    public List<Move> generateMoves(long moveBitboard, byte pieceSquare) {
        return null;
    }

    public abstract long generateMoveBitboard(BoardPosition bp, Side side, byte pieceSquare);
}
