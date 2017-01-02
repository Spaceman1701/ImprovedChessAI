package ethan.chess.move.generator;

import ethan.chess.BoardPosition;
import ethan.chess.SidePosition;
import ethan.chess.move.Move;
import ethan.chess.move.MoveGenerator;
import ethan.chess.Side;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 12/30/2016.
 */
public class KnightMoveGenerator implements MoveGenerator {
    private static final int[] KNIGHT_MOVE_SHIFTS = {15, 17, 6, 10}; //for both left and right shifts

    private SidePosition sidePosition;
    private long notSameSide;
    private long knights;
    private long occupied;
    private Side side;

    private long moveBitboard;

    private long[] moveBitboards;

    public KnightMoveGenerator(BoardPosition bp, Side side) {
        sidePosition = bp.getSidePosition(side);
        knights = sidePosition.knight;
        notSameSide = ~sidePosition.getOccupied();
        occupied = bp.getOccupied();
        this.side = side;
        moveBitboards = new long[4];

        moveBitboards[0] += (knights << KNIGHT_MOVE_SHIFTS[0]) & (~bp.FILE_H) & notSameSide; //15
        moveBitboards[0] += (knights >>> KNIGHT_MOVE_SHIFTS[0]) & (~bp.FILE_A) & notSameSide;

        moveBitboards[1] += (knights << KNIGHT_MOVE_SHIFTS[1]) & (~bp.FILE_A) & notSameSide; //17
        moveBitboards[1] += (knights >>> KNIGHT_MOVE_SHIFTS[1]) & (~bp.FILE_H) & notSameSide;

        moveBitboards[2] += (knights << KNIGHT_MOVE_SHIFTS[2]) & (~(bp.FILE_H | bp.FILE_G)) & notSameSide; //6
        moveBitboards[2] += (knights >>> KNIGHT_MOVE_SHIFTS[2]) & (~(bp.FILE_A | bp.FILE_B)) & notSameSide;

        moveBitboards[3] += (knights << KNIGHT_MOVE_SHIFTS[3]) & (~(bp.FILE_A | bp.FILE_B)) & notSameSide; //10
        moveBitboards[3] += (knights >>> KNIGHT_MOVE_SHIFTS[3]) & (~(bp.FILE_H | bp.FILE_G)) & notSameSide;

        moveBitboard = moveBitboards[0] | moveBitboards[1] | moveBitboards[2] | moveBitboards[3];
    }

    @Override
    public long getMoveBitboard() {
        return moveBitboard;
    }

    public long getAttackBitboard() {
        return moveBitboard;
    }

    @Override
    public List<Move> generateMoves() {
        List<Move> moveList = new ArrayList<>();
        for (int i = 0; i < BoardPosition.BOARD_SIZE; i++) {
            for (int j = 0; j < 4; j++) {
                long bb = moveBitboards[j];
                int shift = KNIGHT_MOVE_SHIFTS[j];
                if ((bb & (1L << i)) != 0) {
                    boolean capture = (occupied & (1L << i)) != 0;
                    int startOne = i - shift;
                    int startTwo = i + shift;
                    if ((knights & (1L << startOne)) != 0) {
                        moveList.add(new Move(side.isWhite(), startOne, i, capture, false, false));
                    }
                    if ((knights & (1L << startTwo)) != 0) {
                        moveList.add(new Move(side.isWhite(), startTwo, i, capture, false, false));
                    }
                }
            }
        }

        return moveList;
    }
}
