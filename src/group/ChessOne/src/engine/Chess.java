package src.engine;

import engine.analysis.CheckDetector;
import engine.analysis.GameOverDetector;
import engine.board.ChessBoard;
import engine.board.ChessMove;
import engine.pieces.*;
import engine.squares.File;
import engine.squares.Rank;
import engine.squares.Square;
import console.ConsoleUI;
import engine.analysis.ChessResult;
import engine.pieces.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Main Chess class where the main chess game logic is being executed.
 *
 * @author Jan de Boer, Dennis Roemmich
 */
public class Chess {

    protected ChessBoard mBoard = ChessBoard.getStartBoard();
    protected int mCurrentMove = 0;
    protected int mLastPawnMoveOrCapture = 0;
    protected Color mCurrentColor = Color.WHITE;
    private boolean mAutoPromotion = true;
    private List<ChessMove> mPossibleMoves = new ArrayList<>();
    private boolean mIsGameOver = false;

    public Chess() {
        updatePossibleMoves();
    }

    public Chess(Chess game) {
        mBoard = new ChessBoard(game.getBoard());
        mCurrentColor = game.getCurrentColor();
        mAutoPromotion = game.getAutoPromotion();
        mPossibleMoves = game.getPossibleMoves();
    }

    public void makeMove(ChessMove move) {
        if (!isMovePossible(move)) {
        	return;
        }
        makeMoveWithoutValidation(move);
    }

    public void makeMoveForCheckChecking(ChessMove move) {
        if (isPawnMove(move)) {
            handleEnPassantCapture(move);
        }
        mBoard.movePiece(move);
    }

    public void makeMoveWithoutValidation(ChessMove move) {

        handleCastling(move);

        if (isPawnMove(move)) {
            handleEnPassantCapture(move);
            handlePromotion(move);
            handleDoubleMove(move);
            mLastPawnMoveOrCapture = mCurrentMove;
        }

        if (mBoard.getPiece(move.getDestination()).isPresent()) {
            mLastPawnMoveOrCapture = mCurrentMove;
        }

        mBoard.movePiece(move);

        registerMove(move.getDestination());
        resetEnPassantFlags();
        nextPlayer();
        updatePossibleMoves();
        updateRunningFlag();
    }

    public boolean isMovePossible(ChessMove move) {
        for (ChessMove possibleMove : getPossibleMoves()) {
            if (possibleMove.equals(move)) {
                return true;
            }
        }
        return false;
    }

    protected void handleCastling(ChessMove move) {
        var possibleKings = mBoard.getPositionedPieces(mCurrentColor, ChessPieceType.KING);
        if (possibleKings.isEmpty()) {
        	return;
        }
        King king = (King) possibleKings.get(0).getPiece();
        if (king.findCastlingMoves(this).contains(move)) {
        	Square extraOrigin;
        	Square extraDestination;
        	ChessMove extraMove;
        	
        	switch (move.getDestination().getFile()) {
                case C:
                    extraOrigin = new Square(mCurrentColor.getBackrank(), File.A);
                    extraDestination = new Square(mCurrentColor.getBackrank(), File.D);
                    extraMove = new ChessMove(extraOrigin, extraDestination);
                    mBoard.movePiece(extraMove);
                    break;
                case G:
                    extraOrigin = new Square(mCurrentColor.getBackrank(), File.H);
                    extraDestination = new Square(mCurrentColor.getBackrank(), File.F);
                    extraMove = new ChessMove(extraOrigin, extraDestination);
                    mBoard.movePiece(extraMove);
                    break;
                default:
                	break;
            }
        }
    }

    protected boolean isPawnMove(ChessMove move) {
    	Optional<ChessPiece> p = mBoard.getPiece(move.getOrigin());
    	if (p.isPresent()) {
    		return mBoard.getPiece(move.getOrigin()).isPresent() && p.get().getType().equals(ChessPieceType.PAWN);
    	} else {
    		return false;
    	}
    }

    protected void handleEnPassantCapture(ChessMove move) {
        if (move.getOrigin().getFile().equals(move.getDestination().getFile())) {
        	return;
        }
        Optional<Square>  s = move.getDestination().getNext(mCurrentColor.getPawnMoveDirection());
        if (mBoard.getPiece(move.getDestination()).isEmpty() && s.isPresent() ) {
            Square squareToRemove = s.get();
            mBoard.removePiece(squareToRemove);
        }
    }

