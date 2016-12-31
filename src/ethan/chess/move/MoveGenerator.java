package ethan.chess.move;

import java.util.List;

/**
 * Created by Ethan on 12/30/2016.
 */
public interface MoveGenerator {
    List<Move> generateMoves(long moveBitboard);
}
