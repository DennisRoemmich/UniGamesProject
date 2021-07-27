package core;

import core.pieces.*;
import core.positioning.Direction;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;
import console.ConsoleUI;
import framework.CallCounter;
import framework.PrintToConsole;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Chess class where the main chess game logic is being executed.
 *
 * @author Jan de Boer, Dennis Roemmich
 */
public class Chess {

    protected ChessBoard mBoard = ChessBoard.getStartBoard();
    protected int mCurrentMove = 0;
    protected int lastPawnMoveOrCapture = 0;
    protected Color currentColor = Color.WHITE;
    private boolean autoPromotion = true;
    //private PositionRepetitionDetector repetitionDetector = new PositionRepetitionDetector();

    public Chess() {
    }

    public Chess(Chess game) {
        mBoard = new ChessBoard(game.getBoard());
        currentColor = game.getCurrentColor();
        autoPromotion = game.getAutoPromotion();
        //repetitionDetector = new PositionRepetitionDetector();
    }

    public void makeMove(ChessMove move) {
        CallCounter.registerCall("makeMove", false);
        if(!isMovePossible(move)) return;

        handleCastling(move);

        if (isPawnMove(move)) {
            handleEnPassantCapture(move);
            handlePromotion(move);
            handleDoubleMove(move);
            lastPawnMoveOrCapture = mCurrentMove;
        }

        if (mBoard.getPiece(move.getDestination()).isPresent()) {
            lastPawnMoveOrCapture = mCurrentMove;
        }

        mBoard.movePiece(move);
        //PrintToConsole.println(mBoard.hashCode() + " : " + repetitionDetector.addHash(move.hashCode()));

        registerMove(move.getDestination());
        resetEnPassantFlags();
        nextPlayer();
    }

    public boolean isMovePossible(ChessMove move) {
        for (ChessMove possibleMove : getPossibleMoves()) {
            if (possibleMove.equals(move)){
                return true;
            }
        }
        return false;
    }

    protected void handleCastling(ChessMove move) {
        var possibleKings = mBoard.getPositionedPieces(currentColor, ChessPieceType.KING);
        if (possibleKings.isEmpty()) return;
        King king = (King) possibleKings.get(0).getPiece();
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

    protected void handleEnPassantCapture(ChessMove move) {
        if (move.getOrigin().getFile().equals(move.getDestination().getFile())) return;
        if(mBoard.getPiece(move.getDestination()).isEmpty()) {
            Square squareToRemove = move.getDestination().getNext(currentColor.getPawnMoveDirection()).get();
            mBoard.removePiece(squareToRemove);
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

    protected void handleDoubleMove(ChessMove move) {
        ChessPiece piece = mBoard.getPiece(move.getOrigin()).get();
        if (piece.getType() == ChessPieceType.PAWN) {
            if (Math.abs(move.getOrigin().getRank().getIndex() - move.getDestination().getRank().getIndex()) == 2) {
                ((Pawn) piece).registerDoubleMove();
            }
        }
    }

    protected void registerMove(Square square) {
        var piece = mBoard.getPiece(square);
        if (piece.isPresent()) {
            piece.get().registerMove();
        }
    }

    protected void resetEnPassantFlags() {
        var pawns = mBoard.getPositionedPieces(Color.WHITE, ChessPieceType.PAWN);
        pawns.addAll(mBoard.getPositionedPieces(Color.BLACK, ChessPieceType.PAWN));
        for (ChessPiece piece : mBoard.getPieces()) {
            if(piece.getType() == ChessPieceType.PAWN) {
                ((Pawn)piece).resetDoubleMove();
            }
        }
    }

    protected void nextPlayer() {
        currentColor = currentColor.getContrary();
        if(currentColor.isWhite()) mCurrentMove++;
    }

    public List<Square> getPossibleOrigins(Square destination, ChessPieceType pieceType) {
        List<PositionedPiece> positionedPieces = mBoard.getPositionedPieces(currentColor, pieceType);
        List<Square> origins = new ArrayList<>();
        for(PositionedPiece piece : positionedPieces) {
            var moves = piece.getPiece().findMoves(mBoard);
            for(ChessMove move : moves) {
                if(move.getDestination().equals(destination)) {
                    origins.add(piece.getPosition());
                }
            }
        }
        return origins;
    }

    public List<ChessMove> getPossibleMoves() {
        var pieces = mBoard.getPieces(currentColor);
        List<ChessMove> possibleMoves = new ArrayList<>();
        for(ChessPiece piece: pieces) {
            possibleMoves.addAll(piece.findMoves(mBoard));
        }
        return possibleMoves;
    }

    public List<ChessMove> getPossiblesCaptureMoves() {
        List<ChessMove> possibleCaptures = new ArrayList<>();
        for(ChessMove move : getPossibleMoves()) {
            if(mBoard.getPiece(move.getDestination()).isPresent()) {
                possibleCaptures.add(move);
            }
        }
        return possibleCaptures;
    }

    public List<ChessMove> getPossibleCheckMoves() {
        List<ChessMove> possibleChecks = new ArrayList<>();
        for(ChessMove move : getPossibleMoves()) {
            if(CheckDetector.isInCheckAfterMove(this, move)) {
                possibleChecks.add(move);
            }
        }
        return possibleChecks;
    }

    public ChessResult getResult() {
        return GameOverDetector.checkForMate(this);
    }

    public boolean isGameRunning() {
        return getResult() == ChessResult.NONE;
    }

    public ChessBoard getBoard() {
        return mBoard;
    }

    public boolean getAutoPromotion() {
        return autoPromotion;
    }

    public void setAutoPromotion(boolean set) {
        this.autoPromotion = set;
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

    public Color getCurrentColor() {
        return currentColor;
    }

    public int getCurrentMove() {
        return mCurrentMove;
    }

    public int getLastPawnMoveOrCapture() {
        return lastPawnMoveOrCapture;
    }
}