    protected void handlePromotion(ChessMove move) {
        Rank topRank = mCurrentColor.isWhite() ? Rank.M8 : Rank.M1;
        if (move.getDestination().getRank() != topRank) {
        	return;
        }
        Optional<ChessPiece> p = mBoard.getPiece(move.getOrigin());
        if (p.isPresent() && p.get().getType() != ChessPieceType.PAWN) {
            	return;
        }

        ChessPiece promotionPiece = new Queen(mCurrentColor);
        if (!mAutoPromotion) {
            ConsoleUI newUI = new ConsoleUI();
            promotionPiece = setPromotionPiece(newUI.setPromotionPiece());
        }
        mBoard.placePiece(promotionPiece, move.getOrigin());
    }

    protected void handleDoubleMove(ChessMove move) {
    	Optional<ChessPiece> p = mBoard.getPiece(move.getOrigin());
    	if (p.isPresent()) {
    		ChessPiece piece = p.get();
    		if (piece.getType() == ChessPieceType.PAWN 
        		&& Math.abs(move.getOrigin().getRank().getIndex() - move.getDestination().getRank().getIndex()) == 2) {
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
            if (piece.getType() == ChessPieceType.PAWN) {
                ((Pawn) piece).resetDoubleMove();
            }
        }
    }

    public void nextPlayer() {
        mCurrentColor = mCurrentColor.getContrary();
        if (mCurrentColor.isWhite()) {
        	mCurrentMove++;
        }
    }

    public List<Square> getPossibleOrigins(Square destination, ChessPieceType pieceType) {
        List<PositionedPiece> positionedPieces = mBoard.getPositionedPieces(mCurrentColor, pieceType);
        List<Square> origins = new ArrayList<>();
        for (PositionedPiece piece : positionedPieces) {
            var moves = piece.getPiece().findMoves(this);
            for (ChessMove move : moves) {
                if (move.getDestination().equals(destination)) {
                    origins.add(piece.getPosition());
                }
            }
        }
        return origins;
    }

    public void updatePossibleMoves() {
        var pieces = mBoard.getPieces(mCurrentColor);
        List<ChessMove> newPossibleMoves = new ArrayList<>();
        for (ChessPiece piece: pieces) {
        var possibleDestinations = piece.findMoves(this);
            newPossibleMoves.addAll(possibleDestinations);
        }
        this.mPossibleMoves = newPossibleMoves;
    }

    public List<ChessMove> getPossibleMoves() {
         return mPossibleMoves;
    }

    public List<ChessMove> getPossiblesCaptureMoves() {
        List<ChessMove> possibleCaptures = new ArrayList<>();
        for (ChessMove move : getPossibleMoves()) {
            if (mBoard.getPiece(move.getDestination()).isPresent()) {
                possibleCaptures.add(move);
            }
        }
        return possibleCaptures;
    }

    public List<ChessMove> getPossibleCheckMoves() {
        List<ChessMove> possibleChecks = new ArrayList<>();
        for (ChessMove move : getPossibleMoves()) {
            if (CheckDetector.isInCheckAfterMove(this, move)) {
                possibleChecks.add(move);
            }
        }
        return possibleChecks;
    }

    private void updateRunningFlag() {
        if (getResult() != ChessResult.NONE) {
            mIsGameOver = false;
        }
    }

    public ChessResult getResult() {
        return GameOverDetector.checkForMate(this);
    }

    public boolean isGameOver() {
        return mIsGameOver;
    }

    public boolean hasAnyPieceMoved() {
        for(ChessPiece piece : mBoard.getPieces()) {
            if (piece.getNumberOfMoves() > 0) {
                return true;
            }
        }
        return false;
    }

    public ChessBoard getBoard() {
        return mBoard;
    }

    public boolean getAutoPromotion() {
        return mAutoPromotion;
    }

    public void setAutoPromotion(boolean set) {
        this.mAutoPromotion = set;
    }

    protected ChessPiece setPromotionPiece(char c) {
        switch (c) {
            case 'n', 'N':
                return new Knight(mCurrentColor);
            case 'b', 'B':
                return new Bishop(mCurrentColor);
            case 'r', 'R':
                return new Rook(mCurrentColor);
            case 'q', 'Q':
                return new Queen(mCurrentColor);
            default:
                throw new IllegalArgumentException();
        }
    }

    public Color getCurrentColor() {
        return mCurrentColor;
    }

    public int getCurrentMove() {
        return mCurrentMove;
    }

    public int getLastPawnMoveOrCapture() {
        return mLastPawnMoveOrCapture;
    }


}
