package test.chess;

import ethan.chess.BoardPosition;
import ethan.chess.Piece;
import ethan.chess.PieceList;
import ethan.chess.Side;
import ethan.chess.move.Move;
import ethan.chess.move.generator.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ethan on 1/2/2017.
 */
public class Perft {
    public static List<Move> generateAllMoves(BoardPosition bp, Side side) {
        long opponentKing = bp.getOpponentSidePosition(side).king;
        PawnMoveGenerator pmg = new PawnMoveGenerator(bp, side);
        KnightMoveGenerator nmg = new KnightMoveGenerator(bp, side);

        PieceList rooks = bp.getRook(side);
        PieceList bishops = bp.getBishops(side);
        PieceList kings = bp.getKing(side);
        PieceList queens = bp.getQueen(side);

        RookMoveGenerator[] rmgs = new RookMoveGenerator[rooks.size()];
        BishopMoveGenerator[] bmgs = new BishopMoveGenerator[bishops.size()];
        QueenMoveGenerator[] qmgs = new QueenMoveGenerator[queens.size()];

        long attacked = pmg.getAttackBitboard();
        attacked |= nmg.getAttackBitboard();

        List<Move> moves = new LinkedList<Move>();
        moves.addAll(pmg.generateMoves());
        moves.addAll(nmg.generateMoves());

        for (int i = 0; i < rmgs.length; i++) {
            rmgs[i] = new RookMoveGenerator(bp, side, (byte)rooks.get(i).getPosition());
            attacked |= rmgs[i].getAttackBitboard();
            moves.addAll(rmgs[i].generateMoves());
        }
        for (int i = 0; i < bmgs.length; i++) {
            bmgs[i] = new BishopMoveGenerator(bp, side, (byte)bishops.get(i).getPosition());
            attacked |= bmgs[i].getAttackBitboard();
            moves.addAll(bmgs[i].generateMoves());
        }
        for (int i = 0; i < qmgs.length; i++) {
            qmgs[i] = new QueenMoveGenerator(bp, side, (byte)queens.get(i).getPosition());
            attacked |= qmgs[i].getAttackBitboard();
            moves.addAll(qmgs[i].generateMoves());
        }

        KingMoveGenerator kmg = new KingMoveGenerator(bp, side, (byte)kings.get(0).getPosition(), attacked);
        moves.addAll(kmg.generateMoves());


        return moves;
    }

    public static long perft(int depth, BoardPosition bp, Side side) {
        long total = 0;
        List<Move> moves = generateAllMoves(bp, side);

        if (depth == 1) {
            return moves.size();
        }
        Side other;
        if (side == Side.WHITE) {
            other = Side.BLACK;
        } else {
            other = Side.WHITE;
        }

        for (Move m : moves) {
            BoardPosition moved = BoardPosition.fromMove(bp, m);
            total += perft(depth - 1, moved, other);
        }

        return total;
    }

    public static void main(String[] args) {
        int depth = 5;
        long start = System.nanoTime();
        System.out.println("perft " + depth + ": ");
        System.out.println(perft(depth, BoardPosition.defaultInitialPosition(), Side.WHITE));
        long delta = Math.abs(System.nanoTime() - start);
        System.out.println(delta / (1e9) + " seconds");
    }
}
