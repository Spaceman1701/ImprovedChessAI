package ethan.chess.move.generator;

import ethan.chess.move.Move;
import ethan.chess.move.SetwiseMoveGenerator;

import java.util.List;

/**
 * Created by Ethan on 12/30/2016.
 */
public class PawnMoveGenerator implements SetwiseMoveGenerator {
    @Override
    public long generateMoveBitboard(long occupied, long sideOccupied, long pieceBitboard) {
        return 0;
    }

    @Override
    public List<Move> generateMoves(long moveBitboard) {
        return null;
    }
}
