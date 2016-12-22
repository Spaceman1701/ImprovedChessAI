package ethan.chess;


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


    public static final long RANK_1 = generateRank(7);
    public static final long RANK_2 = generateRank(6);
    public static final long RANK_3 = generateRank(5);
    public static final long RANK_4 = generateRank(4);
    public static final long RANK_5 = generateRank(3);
    public static final long RANK_6 = generateRank(2);
    public static final long RANK_7 = generateRank(1);
    public static final long RANK_8 = generateRank(0);

    public static final long[] RANKS = {RANK_1, RANK_2, RANK_3, RANK_4, RANK_5, RANK_6, RANK_7, RANK_8};


    private SidePosition white;
    private SidePosition black;

    public BoardPosition(SidePosition white, SidePosition black) {
        this.white = white;
        this.black = black;
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

    public static BoardPosition fromArray(String[][] board) {
        SidePosition white = new SidePosition();
        SidePosition black = new SidePosition();

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

    public static void printBoard(long bitboard) {
        for(int i = 0; i < BOARD_SIZE; i++) {
            if (((1L << i) & bitboard) != 0) {
                System.out.print("x");
            } else {
                System.out.print("0");
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

    public List<Integer> generateWhitePawnMoves() {
        List<Integer> moves = new ArrayList<>();
        long pawns = white.pawn;
        long blackOccupied = black.getOccupied();
        long notOccupied = ~getOccupied();


        long leftAttack = (pawns >>> 9) & blackOccupied & (~FILE_H); //position of pawns shifted 9 where there are black pieces and not on file H
        long rightAttack = (pawns >>> 7) & blackOccupied & (-FILE_A);
        long forwardMove = (pawns >>> 8) & (notOccupied) & (~RANK_8); //rank 8 would be a promotion
        long doubleForwardMove = ((((pawns & RANK_2) >>> 8) & notOccupied) >>> 8) & notOccupied; //yeah, this is confusing... it moves forward one, then two, to ensure there is an empty path
        //TODO: en passant

        for(int i = Long.numberOfLeadingZeros(leftAttack); i < 64; i++) {
            if((leftAttack & (1L << i)) != 0) {
                moves.add(MoveGenerator.createMove(i + 9, i, true, false, false));
            }
        }
        for(int i = Long.numberOfLeadingZeros(rightAttack); i < 64; i++) {
            if((rightAttack & (1L << i)) != 0) {
                moves.add(MoveGenerator.createMove(i + 7, i, true, false, false));
            }
        }
        for(int i = Long.numberOfLeadingZeros(forwardMove); i < 64; i++) {
            if((forwardMove & (1L << i)) != 0) {
                moves.add(MoveGenerator.createMove(1 + 8, i, false, false, false));
            }
        }
        for(int i = Long.numberOfLeadingZeros(doubleForwardMove); i < 64; i++) {
            if((doubleForwardMove & (1L << i)) != 0) {
                moves.add(MoveGenerator.createMove(i + 16, i, false, true, false));
            }
        }

        return moves;
    }
}
