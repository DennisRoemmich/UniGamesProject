package core;

import core.pieces.*;
import core.positioning.Direction;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;
import console.ConsoleUI;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main Chess class where the main chess game logic is being executed.
 *
 * @author Jan de Boer, Dennis Roemmich
 */
public class Chess {

    protected ChessBoard mBoard = ChessBoard.getStartBoard();
    protected int mCurrentMove = 0;
    protected Color currentColor = Color.WHITE;
    protected char standardPromotionPiece = 'Q';
    private boolean autoPromotion = true;

    public Chess() {
    }

    public Chess(Chess game) {
        mBoard = new ChessBoard(game.getBoard());
        mCurrentMove = game.getCurrentMove();
        currentColor = game.getCurrentColor();
        autoPromotion = game.getAutoPromotion();
    }

    public void makeMove(ChessMove move) {
        if (!getPossibleMoves().contains(move)) return;

        handleCastling(move);

        if (isPawnMove(move)) {
            handlePawnMove(move);
        }

        mBoard.movePiece(move);

        registerMove(move.getDestination());
        resetEnPassantFlags();
        incrementMove();
    }

    protected void handleCastling(ChessMove move) {
        var possibleKing = mBoard.getPieces().stream().filter(p -> p.getColor().equals(currentColor) && p.getType().equals(ChessPieceType.KING)).findFirst();
        if (possibleKing.isEmpty()) return;
        King king = (King) possibleKing.get();
        if (king.findCastlingMoves(mBoard).contains(move)) {
            switch (move.getDestination().getFile()) {
                case C -> {
                    Square extraOrigin = new Square(currentColor.getBackrank(), File.A);
                    Square extraDestination = new Square(currentColor.getBackrank(), File.D);
                    ChessMove extraMove = new ChessMove(extraOrigin, extraDestination);
                    mBoard.movePiece(extraMove);
                }
                case G -> {
                    Square extraOrigin = new Square(currentColor.getBackrank(), File.H);
                    Square extraDestination = new Square(currentColor.getBackrank(), File.F);
                    ChessMove extraMove = new ChessMove(extraOrigin, extraDestination);
                    mBoard.movePiece(extraMove);
                }
            }
        }
    }

    protected boolean isPawnMove(ChessMove move) {
        return mBoard.getPiece(move.getOrigin()).isPresent() && mBoard.getPiece(move.getOrigin()).get().getType().equals(ChessPieceType.PAWN);
    }

    protected void handlePawnMove(ChessMove move) {
        handleEnPassantCapture(move);
        handlePromotion(move);
        handleDoubleMove(move);
    }

    protected void handleEnPassantCapture(ChessMove move) {
        if (move.getOrigin().getFile().equals(move.getDestination().getFile())) return;
        if(mBoard.getPiece(move.getDestination()).isEmpty()) {
            Square squareToRemove = move.getDestination().getNext(currentColor.getPawnMoveDirection()).get();
            mBoard.removePiece(squareToRemove);
        }
    }

    protected void resetEnPassantFlags() {
        var pawns = mBoard.getPositionedPieces().stream().filter(pP -> pP.getPiece().getType() == ChessPieceType.PAWN);
        for (Pawn pawn : pawns.map(pP -> (Pawn) pP.getPiece()).toList()) {
            pawn.resetDoubleMove();
        }
    }

    protected void registerMove(Square square) {
        var piece = mBoard.getPiece(square);
        if (piece.isPresent()) {
            piece.get().registerMove();
        }
    }

    protected void handleDoubleMove(ChessMove move) {
        ChessPiece piece = mBoard.getPiece(move.getOrigin()).get();
        if (piece.getType() == ChessPieceType.PAWN) {
            if (Math.abs(move.getOrigin().getRank().getIndex() - move.getDestination().getRank().getIndex()) == 2) {
                ((Pawn) piece).registerDoubleMove();
            }
        }
    }

    protected void handlePromotion(ChessMove move) {
        Rank topRank = currentColor.isWhite() ? Rank.M8 : Rank.M1;
        if (move.getDestination().getRank() != topRank) return;
        if (mBoard.getPiece(move.getOrigin()).get().getType() != ChessPieceType.PAWN) return;

        ChessPiece piece = (Pawn) mBoard.getPiece(move.getOrigin()).get();
        ChessPiece promotionPiece = new Queen(currentColor);
        if (!autoPromotion) {
            // TODO : Via requestMove abfragen, statt neue UI zu initialisieren
            ConsoleUI newUI = new ConsoleUI();
            promotionPiece = setPromotionPiece(newUI.setPromotionPiece());
        }
        mBoard.placePiece(promotionPiece, move.getOrigin());
    }

    protected ChessPiece setPromotionPiece(char c) {
        switch (c) {

            case 'n', 'N':
                return new Knight(currentColor);
            case 'b', 'B':
                return new Bishop(currentColor);
            case 'r', 'R':
                return new Rook(currentColor);
            case 'q', 'Q':
                return new Queen(currentColor);
            default:
                throw new IllegalArgumentException();
        }
    }

    protected void incrementMove() {
        currentColor = currentColor.getContrary();
        if (currentColor.isWhite()) {
            mCurrentMove++;
        }
    }

    public List<Square> getPossibleOrigins(Square destination, ChessPieceType pieceType) {
        List<PositionedPiece> positionedPieces = mBoard.getPositionedPieces(currentColor, pieceType);
        positionedPieces = positionedPieces.stream().filter(pP -> pP.getPiece().findMoves(mBoard).stream().anyMatch(m -> m.getDestination().equals(destination))).collect(Collectors.toList());
        return positionedPieces.stream().map(pP -> pP.getPosition()).collect(Collectors.toList());
    }

    public ChessBoard getBoard() {
        return mBoard;
    }

    public int getCurrentMove() {
        return mCurrentMove;
    }

    public ChessResult getResult() {
        return GameOverDetector.checkForMate(currentColor, mBoard);
    }

    public boolean isGameRunning() {
        return getResult() == ChessResult.NONE;
    }

    public boolean getAutoPromotion() {
        return autoPromotion;
    }

    public void setAutoPromotion(boolean set) {
        this.autoPromotion = set;
    }

    public List<ChessMove> getPossibleMoves() {
        var pieces = mBoard.getPieces().stream().filter(p -> p.getColor().equals(currentColor)).collect(Collectors.toList());
        List<ChessMove> possibleMoves = new ArrayList<>();
        pieces.stream().map(p -> possibleMoves.addAll(p.findMoves(mBoard))).collect(Collectors.toList());
        return possibleMoves;
    }

    public Color getCurrentColor() {
        return currentColor;
    }
}
