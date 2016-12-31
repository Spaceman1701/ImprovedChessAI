package ethan.chess.move.generator;

import ethan.chess.BoardPosition;
import ethan.chess.move.Move;
import ethan.chess.move.MoveGenerator;
import ethan.chess.Side;

import java.util.List;

/**
 * Created by Ethan on 12/30/2016.
 */
public class QueenMoveGenerator extends MoveGenerator {
    @Override
    public long generateMoveBitboard(BoardPosition bp, Side side, byte pieceSquare) {
        return 0;
    }
}
