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
public class PawnMoveGenerator implements MoveGenerator {
    private static final int RIGHT_MOVE = 9;
    private static final int LEFT_MOVE = 7;
    private static final int FORWARD_MOVE = 8;
    private static final int DOUBLE_FORWARD_MOVE = 16;

    private final BoardPosition bp;
    private final SidePosition sp;
    private final SidePosition op;
    private final Side side;
    private long pawns;
    private long opponentOccupied;

    private long rank2;
    private long occupied;
    private long notOccupied;
    private boolean reversed = false;

    private long rightAttack;
    private long leftAttack;
    private long forwardMove;
    private long doubleForwardMove;

    public PawnMoveGenerator(BoardPosition bp, Side side) {
        this.bp = bp;
        sp = bp.getSidePosition(side);
        op = bp.getOpponentSidePosition(side);
        this.side = side;
        pawns = sp.pawn;
        occupied = bp.getOccupied();
        opponentOccupied = op.getOccupied();
        rank2 = BoardPosition.RANK_2;

        if (side == Side.BLACK) {
            pawns = Long.reverse(pawns);
            opponentOccupied = Long.reverse(opponentOccupied);
            rank2 = Long.reverse(rank2);
            occupied = Long.reverse(occupied);
        }
        notOccupied = ~occupied;


        generateMoveBitboard();
        if (side == Side.BLACK) {
            reversed = true;
            rightAttack = Long.reverse(rightAttack);
            leftAttack = Long.reverse(leftAttack);
            forwardMove = Long.reverse(forwardMove);
            doubleForwardMove = Long.reverse(doubleForwardMove);
        }
    }

    private void generateMoveBitboard() {
        rightAttack = (pawns << RIGHT_MOVE) & opponentOccupied & (~BoardPosition.FILE_A); //position of pawns shifted 9 where there are black pieces and not on file H
        leftAttack = (pawns << LEFT_MOVE) & opponentOccupied & (~BoardPosition.FILE_H);
        forwardMove = (pawns << FORWARD_MOVE) & (notOccupied) & (~BoardPosition.RANK_8); //rank 8 would be a promotion TODO: promotions
        doubleForwardMove = ((((pawns & BoardPosition.RANK_2) << FORWARD_MOVE) & notOccupied) << FORWARD_MOVE);
    }

    @Override
    public long getMoveBitboard() {
        return forwardMove | doubleForwardMove;
    }

    @Override
    public long getAttackBitboard() {
        return rightAttack | leftAttack;
    }

    @Override
    public List<Move> generateMoves() {
        long pawns = sp.pawn;
        List<Move> moveList = new ArrayList<>();
        for (int i = 0; i < BoardPosition.BOARD_SIZE; i++) {
            if ((rightAttack & (1L << i)) != 0) {
                int start = getStartBit(i, RIGHT_MOVE);
                if ((pawns & (1L << start)) != 0) {
                    moveList.add(new Move(side.isWhite(), start, i, true, false, false));
                }
            }
            if ((leftAttack & (1L << i)) != 0) {
                int start = getStartBit(i, LEFT_MOVE);
                if ((pawns & (1L << start)) != 0) {
                    moveList.add(new Move(side.isWhite(), start, i, true, false, false));
                }
            }
            if ((forwardMove & (1L << i)) != 0) {
                int start = getStartBit(i, FORWARD_MOVE);
                if ((pawns & (1L << start)) != 0) {
                    moveList.add(new Move(side.isWhite(), start, i, false, false, false));
                }
            }
            if ((doubleForwardMove & (1L << i)) != 0) {
                int start = getStartBit(i, DOUBLE_FORWARD_MOVE);
                if ((pawns & (1L << start)) != 0) {
                    moveList.add(new Move(side.isWhite(), start, i, true, false, false));
                }
            }
        }
        return moveList;
    }

    private int getStartBit(int i, int shift) {
        if (reversed) {
            return i + shift;
        }
        return i - shift;
    }
}
