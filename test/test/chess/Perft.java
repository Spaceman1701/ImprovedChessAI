package test.chess;

import ethan.chess.BoardPosition;
import ethan.chess.Side;
import ethan.chess.move.Move;
import ethan.chess.move.generator.BishopMoveGenerator;
import ethan.chess.move.generator.PawnMoveGenerator;

import java.util.List;

/**
 * Created by Ethan on 1/2/2017.
 */
public class Perft {
    public static List<Move> generateAllMoves(BoardPosition bp) {
        PawnMoveGenerator wpmg = new PawnMoveGenerator(bp, Side.WHITE);
        PawnMoveGenerator bpmg = new PawnMoveGenerator(bp, Side.BLACK);
        List<Move> moves = wpmg.generateMoves();
        moves.addAll(bpmg.generateMoves());
        return moves;
    }

    public static long perft(int depth, BoardPosition bp) {
        long total = 0;
        List<Move> moves = generateAllMoves(bp);

        if (depth == 1) {
            return moves.size();
        }

        for (Move m : moves) {
            BoardPosition moved = BoardPosition.fromMove(bp, m);
            total += perft(depth - 1, moved);
        }

        return total;
    }

    public static void main(String[] args) {
        System.out.println(perft(1, BoardPosition.defaultInitialPosition()));
    }
}
