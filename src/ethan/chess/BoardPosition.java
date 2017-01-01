package ethan.chess;


import ethan.chess.move.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 12/18/2016.
 */
public class BoardPosition {
    public static final int BOARD_SIZE = 64;
    public static final int BOARD_DIM = 8;

    public static final String[][] DEFAULT_INITIAL_POSITION = {
        {"br", "bn", "bb", "bq", "bk", "bb", "bn", "br"},
        {"bp", "bp", "bp", "bp", "bp", "bp", "bp", "bp"},
        {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
        {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
        {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
        {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
        {"wp", "wp", "wp", "wp", "wp", "wp", "wp", "wp"},
        {"wr", "wn", "wb", "wq", "wk", "wb", "wn", "wr"}
    };

    public static final long FILE_A = generateFile(0);
    public static final long FILE_B = generateFile(1);
    public static final long FILE_C = generateFile(2);
    public static final long FILE_D = generateFile(3);
    public static final long FILE_E = generateFile(4);
    public static final long FILE_F = generateFile(5);
    public static final long FILE_G = generateFile(6);
    public static final long FILE_H = generateFile(7);

    public static final long[] FILES = {FILE_A, FILE_B, FILE_C, FILE_D, FILE_E, FILE_F, FILE_G, FILE_H};


    public static final long RANK_1 = generateRank(0);
    public static final long RANK_2 = generateRank(1);
    public static final long RANK_3 = generateRank(2);
    public static final long RANK_4 = generateRank(3);
    public static final long RANK_5 = generateRank(4);
    public static final long RANK_6 = generateRank(5);
    public static final long RANK_7 = generateRank(6);
    public static final long RANK_8 = generateRank(7);

    public static final long[] RANKS = {RANK_1, RANK_2, RANK_3, RANK_4, RANK_5, RANK_6, RANK_7, RANK_8};


    public static final long[] DIAGONALS = generateDiagonals();

    public static final long[] ANTI_DIAGONALS = generateAntiDiagonals();

    private static final int NORTH_SHIFT = 8;
    private static final int NORTH_WEST_SHIFT = 7;
    private static final int NORTH_EAST_SHIFT = 9;
    private static final int EAST_SHIFT = 1;

    private static final int[] KNGIHT_ATTACK_SHIFTS = {15, 17, 6, 10}; //for both left and right shifts

    private SidePosition white;
    private SidePosition black;

    private List<Piece> pieceList;

    private BoardPosition(SidePosition white, SidePosition black) {
        this.white = white;
        this.black = black;
    }

    public BoardPosition(BoardPosition base) {
        this.white = new SidePosition(base.getWhite());
        this.black = new SidePosition(base.getBlack());
    }

    private static long[] generateDiagonals() {
        long[] diagonals = new long[15];
        for (int j = 0; j < 15; j ++) {
            for (int i = 0; i < 64; i++) {
                int rank = i / 8;
                int file = i % 8;
                if (7 + rank - file == j) {
                    diagonals[j] += (1L << i);
                }
            }
        }
        return diagonals;
    }

    private static long[] generateAntiDiagonals() {
        long[] antiDiagonals = new long[15];
        for (int j = 0; j < 15; j ++) {
            for (int i = 0; i < 64; i++) {
                int rank = i / 8;
                int file = i % 8;
                if (rank + file == j) {
                    antiDiagonals[j] += (1L << i);
                }
            }
        }
        return antiDiagonals;
    }

    private static long generateFile(int f) {
        long bitboard = 0;
        for(int i = 0; i < BOARD_SIZE; i++) {
            if(i % BOARD_DIM == 0) {
                bitboard += 1L << (i + f);
            }
        }
        return bitboard;
    }

    private static long generateRank(int r) {
        long bitboard = 0;
        for(int i = 8 * r; i < 8 * r + 8; i++) {
            bitboard += 1L << i;
        }
        return bitboard;
    }

    public static BoardPosition fromArray(String[][] inputBoard) {
        SidePosition white = new SidePosition();
        SidePosition black = new SidePosition();

        String[][] board = new String[BOARD_DIM][BOARD_DIM];

        for(int i = 0; i < BOARD_DIM; i++) {
            board[i] = inputBoard[BOARD_DIM - 1 - i];
        }

        System.out.println(board[0][0]);

        for(int i = 0; i < BOARD_SIZE; i++) {
            String piece = board[i / BOARD_DIM][i % BOARD_DIM];
            int bit = i;
            if(!piece.equals("  ")) {
                SidePosition pos;
                if(piece.startsWith("w")) {
                    pos = white;
                } else {
                    pos = black;
                }

                switch (piece.charAt(1)) {
                    case 'p':
                        pos.pawn += 1L << bit;
                        break;
                    case 'r':
                        pos.rook += 1L << bit;
                        break;
                    case 'n':
                        pos.knight += 1L << bit;
                        break;
                    case 'b':
                        pos.bishop += 1L << bit;
                        break;
                    case 'k':
                        pos.king += 1L << bit;
                        break;
                    case 'q':
                        pos.queen += 1L << bit;
                        break;
                }
            }
        }

        return new BoardPosition(white, black);
    }

    public static BoardPosition fromMove(BoardPosition base, Move move) { //this is pretty poorly written
        BoardPosition copy = new BoardPosition(base);

        int moveStart = move.getStartPosition();
        int moveEnd = move.getEndPosition();
        boolean capture = move.isCapture();
        boolean isWhite = move.getSide();

        SidePosition moveSide;
        SidePosition otherSide;
        if (isWhite) {
            moveSide = copy.getWhite();
            otherSide = copy.getBlack();
        } else {
            moveSide = copy.getBlack();
            otherSide = copy.getWhite();
        }
        long fromBit = 1L << moveStart;
        long toBut = 1L << moveEnd;

        if ((fromBit & moveSide.pawn) != 0) {
            moveSide.pawn &= ~fromBit;
            moveSide.pawn |= moveEnd;
        } else if ((fromBit & moveSide.bishop) != 0) {
            moveSide.bishop &= ~fromBit;
            moveSide.bishop |= moveEnd;
        }else if ((fromBit & moveSide.rook) != 0) {
            moveSide.rook &= ~fromBit;
            moveSide.rook |= moveEnd;
        }else if ((fromBit & moveSide.queen) != 0) {
            moveSide.queen &= ~fromBit;
            moveSide.queen |= moveEnd;
        }else if ((fromBit & moveSide.knight) != 0) {
            moveSide.knight &= ~fromBit;
            moveSide.knight |= moveEnd;
        }else if ((fromBit & moveSide.king) != 0) {
            moveSide.king &= ~fromBit;
            moveSide.king |= moveEnd;
        }

        if (capture) {
            if ((otherSide.pawn & toBut) != 0) {
                otherSide.pawn &= ~toBut;
            } else if ((otherSide.bishop & toBut) != 0) {
                otherSide.bishop &= ~toBut;
            } else if ((otherSide.rook & toBut) != 0) {
                otherSide.rook &= ~toBut;
            } else if ((otherSide.queen & toBut) != 0) {
                otherSide.queen &= ~toBut;
            } else if ((otherSide.knight & toBut) != 0) {
                otherSide.knight &= ~toBut;
            } else if ((moveSide.king & toBut) != 0) {
                otherSide.king &= ~toBut;
            }
        }

        return copy;
    }

    public static void printBoard(long bitboard) {
        long output = Long.reverseBytes(bitboard);
        for(int i = 0; i < BOARD_SIZE; i++) {
            if((output & (1L << i)) != 0) {
                System.out.print("X ");
            } else {
                System.out.print("0 ");
            }
            if(i % 8 == 7) {
                System.out.println();
            }
        }
    }

    public static BoardPosition defaultInitialPosition() {
        return BoardPosition.fromArray(DEFAULT_INITIAL_POSITION);
    }

    public SidePosition getWhite() {
        return white;
    }

    public SidePosition getBlack() {
        return black;
    }

    public long getOccupied() {
        return white.getOccupied() | black.getOccupied();
    }

    /**
     * @return positive for white and negative for black
     */
    public int evaluate() {
        int whiteValue = white.getMaterialValue();
        int blackValue = black.getMaterialValue();
        //evaluate positional advantage and stuff here
        return whiteValue - blackValue;
    }

    public boolean isPieceAt(PieceType piece, boolean white, int location) { //mostly for debug
        long board = 0L;
        SidePosition side = this.white;
        if(!white) {
            side = this.black;
        }
        switch (piece) {
            case PAWN:
                board = side.pawn;
                break;
            case ROOK:
                board = side.rook;
                break;
            case KNIGHT:
                board = side.knight;
                break;
            case BISHOP:
                board = side.bishop;
                break;
            case QUEEN:
                board = side.queen;
                break;
            case KING:
                board = side.king;
                break;
        }
        return (board & (1L << location)) != 0;
    }

    public SidePosition getSidePosition(Side side) {
        switch (side) {
            case WHITE:
                return getWhite();
            case BLACK:
                return getBlack();
        }
        return null;
    }

    public SidePosition getOpponentSidePosition(Side side) {
        switch (side) {
            case BLACK:
                return getWhite();
            case WHITE:
                return getBlack();
        }
        return null;
    }
}
