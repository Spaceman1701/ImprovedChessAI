package ethan.chess.move.generator;

import ethan.chess.BoardPosition;
import ethan.chess.SidePosition;
import ethan.chess.move.Move;
import ethan.chess.move.SetwiseMoveGenerator;
import ethan.chess.Side;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 12/30/2016.
 */
public class KnightMoveGenerator implements SetwiseMoveGenerator {
    private static final int[] KNIGHT_MOVE_SHIFTS = {15, 17, 6, 10}; //for both left and right shifts

    private SidePosition sidePosition;
    private long notSameSide;
    private long knights;
    private long occupied;
    private Side side;

    private long moveBitboard;

    public KnightMoveGenerator(BoardPosition bp, Side side) {
        sidePosition = bp.getSidePosition(side);
        knights = sidePosition.knight;
        notSameSide = ~sidePosition.getOccupied();
        occupied = bp.getOccupied();
        this.side = side;

        moveBitboard += (knights << KNIGHT_MOVE_SHIFTS[0]) & (~bp.FILE_A) & notSameSide; //15
        moveBitboard += (knights >>> KNIGHT_MOVE_SHIFTS[0]) & (~bp.FILE_H) & notSameSide;

        moveBitboard += (knights << KNIGHT_MOVE_SHIFTS[1]) & (~bp.FILE_H) & notSameSide; //17
        moveBitboard += (knights >>> KNIGHT_MOVE_SHIFTS[1]) & (~bp.FILE_A) & notSameSide;

        moveBitboard += (knights << KNIGHT_MOVE_SHIFTS[2]) & (~(bp.FILE_H | bp.FILE_G)) & notSameSide; //6
        moveBitboard += (knights >>> KNIGHT_MOVE_SHIFTS[2]) & (~(bp.FILE_A | bp.FILE_B)) & notSameSide;

        moveBitboard += (knights << KNIGHT_MOVE_SHIFTS[3]) & (~(bp.FILE_A | bp.FILE_B)) & notSameSide; //10
        moveBitboard += (knights >>> KNIGHT_MOVE_SHIFTS[3]) & (~(bp.FILE_H | bp.FILE_G)) & notSameSide;
    }

    @Override
    public long getMoveBitboard() {
        return moveBitboard;
    }

    @Override
    public List<Move> generateMoves() {
        List<Move> moveList = new ArrayList<>();
        for (int i = 0; i < BoardPosition.BOARD_SIZE; i++) {
            if ((moveBitboard & (1L << i)) != 0) {
                for (int shift : KNIGHT_MOVE_SHIFTS) {
                    boolean capture = false;
                    if ((occupied & (1L << (i + shift))) != 0) {
                        capture = true;
                    }
                    if ((knights & (1L << (i + shift))) != 0) {
                        moveList.add(new Move(side.isWhite(), i + shift, i, capture, false, false));
                    }
                    if ((knights & (1L << (i - shift))) != 0) {
                        moveList.add(new Move(side.isWhite(), i - shift, i, capture, false, false));
                    }
                }
            }
        }

        return null;
    }
}
