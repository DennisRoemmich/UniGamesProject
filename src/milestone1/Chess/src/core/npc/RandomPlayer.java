package core.npc;

import console.ConsoleMain;
import console.Controller;
import core.pieces.ChessPiece;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;
import framework.Player;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RandomPlayer implements Player {

    protected Controller mController;

    public RandomPlayer(Controller controller) {
        this.mController = controller;
    }

    //Random dummy AI
    @Override
    public JSONObject requestMove(JSONObject dataType) {

        //Initialize the chess move
        var possibleMoves = mController.getGame().getPossibleMovesQuickly();
        Collections.shuffle(possibleMoves);
        if (possibleMoves.isEmpty()) {
            new JSONObject();
        }
        return possibleMoves.get(0).toJSon();
    }
}
