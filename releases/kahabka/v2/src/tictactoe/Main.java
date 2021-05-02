package tictactoe;

public class Main {


    public static void main(String[] args) {

        var player1 = "Player 1";
        var player2 = "Player 2";

        // if application was started with arguments, they are used as player names

        if (args.length > 0){
            player1 = args[0];
        }

        if (args.length > 1){
            player2 = args[1];
        }

        var game = new TicTacToe(player1, player2);
        game.startGame();

    }

}
