package ethan.chess.move.generator;

import ethan.chess.BoardPosition;
import ethan.chess.move.SlidingMoveGenerator;
import ethan.chess.Side;

/**
 * Created by Ethan on 12/30/2016.
 */
public class RookMoveGenerator extends SlidingMoveGenerator {


    private byte pieceSquare;
    private long moves;

    public RookMoveGenerator(BoardPosition bp, Side side, byte piecePosition) {
        super(bp, side);
        long sideOccupied = bp.getSidePosition(side).getOccupied();
        long occupied = bp.getOccupied();
        long bitboard = (1L << piecePosition); //maybe use lookup table
        long reverseBitboard = Long.reverse(bitboard);
        int rank = piecePosition / bp.BOARD_DIM;
        int file = piecePosition % bp.BOARD_DIM;

        long verticalMask = bp.FILES[file] & occupied;
        long reverseVertMask = Long.reverse(verticalMask);

        long horizontalMask = bp.RANKS[rank] & occupied;
        long reverseHorizMask = Long.reverse(horizontalMask);

        long vertMoves = (verticalMask ^ (verticalMask - 2 * bitboard)) |
                Long.reverse(reverseVertMask ^ (reverseVertMask - 2 * reverseBitboard));

        long horizMoves = (horizontalMask ^ (horizontalMask - 2 * bitboard)) |
                Long.reverse(reverseHorizMask ^ (reverseHorizMask - 2 * reverseBitboard));

        moves = ((vertMoves & bp.FILES[file]) | (horizMoves & bp.RANKS[rank])) & (~sideOccupied);
    }

    @Override
    public long getMoveBitboard() {
        return moves;
    }

    public byte getPieceSquare() {
        return pieceSquare;
    }

    public long getAttackBitboard() {
        return moves;
    }
}
