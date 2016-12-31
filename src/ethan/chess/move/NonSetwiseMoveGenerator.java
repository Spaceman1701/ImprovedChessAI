package ethan.chess.move;

/**
 * Created by Ethan on 12/30/2016.
 */
public interface NonSetwiseMoveGenerator extends MoveGenerator{
    long generateMoveBitboard(long occupied, long sideOccupied, byte pieceSquare); //use a byte to reduce accidental calls
}
