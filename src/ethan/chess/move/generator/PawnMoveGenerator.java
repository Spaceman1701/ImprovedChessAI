package ethan.chess.move.generator;

import ethan.chess.BitBoardUtil;
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
public class PawnMoveGenerator implements SetwiseMoveGenerator{
    private static int[] PAWN_MOVE_SHIFTS = {9, 7, 8, 16};

    private final BoardPosition bp;
    private final SidePosition sp;
    private final SidePosition op;
    private final Side side;
    private long pawns;
    private long opponentOccupied;
    private long fileA;
    private long fileH;
    private long rank8;
    private long rank2;
    private long notOccupied;
    private boolean reversed = false;

    private long moveBitboard;

    public PawnMoveGenerator(BoardPosition bp, Side side) {
        this.bp = bp;
        sp = bp.getSidePosition(side);
        op = bp.getOpponentSidePosition(side);
        this.side = side;
        pawns = sp.pawn;
        opponentOccupied = op.getOccupied();
        fileA = BoardPosition.FILE_A;
        fileH = BoardPosition.FILE_H;
        rank8 = BoardPosition.RANK_8;
        rank2 = BoardPosition.RANK_2;
        if (side == Side.BLACK) {
            pawns = Long.reverse(pawns);
            opponentOccupied = Long.reverse(opponentOccupied);
        }
        notOccupied = ~op.getOccupied();


        moveBitboard = generateMoveBitboard();
        if (side == Side.BLACK) {
            reversed = true;
            moveBitboard = Long.reverse(moveBitboard);
        }
    }

    private long generateMoveBitboard() {
        long rightAttack = (pawns << PAWN_MOVE_SHIFTS[0]) & opponentOccupied & (~fileA); //position of pawns shifted 9 where there are black pieces and not on file H
        long leftAttack = (pawns << PAWN_MOVE_SHIFTS[1]) & opponentOccupied & (-fileH);
        long forwardMove = (pawns << PAWN_MOVE_SHIFTS[2]) & (notOccupied) & (~rank8); //rank 8 would be a promotion TODO: promotions
        long doubleForwardMove = ((((pawns & rank2) << PAWN_MOVE_SHIFTS[2]) & notOccupied) << PAWN_MOVE_SHIFTS[2])
                & notOccupied; //yeah, this is confusing... it moves forward one, then two, to ensure there is an empty path
        //TODO: en passant

        return forwardMove | doubleForwardMove | leftAttack | rightAttack;
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
                for (int shift : PAWN_MOVE_SHIFTS) {
                    int startBit;
                    if (reversed) {
                        startBit = i + shift;
                    } else {
                        startBit = i - shift;
                    }
                    if (pawnAt(startBit) && moveLegal(shift, isCapture(i))) {
                        boolean capture = false;
                        boolean pawnStart = false;
                        if ((sp.getOccupied() & (1L << i)) != 0) {
                            capture = true;
                        }
                        if (((1L << i) & rank2) != 0) { //properly handles reversed bitboard
                            pawnStart = true;
                        }
                        moveList.add(new Move(side.isWhite(), startBit, i, capture, pawnStart, false));
                    }
                }
            }
        }
        return moveList;
    }
    private boolean moveLegal(int shift, boolean isCapture) {
        return (isCapture && shift == PAWN_MOVE_SHIFTS[0]) | (isCapture && shift == PAWN_MOVE_SHIFTS[1]) |
                (!isCapture && shift == PAWN_MOVE_SHIFTS[2]) | (!isCapture && shift == PAWN_MOVE_SHIFTS[3]);
    }
    private boolean isCapture(int shift) {
        return (sp.getOccupied() & (1L << shift)) != 0;
    }
    private int getStartBit(int i, int shift) {
        if (reversed) {
            return i + shift;
        }
        return i - shift;
    }

    private boolean pawnAt(int i) {
        long pawns = sp.pawn; //in case the class variable is reversed
        return (pawns & (1L << i)) != 0;
    }
}
