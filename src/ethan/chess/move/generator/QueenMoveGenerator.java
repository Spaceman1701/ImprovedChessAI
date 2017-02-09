package ethan.chess.move.generator;

import ethan.chess.BoardPosition;
import ethan.chess.move.MoveGenerator;
import ethan.chess.move.SlidingMoveGenerator;
import ethan.chess.Side;

/**
 * Created by Ethan on 12/30/2016.
 */
public class QueenMoveGenerator extends SlidingMoveGenerator {

    private byte pieceSqure;
    private long moveBitboard;

    public QueenMoveGenerator(BoardPosition bp, Side side, byte pieceSquare) {
        super(bp, side);
        this.pieceSqure = pieceSquare;
        moveBitboard = new RookMoveGenerator(bp, side, pieceSquare).getMoveBitboard() |
                new BishopMoveGenerator(bp, side, pieceSquare).getMoveBitboard();
        moveBitboard = MoveGenerator.removeIllegalMoves(bp, side, moveBitboard);

    }

    @Override
    public long getMoveBitboard() {
        return moveBitboard;
    }

    @Override
    public byte getPieceSquare() {
        return pieceSqure;
    }

    public long getAttackBitboard() {
        return moveBitboard;
    }
}
