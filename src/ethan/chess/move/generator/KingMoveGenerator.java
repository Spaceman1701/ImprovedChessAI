package ethan.chess.move.generator;

import ethan.chess.BoardPosition;
import ethan.chess.move.Move;
import ethan.chess.move.MoveGenerator;
import javafx.geometry.Side;

import java.util.List;

/**
 * Created by Ethan on 12/30/2016.
 */
public class KingMoveGenerator implements MoveGenerator {
    @Override
    public long generateMoveBitboard(BoardPosition bp, Side side) {
        return 0;
    }

    @Override
    public List<Move> generateMoves(long moveBitboard) {
        return null;
    }
}
