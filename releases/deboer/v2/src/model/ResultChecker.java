package model;

import elements.*;

public final class ResultChecker {

	
	private ResultChecker(){} // Prevent initialization
	
	public static Result getResult(GameBoard gameBoard) {
		for(Position[] positions: winPatterns) {
			Sign sign = commonSign(gameBoard, positions);
			if (sign == Sign.X) return Result.X_WON;
			if (sign == Sign.O) return Result.O_WON;
		}
		if(!gameBoard.freeCellAvailable()) {
			return Result.TIE;
		}
		return Result.IN_PROGRESS;
	}
	
	private static Sign commonSign(GameBoard gameBoard, Position... positions) {
		return commonSign(gameBoard.getSigns(positions));
	}
	
	private static Sign commonSign(Sign... signs) {
		if (signs.length == 0) return Sign.NONE;
		Sign referenceSign = signs[0];
		for(Sign sign: signs) {
			if(sign != referenceSign) return Sign.NONE;
		}
		return referenceSign;
	}
	
	private static Position[][]  winPatterns = {
			{Position.TOPLEFT, Position.TOPCENTER, Position.TOPRIGHT},
			{Position.CENTERLEFT, Position.CENTERCENTER, Position.CENTERRIGHT},
			{Position.BOTTOMLEFT, Position.BOTTOMCENTER, Position.BOTTOMRIGHT},
			{Position.TOPLEFT, Position.CENTERCENTER, Position.BOTTOMRIGHT},
			{Position.TOPRIGHT, Position.CENTERCENTER, Position.BOTTOMLEFT},
			{Position.TOPLEFT, Position.CENTERLEFT, Position.BOTTOMLEFT},
			{Position.TOPCENTER, Position.CENTERCENTER, Position.BOTTOMCENTER},
			{Position.TOPRIGHT, Position.CENTERRIGHT, Position.BOTTOMRIGHT}
	};
}
