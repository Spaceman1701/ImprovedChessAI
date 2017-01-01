package ethan.chess.move.generator;

import ethan.chess.move.SlidingMoveGenerator;

/**
 * Created by Ethan on 12/30/2016.
 */
public class KingMoveGenerator extends SlidingMoveGenerator {


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

    public long getAttackBitboard() {
        return 0L;
    }
}
