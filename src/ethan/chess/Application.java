package ethan.chess;

/**
 * Created by Ethan on 12/21/2016.
 */
public class Application {
    public static void main(String[] args) {
        BoardPosition bp = BoardPosition.defaultInitialPosition();
        bp.printBoard(bp.generateQueenMoves(28, bp.getOccupied(), bp.getWhite().getOccupied()));
    }
}
