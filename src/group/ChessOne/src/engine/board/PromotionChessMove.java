package engine.board;

import engine.pieces.ChessPieceType;
import engine.squares.Square;
import org.json.simple.JSONObject;

public class PromotionChessMove extends ChessMove {

    private final ChessPieceType selectedPieceType;

    public PromotionChessMove(Square origin, Square destination) {
        super(origin, destination);
        selectedPieceType = ChessPieceType.QUEEN;
    }

    public PromotionChessMove(Square origin, Square destination, ChessPieceType pieceType) {
        super(origin, destination);
        selectedPieceType = pieceType;
    }

    public ChessPieceType getSelectedPieceType() {
        return selectedPieceType;
    }

    @Override
    public JSONObject toJSon() {
        JSONObject regularJSON = super.toJSon();
        regularJSON.put("specialmove", "promotion");
        regularJSON.put("type", selectedPieceType.toString());
        return regularJSON;
    }
}
