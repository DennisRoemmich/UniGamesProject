package console;

import engine.analysis.ChessResult;
import engine.board.ChessBoard;
import engine.squares.File;
import engine.squares.Rank;
import engine.squares.Square;
import framework.PrintToConsole;

public final class GamePrinter {

    public static void printBoard(ChessBoard board) {
        PrintToConsole.println("   A   B   C   D   E   F   G   H  ");
        PrintToConsole.println(" ┌───────────────────────────────┐ ");
        for (Rank rank = Rank.M8; rank != null; rank = rank.getBottomNeighbour()) {
            PrintToConsole.print(rank + "│");
            for (File file : File.values()) {
                PrintToConsole.print(' ');
                var piece = board.getPiece(new Square(rank, file));
                if (piece.isEmpty()) {
                    PrintToConsole.print(' ');
                } else {
                    // .toChar() can be changed to .toSymbol() for Unicode symbols
                    PrintToConsole.print(piece.get().toSymbol());
                }
                PrintToConsole.print(" │");
            }
            PrintToConsole.println(rank.toString());
            if (rank != Rank.M1) {
                PrintToConsole.println(" ├───┼───┼───┼───┼───┼───┼───┼───┤ ");
            }
        }
        PrintToConsole.println(" └───────────────────────────────┘ ");
        PrintToConsole.println("   A   B   C   D   E   F   G   H  ");
    }

    public static final void printResult(ChessResult result) {
        switch (result) {
            case DRAW -> PrintToConsole.println("Draw");
            case CHECKMATE -> PrintToConsole.println("Checkmate");
            case STALEMATE -> PrintToConsole.println("Stalemate");
            case NONE -> PrintToConsole.println("The game isn't over.");
            default -> PrintToConsole.println("ERROR: Unknown game result");
        }
    }

    public static final void printHelp() {
        PrintToConsole.println("--------Classic Chess 1v1---------");
        PrintToConsole.println("The classic chess game vs another player via hotseat.");
        PrintToConsole.println("");
        PrintToConsole.println("--------Classic Chess vs AI---------");
        PrintToConsole.println("Challenge vs the computer!");
        PrintToConsole.println("");
        PrintToConsole.println("--------Torpedo Chess 1v1---------");
        PrintToConsole.print("Alternative game mode where your pawns \n ");
        PrintToConsole.print("may always move 2 sqaures. En passant rule applies!");
        PrintToConsole.println("");
    }

    public static final void printGameModeQuestion(boolean networkGame) {
        PrintToConsole.print("Please choose your game mode: \n");
        if (!networkGame) {
            PrintToConsole.print("[C]lassic Chess 1v1, Classic Chess vs [A]I, ");
        } else {
            PrintToConsole.print("[C]lassic Chess 1v1, ");
        }
        PrintToConsole.print("[T]orpedo Chess 1v1, [R]eplay from save file, [H]elp, [Q]uit \n");
    }
}
