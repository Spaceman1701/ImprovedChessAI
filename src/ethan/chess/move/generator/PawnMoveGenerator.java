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
public class PawnMoveGenerator implements SetwiseMoveGenerator {
    @Override
    public long generateMoveBitboard(BoardPosition bp, Side side) {
        SidePosition sp = bp.getSidePosition(side);
        SidePosition op = bp.getOpponentSidePosition(side);
        long pawns = sp.pawn;
        long opponentOccupied = op.getOccupied();
        long fileA = bp.FILE_A;
        long fileH = bp.FILE_H;
        long rank8 = bp.RANK_8;
        long rank2 = bp.RANK_2;

        if (side == Side.BLACK) {
            pawns = Long.reverse(pawns);
            opponentOccupied = Long.reverse(opponentOccupied);
            fileA = Long.reverse(fileA);
            fileH = Long.reverse(fileH);
            rank8 = Long.reverse(rank8);
            rank2 = Long.reverse(rank2);
        }


        long notOccupied = ~op.getOccupied();


        long rightAttack = (pawns << 9) & opponentOccupied & (~fileA); //position of pawns shifted 9 where there are black pieces and not on file H
        long leftAttack = (pawns << 7) & opponentOccupied & (-fileH);
        long forwardMove = (pawns << 8) & (notOccupied) & (~rank8); //rank 8 would be a promotion TODO: promotions
        long doubleForwardMove = ((((pawns & rank2) << 8) & notOccupied) << 8) & notOccupied; //yeah, this is confusing... it moves forward one, then two, to ensure there is an empty path
        //TODO: en passant

        long result = rightAttack | leftAttack | forwardMove | doubleForwardMove;
        if (side == Side.BLACK) {
            result = Long.reverse(result);
        }
        return result;
    }

    @Override
    public List<Move> generateMoves(long moveBitboard) {
        return null;
    }
}
