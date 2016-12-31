package ethan.chess.move.generator;

import ethan.chess.move.Move;
import ethan.chess.move.NonSetwiseMoveGenerator;

import java.util.List;

/**
 * Created by Ethan on 12/30/2016.
 */
public class RookMoveGenerator implements NonSetwiseMoveGenerator {
    @Override
    public long generateMoveBitboard(long occupied, long sideOccupied, byte pieceSquare) {
        return 0;
    }

    @Override
    public List<Move> generateMoves(long moveBitboard) {
        return null;
    }
}
