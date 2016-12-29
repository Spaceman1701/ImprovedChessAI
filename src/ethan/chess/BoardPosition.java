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

    public BoardPosition(SidePosition white, SidePosition black) {
        this.white = white;
        this.black = black;
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

    public List<Integer> generateWhitePawnMoves() {
        List<Integer> moves = new ArrayList<>();
        long pawns = white.pawn;
        long blackOccupied = black.getOccupied();
        long notOccupied = ~getOccupied();


        long rightAttack = (pawns << 9) & blackOccupied & (~FILE_A); //position of pawns shifted 9 where there are black pieces and not on file H
        long leftAttack = (pawns << 7) & blackOccupied & (-FILE_H);
        long forwardMove = (pawns << 8) & (notOccupied) & (~RANK_8); //rank 8 would be a promotion
        long doubleForwardMove = ((((pawns & RANK_2) << 8) & notOccupied) << 8) & notOccupied; //yeah, this is confusing... it moves forward one, then two, to ensure there is an empty path
        //TODO: en passant

        for(int i = Long.numberOfLeadingZeros(rightAttack); i < 64; i++) {
            if((rightAttack & (1L << i)) != 0) {
                moves.add(MoveGenerator.createMove(i - 9, i, true, false, false));
            }
        }
        for(int i = Long.numberOfLeadingZeros(leftAttack); i < 64; i++) {
            if((leftAttack & (1L << i)) != 0) {
                moves.add(MoveGenerator.createMove(i - 7, i, true, false, false));
            }
        }
        for(int i = Long.numberOfLeadingZeros(forwardMove); i < 64; i++) {
            if((forwardMove & (1L << i)) != 0) {
                moves.add(MoveGenerator.createMove(1 - 8, i, false, false, false));
            }
        }
        for(int i = Long.numberOfLeadingZeros(doubleForwardMove); i < 64; i++) {
            if((doubleForwardMove & (1L << i)) != 0) {
                moves.add(MoveGenerator.createMove(i - 16, i, false, true, false));
            }
        }

        return moves;
    }

    public List<Integer> generateBlackPawnMoves() {
        List<Integer> moves = new ArrayList<>();
        long pawns = black.pawn;
        long whiteOccupied = white.getOccupied();
        long notOccupied = ~getOccupied();


        long rightAttack = (pawns >>> 9) & whiteOccupied & (~FILE_H); //position of pawns shifted 9 where there are black pieces and not on file H
        long leftAttack = (pawns >>> 7) & whiteOccupied & (-FILE_A);
        long forwardMove = (pawns >>> 8) & (notOccupied) & (~RANK_1); //rank 8 would be a promotion
        long doubleForwardMove = ((((pawns & RANK_7) >>> 8) & notOccupied) >>> 8) & notOccupied; //yeah, this is confusing... it moves forward one, then two, to ensure there is an empty path
        //TODO: en passant


        for(int i = 0; i < 64; i++) {
            if((rightAttack & (1L << i)) != 0) {
                moves.add(MoveGenerator.createMove(i + 9, i, true, false, false));
            }
        }
        for(int i = 0; i < 64; i++) {
            if((leftAttack & (1L << i)) != 0) {
                moves.add(MoveGenerator.createMove(i + 7, i, true, false, false));
            }
        }
        for(int i = 0; i < 64; i++) {
            if((forwardMove & (1L << i)) != 0) {
                moves.add(MoveGenerator.createMove(1 + 8, i, false, false, false));
            }
        }
        for(int i = 0; i < 64; i++) {
            if((doubleForwardMove & (1L << i)) != 0) {
                moves.add(MoveGenerator.createMove(i + 16, i, false, true, false));
            }
        }

        return moves;
    }

    public long generateRookMoves(int rookSquare, long occupied, long sideOccupied) {
        long bitboard = (1L << rookSquare); //maybe use lookup table
        long reverseBitboard = Long.reverse(bitboard);
        int rank = rookSquare / BOARD_DIM;
        int file = rookSquare % BOARD_DIM;

        long verticalMask = FILES[file] & occupied;
        long reverseVertMask = Long.reverse(verticalMask);

        long horizontalMask = RANKS[rank] & occupied;
        long reverseHorizMask = Long.reverse(horizontalMask);

        long vertMoves = (verticalMask ^ (verticalMask - 2 * bitboard)) |
                Long.reverse(reverseVertMask ^ (reverseVertMask - 2 * reverseBitboard));

        long horizMoves = (horizontalMask ^ (horizontalMask - 2 * bitboard)) |
                Long.reverse(reverseHorizMask ^ (reverseHorizMask - 2 * reverseBitboard));

        long moves = ((vertMoves & FILES[file]) | (horizMoves & RANKS[rank])) & (~sideOccupied);

        return moves;
    }

    public long generateBishopMoves(int bishopSquare, long occupied, long sideOccupied) {
        long bitboard = (1L << bishopSquare);
        long reverseBitboard = Long.reverse(bitboard);
        int rank = bishopSquare / BOARD_DIM;
        int file = bishopSquare % BOARD_DIM;

        int diag = 7 + rank - file;
        int antiDiag = rank + file;

        long diagMask = DIAGONALS[diag] & occupied;
        long antiDiagMask = ANTI_DIAGONALS[antiDiag] & occupied;

        long reverseDiagMask = Long.reverse(diagMask);
        long reverseAntiDiagMask = Long.reverse(antiDiagMask);

        long diagMoves = (diagMask ^ (diagMask - 2 * bitboard)) |
                Long.reverse(reverseDiagMask ^ (reverseDiagMask - 2 * reverseBitboard));
        long antiDiagMoves = (antiDiagMask ^ (antiDiagMask - 2 * bitboard)) |
                Long.reverse(reverseAntiDiagMask ^ (reverseAntiDiagMask - 2 * reverseBitboard));

        long moveBitboard = ((diagMoves & DIAGONALS[diag]) | (antiDiagMoves & ANTI_DIAGONALS[antiDiag])) & (~sideOccupied);

        return moveBitboard;
    }

    public List<Integer> moveBoardToList(long moves, long occupied, int origin) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            if ((moves & (1L << i)) != 0) {
                list.add(MoveGenerator.createMove(origin, i, ((1L << i) & occupied) != 0, false, false));
            }
        }

        return list;
    }

    public long generateQueenMoves(int queenSquare, long occupied, long sideOccupied) {
        return generateBishopMoves(queenSquare, occupied, sideOccupied) |
                generateRookMoves(queenSquare, occupied, sideOccupied);
    }

    public List<Integer> generateWhiteKnightMoves() {
        long moves = 0L;
        long knights = white.knight;

        moves += (knights << KNGIHT_ATTACK_SHIFTS[0]) & (~FILE_A); //15
        moves += (knights >>> KNGIHT_ATTACK_SHIFTS[0]) & (~FILE_H);

        moves += (knights << KNGIHT_ATTACK_SHIFTS[1]) & (~FILE_H); //17
        moves += (knights >>> KNGIHT_ATTACK_SHIFTS[1]) & (~FILE_A);

        moves += (knights << KNGIHT_ATTACK_SHIFTS[2]) & (~(FILE_H | FILE_G)); //6
        moves += (knights >>> KNGIHT_ATTACK_SHIFTS[2]) & (~(FILE_A | FILE_B));

        moves += (knights << KNGIHT_ATTACK_SHIFTS[3]) & (~(FILE_A | FILE_B)); //10
        moves += (knights >>> KNGIHT_ATTACK_SHIFTS[3]) & (~(FILE_H | FILE_G));

        List<Integer> moveList = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            if ((moves & (1L << i)) != 0) {
                for (int shift : KNGIHT_ATTACK_SHIFTS) {
                    if ((knights & (1L << (i + shift))) != 0) {
                        moveList.add(MoveGenerator.createMove(i + shift, i, false, false, false));
                    }
                    if ((knights & (1L << (i - shift))) != 0) {
                        moveList.add(MoveGenerator.createMove(i - shift, i, false, false, false));
                    }
                }
            }
        }

        return moveList;
    }

}
