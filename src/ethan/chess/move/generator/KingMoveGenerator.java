package ethan.chess.move.generator;

import ethan.chess.BoardPosition;
import ethan.chess.move.Move;
import ethan.chess.move.MoveGenerator;
import ethan.chess.Side;
import java.util.List;

/**
 * Created by Ethan on 12/30/2016.
 */
public class KingMoveGenerator extends MoveGenerator {


    public KingMoveGenerator() {
        super(null, null);
    }

    @Override
    public long getMoveBitboard() {
        return 0;
    }

    @Override
    public byte getPieceSquare() {
        return (byte)0;
    }
}
