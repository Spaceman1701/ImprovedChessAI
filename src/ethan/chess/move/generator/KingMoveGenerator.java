package ethan.chess.move.generator;

import ethan.chess.BoardPosition;
import ethan.chess.Side;
import ethan.chess.move.Move;
import ethan.chess.move.MoveGenerator;
import ethan.chess.move.SlidingMoveGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 12/30/2016.
 */
public class KingMoveGenerator extends SlidingMoveGenerator {
    private static final int[] SHIFTS = {8, 7, 9, 1};

    private long moveBitboard;
    private byte pieceSquare;

    public KingMoveGenerator(BoardPosition bp, Side side, byte pieceSquare, long attackedSquares) {
        super(bp, side);

        this.pieceSquare = pieceSquare;
        long bitboard = 1L << pieceSquare;
        long occupied = bp.getOccupied();
        long canMove = ~(occupied | attackedSquares);

        moveBitboard += (bitboard << 8) & canMove;
        moveBitboard += (bitboard << 7) & canMove & (~BoardPosition.FILE_H);
        moveBitboard += (bitboard << 9) & canMove & (~BoardPosition.FILE_A);
        moveBitboard += (bitboard << 1) & canMove & (~BoardPosition.FILE_A);

        moveBitboard += (bitboard >>> 8) & canMove;
        moveBitboard += (bitboard >>> 7) & canMove & (~BoardPosition.FILE_A);
        moveBitboard += (bitboard >>> 9) & canMove & (~BoardPosition.FILE_H);
        moveBitboard += (bitboard >>> 1) & canMove & (~BoardPosition.FILE_H);

        moveBitboard = MoveGenerator.removeIllegalMoves(bp, side, moveBitboard);

    }

    @Override
    public List<Move> generateMoves() {
        List<Move> moveList = new ArrayList<>();
        for (int shift : SHIFTS) {
            int locationOne = shift + pieceSquare;
            int locationTwo = pieceSquare - shift;
            if ((moveBitboard & (1L << locationOne)) != 0) {
                boolean capture = (bp.getOccupied() & (1L << locationOne)) != 0;
                moveList.add(new Move(side.isWhite(), (int)pieceSquare, locationOne, capture, false, false));
            } else if ((moveBitboard & (1L << locationTwo)) != 0) {
                boolean capture = (bp.getOccupied() & (1L << locationTwo)) != 0;
                moveList.add(new Move(side.isWhite(), (int)pieceSquare, locationTwo, capture, false, false));
            }
        }
        return moveList;
    }

    @Override
    public long getMoveBitboard() {
        return moveBitboard;
    }

    @Override
    public byte getPieceSquare() {
        return pieceSquare;
    }

    public long getAttackBitboard() {
        return moveBitboard;
    }
}
