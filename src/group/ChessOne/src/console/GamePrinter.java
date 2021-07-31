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

    public static final void printGameModes() {
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

    public static final void printHelp() {
        PrintToConsole.println("----------Commands----------");
        PrintToConsole.print("*help* Prints the current screen with ");
        PrintToConsole.println("information on commands and how to play the game ");
        PrintToConsole.println("*menu* Opens the game settings menu");
        PrintToConsole.println("*undo* Undoes the last move ");
        PrintToConsole.println("*exit* Closes the chess application ");
        PrintToConsole.println("");
        PrintToConsole.println("--------How to play---------");
        PrintToConsole.println("This chess app uses the official chess notation to register moves.");
        PrintToConsole.println("Simply type the square you want your piece to move to, e.g. e (column) 4 (row).");
        PrintToConsole.print("In case there are several possible moves (like BISHOP can move ");
        PrintToConsole.println("to e4 and PAWN can move to e4), you must define the moving piece. ");
        PrintToConsole.println("This can be done by typing Be4 (BISHOP to e4) ");
        PrintToConsole.print("For special moves like castling you may either move the king ");
        PrintToConsole.println("two squares or use the special castling notation (O-O or O-O-O) ");
        PrintToConsole.println("");
        PrintToConsole.print("For further information please visit: ");
        PrintToConsole.println("https://en.wikipedia.org/wiki/Algebraic_notation_(chess)");
        PrintToConsole.println("");
    }
}
