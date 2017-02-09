package test.chess;

import ethan.chess.BitBoardUtil;
import ethan.chess.BoardPosition;
import ethan.chess.Side;
import ethan.chess.move.Move;
import ethan.chess.move.generator.PawnMoveGenerator;
import org.junit.Test;

import java.util.List;

/**
 * Created by Ethan on 1/4/2017.
 */
public class BoardPositionTest {

    @Test
    public void testPawnMove() {
        BoardPosition bp = BoardPosition.defaultInitialPosition();


        long count = perftWhitePawn(bp, 1);
        System.out.println(count);
        assert count == 240;
    }



    public long perftWhitePawn(BoardPosition inital, int depth) {
        PawnMoveGenerator pg = new PawnMoveGenerator(inital, Side.WHITE);

        List<Move> moves = pg.generateMoves();

        if (depth == 0) {
            return moves.size();
        }
        long count = 0;
        for (Move m : moves) {
            BoardPosition movePos = BoardPosition.fromMove(inital, m);
            count += perftWhitePawn(movePos, depth - 1);
        }

        return count;
    }
}
