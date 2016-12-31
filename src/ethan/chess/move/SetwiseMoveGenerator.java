package ethan.chess.move;

/**
 * Created by Ethan on 12/30/2016.
 */
public interface SetwiseMoveGenerator extends MoveGenerator{
    long generateMoveBitboard(long occupied, long sideOccupied, long pieceBitboard, byte opponentKingSquare);
}
