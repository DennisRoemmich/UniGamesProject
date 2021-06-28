package core;

import console.ConsoleMain;
import console.Controller;
import core.pieces.ChessPiece;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;
import frameworkchess.Player;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class AiPlayer implements Player {

	protected Controller mController;

	public AiPlayer(Controller controller) {
		this.mController = controller;
	}
	
	//Random dummy AI
	@Override
    public JSONObject requestMove(JSONObject dataType) {
		if (dataType.get("type") != "move") {
			return new JSONObject();
		}
		try {
			
			//Necessary values for the chess move
			Square origin;
			Square destination;
			
			//All squares with pieces
			List <Square> originList = mController.getGame().getBoard().findSquaresOfPieces(false); 
			
			//A random square is chosen
			int randomOriginIndex = (int) Math.floor(Math.random()*(originList.size() -1));
			origin = originList.get(randomOriginIndex);
			
			//Saves the chess piece for the findMoves check
			ChessPiece piece = mController.getGame().getBoard().getPiece(origin);
				
			//Possible destinations are checked
			List<Square> possibleDestinations = new ArrayList<>();	
		    for (int rank = 0; rank < 8; rank++) {
		        for (int file = 0; file < 8; file++) {
		        	destination = new Square(Rank.valueOf(rank), File.valueOf(file));
		        	if (piece.findMoves(mController.getGame().getBoard(), origin).contains(destination)) {
		        		possibleDestinations.add(destination);
		        	}
		        }
		    }
		    
		    //Choose a random destination
		    int randomDestinationIndex = (int) Math.floor(Math.random()*(possibleDestinations.size() -1));
		    destination = possibleDestinations.get(randomDestinationIndex);
			
		    //Initialize the chess move
			ChessMove move = new ChessMove(origin, destination);
			return move.toJSon();
		} catch (Exception e) {
			System.out.println("Unknown Issue.");
			return new JSONObject();
		}
    }
}
