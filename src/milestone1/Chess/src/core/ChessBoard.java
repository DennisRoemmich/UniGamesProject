package core;

import core.pieces.*;
import core.positioning.Direction;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The chess board implementation of a chess game.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class ChessBoard implements Cloneable {

    List<PositionedPiece> positionedPieces = new ArrayList<>();

    public ChessBoard() {

    }

    public ChessBoard(ChessBoard board) {
        for(PositionedPiece positionedPiece : board.positionedPieces) {
            this.positionedPieces.add(new PositionedPiece(positionedPiece));
        }
    }

    public void placePiece(ChessPiece piece, Square square) {
        PositionedPiece positionedPiece = new PositionedPiece(square, piece);
        addPiece(positionedPiece);
    }

    public void addPiece(PositionedPiece positionedPiece) {
        removePiece(positionedPiece.getPosition());
        positionedPieces.add(positionedPiece);
    }

    public void removePiece(Square square) {
        positionedPieces.removeIf(pP -> pP.getPosition().equals(square));
    }

    public void removePiece(ChessPiece piece) {
        positionedPieces.removeIf(pP -> pP.getPiece() == piece);
    }

    public Optional<ChessPiece> getPiece(Square square) {
        return positionedPieces.stream().filter(pP -> pP.getPosition().equals(square)).map(pP -> pP.getPiece()).findFirst();
    }

    public void movePiece(ChessPiece piece, Square newSquare) {
        removePiece(piece);
        placePiece(piece, newSquare);
    }

    public void movePiece(ChessMove move) {
        movePiece(move.getOrigin(), move.getDestination());
    }

    public void movePiece(Square origin, Square destination) {
        var piece = getPiece(origin);
        if(piece.isPresent()) {
            removePiece(origin);
            placePiece(piece.get(), destination);
        }
    }

    public boolean isFieldFree(Square square) {
        return getPiece(square).isEmpty();
    }


    public static ChessBoard getStartBoard() {
        ChessBoard board = new ChessBoard();
        for(Color color : Color.values()) {
            Rank backrank = color.getBackrank();
            for (File file : File.values()) {
                board.placePiece(new Pawn(color), new Square(backrank, file).getNext(color.getPawnMoveDirection()).get());
            }
            for (File file : new File[]{File.A, File.H}) {
                board.placePiece(new Rook(color), new Square(backrank, file));
            }
            for (File file : new File[]{File.B, File.G}) {
                board.placePiece(new Knight(color), new Square(backrank, file));
            }
            for (File file : new File[]{File.C, File.F}) {
                board.placePiece(new Bishop(color), new Square(backrank, file));
            }
            board.placePiece(new Queen(color), new Square(backrank, File.D));
            board.placePiece(new King(color), new Square(backrank, File.E));
        }
        return board;
    }

    public Optional<Square> getSquare(ChessPiece piece) {
        return positionedPieces.stream().filter(pP -> pP.getPiece() == piece).map(pP -> pP.getPosition()).findFirst();
    }

    public List<ChessPiece> getPieces() {
        return positionedPieces.stream().map(PositionedPiece::getPiece).toList();
    }

    public List<PositionedPiece> getPositionedPieces() {
        return positionedPieces;
    }

    public List<PositionedPiece> getPositionedPieces(Color color) {
        return positionedPieces.stream().filter(pP -> pP.getPiece().getColor().equals(color)).collect(Collectors.toList());
    }

    public List<PositionedPiece> getPositionedPieces(ChessPieceType type) {
        return positionedPieces.stream().filter(pP -> pP.getPiece().getType().equals(type)).collect(Collectors.toList());
    }

    public List<PositionedPiece> getPositionedPieces(Color color, ChessPieceType type) {
        return positionedPieces.stream().filter(pP -> pP.getPiece().getColor().equals(color) && pP.getPiece().getType().equals(type)).collect(Collectors.toList());
    }
}
