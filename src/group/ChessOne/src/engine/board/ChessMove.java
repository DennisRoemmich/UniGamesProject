package engine.board;

import engine.Chess;
import engine.pieces.ChessPieceType;
import engine.squares.File;
import engine.squares.Rank;
import engine.squares.Square;

import java.util.*;

import org.json.simple.JSONObject;

public class ChessMove {
    private final Square mOrigin;
    private final Square mDestination;

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
        //moveObject.put("isValid", true);
        moveObject.put("origin", mOrigin.toString());
        moveObject.put("destination", mDestination.toString());
        return moveObject;
    }

    public static ChessMove valueOf(String shortNotation, Chess game) {
        shortNotation = keepOnlyRequiredChars(shortNotation);
        switch (shortNotation.length()) {
            case 2 -> { return decodeLengthTwo(shortNotation, game); }
            case 3 -> { return decodeLengthThree(shortNotation, game); }
            case 4 -> { return decodeLengthFour(shortNotation, game); }
            case 5 -> { return decodeLengthFive(shortNotation, game); }
            default -> throw new IllegalArgumentException();
        }
    }

    public static ChessMove valueOf(JSONObject moveJSON) {
        if(moveJSON.containsKey("origin") && moveJSON.containsKey("destination")) {
            Square origin = Square.valueOf(moveJSON.get("origin").toString());
            Square destination = Square.valueOf((moveJSON.get("destination").toString()));
            if(moveJSON.containsKey("specialmove")) {
                switch (moveJSON.get("specialmove").toString()) {
                    case "castling":
                        return new CastlingMove(origin, destination);
                    case "promotion":
                        ChessPieceType promotionType = ChessPieceType.valueOf(moveJSON.get("type").toString());
                        return new PromotionChessMove(origin, destination, promotionType);
                }
            }
            return new ChessMove(origin, destination);
        }
        throw new IllegalArgumentException();
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

    private static ChessMove decodeLengthTwo(String shortNotation, Chess game) {
        ChessPieceType pieceType = ChessPieceType.PAWN;
        Square destination = new Square(shortNotation);
        List<Square> possibleOrigins = game.getPossibleOrigins(destination, pieceType);
        if (possibleOrigins.size() == 1) return new ChessMove(possibleOrigins.get(0), destination);
        throw new IllegalArgumentException();
    }

    private static ChessMove decodeLengthThree(String shortNotation, Chess game) {
        if(shortNotation.equals("0-0")) return getCastlingMove(true, game);
        Square destination = new Square(shortNotation.substring(1,3));
        List<ChessMove> possibleMoves = new ArrayList<>();
        char firstChar = shortNotation.charAt(0);
        switch(firstChar) {
            case 'a', 'A', 'c', 'C', 'd', 'D', 'e', 'E', 'f', 'F', 'g', 'G', 'h', 'H' -> {
                var move = decodeLongPawnMove(firstChar, destination, game);
                if(!move.isEmpty()) {
                    return move.get();
                }
            }
            case 'n', 'N', 'r', 'R', 'q', 'Q', 'k', 'K' -> {
                ChessPieceType pieceType = ChessPieceType.valueOf(firstChar);
                possibleMoves.addAll(decodeNonPawnMove(firstChar, destination, game));
            }
            case 'b' -> {
                var move = decodeLongPawnMove('b', destination, game);
                if (!move.isEmpty()) {
                    possibleMoves.add(move.get());
                }
                possibleMoves.addAll(decodeNonPawnMove('B', destination, game));
            }
            case 'B' -> {
                possibleMoves.addAll(decodeNonPawnMove('B', destination, game));
                if (possibleMoves.size() == 0) {
                    var move = decodeLongPawnMove('b', destination, game);
                    if(!move.isEmpty()) {
                        possibleMoves.add(move.get());
                    }
                }
            }
        }
        if(possibleMoves.size() == 1) return possibleMoves.get(0);
        throw new IllegalArgumentException();
    }

    private static ChessMove decodeLengthFour(String shortNotation, Chess game) {
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
                for(Square origin : game.getPossibleOrigins(destination, pieceType)) {
                    if(origin.getRank() == rank) possibleMoves.add(new ChessMove(origin, destination));
                }
            }
            default -> {
                File file = File.valueOf(firstChar);
                for(Square origin : game.getPossibleOrigins(destination, pieceType)) {
                    if(origin.getFile() == file) possibleMoves.add(new ChessMove(origin, destination));
                }
            }
        }
        if(possibleMoves.size() == 1) return possibleMoves.get(0);
        throw new IllegalArgumentException();
    }

    private static ChessMove decodeLengthFive(String shortNotation, Chess game) {

        if(shortNotation.equals("0-0-0")) return getCastlingMove(false, game);

        List<ChessMove> possibleMoves = new ArrayList<>();
        Square origin = new Square(shortNotation.substring(0,2));
        ChessPieceType pieceType = ChessPieceType.valueOf(shortNotation.charAt(2));
        Square destination = new Square(shortNotation.substring(3,5));
        return new ChessMove(origin,destination);
    }

    private static Optional<ChessMove> decodeLongPawnMove(char originFileChar, Square destination, Chess game) {
        File originFile = File.valueOf(originFileChar);
        if(originFile == destination.getFile()) return Optional.empty();
        for(Square square : game.getPossibleOrigins(destination, ChessPieceType.PAWN)) {
            if(square.getFile() == originFile) {
                return Optional.of(new ChessMove(square, destination));
            }
        }
        return Optional.empty();
    }

    private static List<ChessMove> decodeNonPawnMove(char pieceTypeChar, Square destination, Chess game) {
        List<ChessMove> possibleMoves = new ArrayList<>();
        ChessPieceType pieceType = ChessPieceType.valueOf(pieceTypeChar);
        for(Square origin : game.getPossibleOrigins(destination, pieceType)) {
            possibleMoves.add(new ChessMove(origin, destination));
        }
        return possibleMoves;
    }

    private static ChessMove getCastlingMove(boolean isKingSide, Chess game) {
        Rank backRank = game.getCurrentColor().getBackrank();
        File destinationFile = isKingSide ? File.G : File.C;
        Square origin = new Square(backRank, File.E);
        Square destination = new Square(backRank, destinationFile);
        return new ChessMove(origin, destination);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessMove chessMove = (ChessMove) o;
        return Objects.equals(mOrigin, chessMove.mOrigin) && Objects.equals(mDestination, chessMove.mDestination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mOrigin, mDestination);
    }

    @Override
    public String toString() {
        return mOrigin.toString() + " -> " + mDestination.toString();

    }
}
