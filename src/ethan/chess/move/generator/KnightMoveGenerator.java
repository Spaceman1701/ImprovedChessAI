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

    @Override
    public long generateMoveBitboard(BoardPosition bp, Side side) {
        SidePosition sidePosition = bp.getSidePosition(side);
        long moves = 0L;
        long knights = sidePosition.knight;
        long notSameSide = ~sidePosition.getOccupied();
        long occupied = bp.getOccupied();

        moves += (knights << KNIGHT_MOVE_SHIFTS[0]) & (~bp.FILE_A) & notSameSide; //15
        moves += (knights >>> KNIGHT_MOVE_SHIFTS[0]) & (~bp.FILE_H) & notSameSide;

        moves += (knights << KNIGHT_MOVE_SHIFTS[1]) & (~bp.FILE_H) & notSameSide; //17
        moves += (knights >>> KNIGHT_MOVE_SHIFTS[1]) & (~bp.FILE_A) & notSameSide;

        moves += (knights << KNIGHT_MOVE_SHIFTS[2]) & (~(bp.FILE_H | bp.FILE_G)) & notSameSide; //6
        moves += (knights >>> KNIGHT_MOVE_SHIFTS[2]) & (~(bp.FILE_A | bp.FILE_B)) & notSameSide;

        moves += (knights << KNIGHT_MOVE_SHIFTS[3]) & (~(bp.FILE_A | bp.FILE_B)) & notSameSide; //10
        moves += (knights >>> KNIGHT_MOVE_SHIFTS[3]) & (~(bp.FILE_H | bp.FILE_G)) & notSameSide;

        return moves;
    }

    @Override
    public List<Move> generateMoves(long moveBitboard, BoardPosition bp, Side side) {
        long occupied = bp.getOccupied();
        long knights = bp.getSidePosition(side).knight;
        List<Integer> moveList = new ArrayList<>();
        for (int i = 0; i < BoardPosition.BOARD_SIZE; i++) {
            if ((moveBitboard & (1L << i)) != 0) {
                for (int shift : KNIGHT_MOVE_SHIFTS) {
                    boolean capture = false;
                    if ((occupied & (1L << (i + shift))) != 0) {
                        capture = true;
                    }
                    if ((knights & (1L << (i + shift))) != 0) {
                        moveList.add(Move.createMove(side.isWhite(), i + shift, i, capture, false, false)); //TODO: fine captures
                    }
                    if ((knights & (1L << (i - shift))) != 0) {
                        moveList.add(Move.createMove(side.isWhite(), i - shift, i, capture, false, false));
                    }
                }
            }
        }

        return null;
    }
}
