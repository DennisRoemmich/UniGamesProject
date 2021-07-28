package engine.board;

import engine.pieces.ChessPieceType;
import engine.squares.Square;

import java.util.Objects;

import org.json.simple.JSONObject;

public class PromotionChessMove extends ChessMove {

    private final ChessPieceType mSelectedPieceType;

    public PromotionChessMove(Square origin, Square destination) {
        super(origin, destination);
        mSelectedPieceType = ChessPieceType.QUEEN;
    }

    public PromotionChessMove(Square origin, Square destination, ChessPieceType pieceType) {
        super(origin, destination);
        mSelectedPieceType = pieceType;
    }

    public ChessPieceType getSelectedPieceType() {
        return mSelectedPieceType;
    }

    @SuppressWarnings("unchecked")
	@Override
    public JSONObject toJSon() {
        JSONObject regularJSON = super.toJSon();
        regularJSON.put("specialmove", "promotion");
        regularJSON.put("type", mSelectedPieceType.toString());
        return regularJSON;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
          }
        PromotionChessMove move = (PromotionChessMove) obj;
        return mSelectedPieceType.equals(move.getSelectedPieceType());     
    }
    
    @Override
    public int hashCode() {
    	return Objects.hash(0);	
    }
}
