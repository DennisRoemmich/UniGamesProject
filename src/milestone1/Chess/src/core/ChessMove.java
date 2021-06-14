package core;

import core.pieces.ChessPieceType;
import core.positioning.Direction;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChessMove {
    private Square mOrigin;
    private Square mDestination;
    private static Square destination;

    public ChessMove(Square origin, Square destination) {
        this.mOrigin = origin;
        this.mDestination = destination;
    }

    public Square getOrigin() {
        return mOrigin;
    }

    public Square getDestination() {
        return mDestination;
    }

    public JSONObject toJSon() {
        JSONObject moveObject = new JSONObject();
        moveObject.put("origin", mOrigin.toString());
        moveObject.put("destination", mDestination.toString());
        return moveObject;
    }

    public static ChessMove valueOf(String shortNotation) {
        Square destination;
        ChessPieceType pieceType;
        shortNotation = keepOnlyRequiredChars(shortNotation);
        switch (shortNotation.length()) {
            case 2 -> { return decodeLengthTwo(shortNotation); }
            case 3 -> { return decodeLengthThree(shortNotation); }
            case 4 -> { return decodeLengthFour(shortNotation); }
            case 5 -> { return decodeLengthFive(shortNotation); }
            default -> throw new IllegalArgumentException();
        }
    }

    private static String keepOnlyRequiredChars(String shortNotation) {
        shortNotation = shortNotation.replace("x","");
        shortNotation = shortNotation.replace("*","");
        shortNotation = shortNotation.replace("X","");
        shortNotation = shortNotation.replace("+","");
        shortNotation = shortNotation.replace(" ","");
        shortNotation = shortNotation.replace(">","");
        shortNotation = shortNotation.replace("<","");
        return  shortNotation;
    }

    private static ChessMove decodeLengthTwo(String shortNotation) {
        ChessPieceType pieceType = ChessPieceType.PAWN;
        Square destination = new Square(shortNotation);
        List<Square> possibleOrigins = Chess.getPossibleOrigins(destination, pieceType);
        if (possibleOrigins.size() == 1) return new ChessMove(possibleOrigins.get(0), destination);
        throw new IllegalArgumentException();
    }

    private static ChessMove decodeLengthThree(String shortNotation) {
        if(shortNotation.equals("0-0")) return getCastlingMove(true);
        destination = new Square(shortNotation.substring(1,3));
        List<ChessMove> possibleMoves = new ArrayList<>();
        char firstChar = shortNotation.charAt(0);
        switch(firstChar) {
            case 'a', 'A', 'c', 'C', 'd', 'D', 'e', 'E', 'f', 'F', 'g', 'G', 'h', 'H' -> {
                var move = decodeLongPawnMove(firstChar, destination);
                if(!move.isEmpty()) {
                    return move.get();
                }
            }
            case 'n', 'N', 'r', 'R', 'q', 'Q', 'k', 'K' -> {
                ChessPieceType pieceType = ChessPieceType.valueOf(firstChar);
                possibleMoves.addAll(decodeNonPawnMove(firstChar, destination));
            }
            case 'b' -> {
                var move = decodeLongPawnMove('b', destination);
                if (move.isEmpty()) {
                    possibleMoves.addAll(decodeNonPawnMove('B', destination));
                } else {
                    possibleMoves.add(move.get());
                }
            }
            case 'B' -> {
                possibleMoves.addAll(decodeNonPawnMove('B', destination));
                if (possibleMoves.size() == 0) {
                    var move = decodeLongPawnMove('b', destination);
                    if(!move.isEmpty()) {
                        possibleMoves.add(move.get());
                    }
                }
            }
        }
        if(possibleMoves.size() == 1) return possibleMoves.get(0);
        throw new IllegalArgumentException();
    }

    private static ChessMove decodeLengthFour(String shortNotation) {
        List<ChessMove> possibleMoves = new ArrayList<>();
        String destinationString = shortNotation.substring(2,4);
        Square destination = new Square(destinationString);
        try {
            String originString = shortNotation.substring(0,2);
            Square origin = new Square(originString);
            return new ChessMove(origin, destination);
        } catch (Exception e) {

        }
        ChessPieceType pieceType = ChessPieceType.valueOf(shortNotation.charAt(1));
        char firstChar = shortNotation.charAt(0);
        switch (firstChar) {
            case '1', '2', '3', '4', '5', '6', '7', '8' -> {
                Rank rank = Rank.valueOf(firstChar);
                for(Square origin : Chess.getPossibleOrigins(destination, pieceType)) {
                    if(origin.getRank() == rank) possibleMoves.add(new ChessMove(origin, destination));
                }
            }
            default -> {
                File file = File.valueOf(firstChar);
                for(Square origin : Chess.getPossibleOrigins(destination, pieceType)) {
                    if(origin.getFile() == file) possibleMoves.add(new ChessMove(origin, destination));
                }
            }
        }
        if(possibleMoves.size() == 1) return possibleMoves.get(0);
        throw new IllegalArgumentException();
    }

    private static ChessMove decodeLengthFive(String shortNotation) {

        if(shortNotation.equals("0-0-0")) return getCastlingMove(false);

        List<ChessMove> possibleMoves = new ArrayList<>();
        Square origin = new Square(shortNotation.substring(0,2));
        ChessPieceType pieceType = ChessPieceType.valueOf(shortNotation.charAt(2));
        Square destination = new Square(shortNotation.substring(3,5));
        return new ChessMove(origin,destination);
    }

    private static Optional<ChessMove> decodeLongPawnMove(char originFileChar, Square destination) {
        File originFile = File.valueOf(originFileChar);
        if(originFile == destination.getFile()) return Optional.empty();
        for(Square square : Chess.getPossibleOrigins(destination, ChessPieceType.PAWN)) {
            if(square.getFile() == originFile) {
                return Optional.of(new ChessMove(square, destination));
            }
        }
        return Optional.empty();
    }

    private static List<ChessMove> decodeNonPawnMove(char pieceTypeChar, Square destination) {
        List<ChessMove> possibleMoves = new ArrayList<>();
        ChessPieceType pieceType = ChessPieceType.valueOf(pieceTypeChar);
        for(Square origin : Chess.getPossibleOrigins(destination, pieceType)) {
            possibleMoves.add(new ChessMove(origin, destination));
        }
        return possibleMoves;
    }

    private static ChessMove getCastlingMove(boolean isKingSide) {
        Rank backRank = Chess.isItWhitesTurn() ? Rank.M1 : Rank.M8;
        File destinationFile = isKingSide ? File.G : File.C;
        Square origin = new Square(backRank, File.E);
        Square destination = new Square(backRank, destinationFile);
        return new ChessMove(origin, destination);
    }

    @Override public String toString() {
        return mOrigin.toString() + " -> " + mDestination.toString();
    }
}
