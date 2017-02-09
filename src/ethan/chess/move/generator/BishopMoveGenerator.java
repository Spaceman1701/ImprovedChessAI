package ethan.chess.move.generator;

import ethan.chess.BitBoardUtil;
import ethan.chess.BoardPosition;
import ethan.chess.move.MoveGenerator;
import ethan.chess.move.SlidingMoveGenerator;
import ethan.chess.Side;

/**
 * Created by Ethan on 12/30/2016.
 */
public class BishopMoveGenerator extends SlidingMoveGenerator {


    private byte pieceSquare;
    private Side side;

    private long moveBitboard;

    public BishopMoveGenerator(BoardPosition bp, Side side, byte pieceSquare) {
        super(bp, side);
        long sideOccupied = bp.getSidePosition(side).getOccupied();
        long occupied = bp.getOccupied();
        long bitboard = (1L << pieceSquare);
        long reverseBitboard = Long.reverse(bitboard);
        int rank = pieceSquare / bp.BOARD_DIM;
        int file = pieceSquare % bp.BOARD_DIM;

        int diag = 7 + rank - file;
        int antiDiag = rank + file;

        long diagMask = bp.DIAGONALS[diag] & occupied;
        long antiDiagMask = bp.ANTI_DIAGONALS[antiDiag] & occupied;

        long reverseDiagMask = Long.reverse(diagMask);
        long reverseAntiDiagMask = Long.reverse(antiDiagMask);

        long diagMoves = (diagMask ^ (diagMask - 2 * bitboard)) |
                Long.reverse(reverseDiagMask ^ (reverseDiagMask - 2 * reverseBitboard));
        long antiDiagMoves = (antiDiagMask ^ (antiDiagMask - 2 * bitboard)) |
                Long.reverse(reverseAntiDiagMask ^ (reverseAntiDiagMask - 2 * reverseBitboard));

        moveBitboard = ((diagMoves & bp.DIAGONALS[diag]) | (antiDiagMoves & bp.ANTI_DIAGONALS[antiDiag])) & (~sideOccupied);
        moveBitboard = MoveGenerator.removeIllegalMoves(bp, side, moveBitboard);
    }

    @Override
    public long getMoveBitboard() {
        return moveBitboard;
    }

    public long getAttackBitboard() {
        return moveBitboard;
    }

    @Override
    public byte getPieceSquare() {
        return pieceSquare;
    }
}
