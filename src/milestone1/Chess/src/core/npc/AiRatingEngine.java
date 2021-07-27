package core.npc;

import core.*;
import core.pieces.ChessPiece;
import core.positioning.Square;
import framework.CallCounter;
import framework.PrintToConsole;
import framework.TimeKeeper;

import java.util.ArrayList;
import java.util.List;

public class AiRatingEngine {
    public static double rateMove(Chess game, ChessMove chessMove, int depth) {
        if(depth == 2) {
            CallCounter.registerCall("rateMove", true);
        }
        PrintToConsole.println("--> Rating " + " @time " + TimeKeeper.timeToString());
        Chess gameClone = new Chess(game);
        PrintToConsole.println("Cloned game @time " + TimeKeeper.timeToString());
        double score = -1 * rateSituation(gameClone);
        PrintToConsole.println("Rated current situation @time " + TimeKeeper.timeToString());
        gameClone.makeMove(chessMove);
        PrintToConsole.println("Made move @time " + TimeKeeper.timeToString());
        score += rateSituation(gameClone);
        PrintToConsole.println("Rated situation after move @time " + TimeKeeper.timeToString());
        if(depth > 1) {
            //PrintToConsole.println("Starting recursion loop @time " + TimeKeeper.timeToString());
            var possibleMoves = gameClone.getPossibleMoves();
            double numberOfPossibleMoves = possibleMoves.size();
            for(ChessMove nextMove : possibleMoves) {
                score += rateMove(gameClone, nextMove, depth - 1);
            }
        }
        return score;
    }

    public static double rateSituation(Chess game) {
        double score = 0;
        switch(GameOverDetector.checkForMate(game)) {
            case CHECKMATE:
                score -= 10000 * game.getCurrentColor().getScoreFactor();
                break;
            case STALEMATE, DRAW:
                score = 0;
                break;
            case NONE:
                for(ChessPiece piece : game.getBoard().getPieces()) {
                    score += piece.getSignedValue();
                }
                for(ChessPiece piece : getThreatenedPieces(game)) {
                    score -= piece.getSignedValue() * piece.getColor().getScoreFactor();
                }
                break;
        }
        return score;
    }

    public static int numberOfCoveringPieces(ChessBoard board, Color color, Square squareToTest) {
        var pieces = board.getPositionedPieces(color);
        int number = 0;
        for(PositionedPiece piece : pieces) {
            for(Square square : piece.getPiece().findCoveredSquares(board)) {
                if(square.equals(squareToTest)) {
                    number++;
                }
            }
        }
        return number;
    }

    public static List<ChessPiece> getThreatenedPieces(Chess game) {
        boolean isWhite = game.getCurrentColor().isWhite();

        List<ChessPiece> threatenedPieces = new ArrayList<>();
        for(PositionedPiece positionedPiece : game.getBoard().getPositionedPieces(game.getCurrentColor())) {
            if(0 < numberOfCoveringPieces(game.getBoard(), game.getCurrentColor().getContrary(), positionedPiece.getPosition())){
                threatenedPieces.add(positionedPiece.getPiece());
            }
        }

        return threatenedPieces;
    }

}
