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
            CallCounter.registerCall("rateMove", false);
        }
        //PrintToConsole.println("--> Rating " + " @time " + TimeKeeper.timeToString());
        Chess gameClone = new Chess(game);
        //PrintToConsole.println("Cloned game @time " + TimeKeeper.timeToString());
        double score = -1 * rateSituation(gameClone);
        //PrintToConsole.println("Rated current situation @time " + TimeKeeper.timeToString());
        gameClone.makeMoveWithoutValidation(chessMove);
        //PrintToConsole.println("Made move @time " + TimeKeeper.timeToString());
        score += rateSituation(gameClone);
        //PrintToConsole.println("Rated situation after move @time " + TimeKeeper.timeToString());
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
        //PrintToConsole.println("-- start rating @time " + TimeKeeper.timeToString());
        switch(GameOverDetector.checkForMate(game)) {
            case CHECKMATE:
                //PrintToConsole.println("-- handling checkmate @time " + TimeKeeper.timeToString());
                score -= 10000 * game.getCurrentColor().getScoreFactor();
                break;
            case STALEMATE, DRAW:
                //PrintToConsole.println("-- handling stalemate @time " + TimeKeeper.timeToString());
                score = 0;
                break;
            case NONE:
                //PrintToConsole.println("-- handling none @time " + TimeKeeper.timeToString());
                for(ChessPiece piece : game.getBoard().getPieces()) {
                    score += piece.getSignedValue();
                }
                //PrintToConsole.println("-- handling threatened pieces @time " + TimeKeeper.timeToString());
                /*for(ChessPiece piece : getThreatenedPieces(game)) {
                    score -= piece.getSignedValue() * piece.getColor().getScoreFactor();
                }*/
                break;
        }
        //PrintToConsole.println("-- rating finished @time " + TimeKeeper.timeToString());
        return score;
    }

    public static List<ChessPiece> getThreatenedPieces(Chess game) {
        boolean isWhite = game.getCurrentColor().isWhite();

        //PrintToConsole.println("---- handling checkmate @time " + TimeKeeper.timeToString());
        List<ChessPiece> threatenedPieces = new ArrayList<>();
        for(PositionedPiece positionedPiece : game.getBoard().getPositionedPieces(game.getCurrentColor())) {
            if(0 < numberOfCoveringPieces(game, positionedPiece.getPosition())){
                threatenedPieces.add(positionedPiece.getPiece());
            }
        }

        return threatenedPieces;
    }

    public static int numberOfCoveringPieces(Chess game, Square squareToTest) {
        var pieces = game.getBoard().getPositionedPieces(game.getCurrentColor());
        int number = 0;
        for(PositionedPiece piece : pieces) {
            for(Square square : piece.getPiece().findCoveredSquares(game)) {
                if(square.equals(squareToTest)) {
                    number++;
                }
            }
        }
        return number;
    }

}
