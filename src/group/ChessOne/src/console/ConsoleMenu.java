package console;

import engine.Chess;
import engine.Controller;
import framework.FileController;
import framework.GameLog;
import framework.Player;
import framework.PrintToConsole;
import network.ClientController;
import network.NetworkPlayer;
import network.NetworkState;
import npc.AiPlayer;
import org.json.simple.JSONObject;

import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConsoleMenu {

    private boolean mIsNetworkGame;
    private boolean mIsHost;
    private Opponent mOpponent;
    private Optional<GameLog> gameLog = Optional.empty();
    private boolean mStandardChess;

    private Scanner mScanner = new Scanner(System.in);
    private boolean endFlag = false;

    private BlockingQueue<String> replyQueue = new LinkedBlockingQueue<>();

    public void run() {
        PrintToConsole.println("Welcome to Chess!");

        askNetworkOrLocal();
        if (mIsNetworkGame) {
            askHostOrClient();
        } else {
            askOpponent();
        }
        if (!(mIsNetworkGame && !mIsHost)) {
            askLoadGame();
            if (gameLog.isEmpty()) {
                askGameMode();
            }
        }

        startGame();
        while (replyQueue.peek() == null) {
        }
    }

    public void askNetworkOrLocal() {
        boolean wrongMenuInput = true;
        while (wrongMenuInput) {
            PrintToConsole.println("Play [L]ocally, play via [N]etwork or [Q]uit");

            String input = mScanner.nextLine();

            switch (input) {
                case "l", "L":
                    mIsNetworkGame = false;
                    wrongMenuInput = false;
                    break;
                case "n", "N":
                    mIsNetworkGame = true;
                    wrongMenuInput = false;
                    break;
                case "q", "Q":
                    endFlag = true;
                    return;
                default:
                    break;
            }
        }
    }

    public void askHostOrClient() {
        boolean wrongMenuInput = true;
        while (wrongMenuInput) {
            PrintToConsole.println("Do you want to be the [H]ost or [C]lient?");

            String input = mScanner.nextLine();

            switch (input) {
                case "h", "H":
                    mIsHost = true;
                    mOpponent = Opponent.NETWORK_PLAYER;
                    wrongMenuInput = false;
                    break;
                case "c", "C":
                    mIsHost = false;
                    wrongMenuInput = false;
                    PrintToConsole.print("Awaiting network confirmation... (Popup window)\n");
                    break;
                case "q", "Q":
                    endFlag = true;
                    return;
                default:
                    break;
            }
        }
    }

    public void askOpponent() {
        boolean correctInput = false;
        while (!correctInput) {

            PrintToConsole.println("Play against [A]I or [H]otseat?");
            String input = mScanner.nextLine();

            correctInput = true;

            switch (input) {
                case "a", "A":
                    mOpponent = Opponent.AI_PLAYER;
                    break;
                case "h", "H":
                    mOpponent = Opponent.HOTSEAT;
                    break;
                case "q", "Q":
                    endFlag = true;
                    break;
                default:
                    PrintToConsole.println("Please try again");
                    correctInput = false;
                    break;
            }
        }
    }

    public void askLoadGame() {
        boolean correctInput = false;
        while (!correctInput) {
            PrintToConsole.println("Load a game? [Y/N]");

            String input = mScanner.nextLine();
            correctInput = true;

            switch (input) {
                case "y", "Y":
                    gameLog = askGameLog();
                    correctInput = true;
                    break;
                case "n", "N":
                    gameLog = Optional.empty();
                    correctInput = true;
                    break;
                case "q", "Q":
                    endFlag = true;
                    break;
                default:
                    PrintToConsole.println("Please try again");
                    correctInput = false;
                    break;
            }
        }
    }

    public Optional<GameLog> askGameLog() {
        boolean correctInput = false;
        while (!correctInput) {
            PrintToConsole.println("Please enter the save file name or type [A] for Abort. ");
            PrintToConsole.println("To replay your last game type the filename of your newest created save state");
            String inputTwo = mScanner.nextLine();
            if (!"a".equalsIgnoreCase(inputTwo)) {
                try {
                    correctInput = true;
                    JSONObject gameLogJSon = FileController.loadJSon(inputTwo);
                    return Optional.of(GameLog.valueOf(gameLogJSon));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                correctInput = true;
                return Optional.empty();
            }
        }
        return Optional.empty();
    }


    public void askGameMode() {
        boolean correctInput = false;
        while (!correctInput) {

            GamePrinter.printGameModeQuestion(mIsNetworkGame);
            String input = mScanner.nextLine();

            correctInput = true;

            switch (input) {
                case "c", "C":
                    mStandardChess = true;
                    break;
                case "t", "T":
                    mStandardChess = false;
                    break;
                case "q", "Q":
                    endFlag = true;
                    break;
                default:
                    PrintToConsole.println("Please try again");
                    correctInput = false;
                    break;
            }
        }
    }

    public void startGame() {

        PrintToConsole.println("Type \"help\" for information on how to play. \n");

        ConsoleUI consoleUI = new ConsoleUI(replyQueue); // Player A
        Thread controllerThread;

        if (mIsNetworkGame && !mIsHost) {
            ClientController clientController = new ClientController(consoleUI, consoleUI);
            consoleUI.setGameOwner(clientController);
            controllerThread = new Thread(clientController);
        } else {
            Controller controller = new Controller(consoleUI, consoleUI);
            consoleUI.setGameOwner(controller);
            Player playerB = switch (mOpponent) {
                case HOTSEAT -> consoleUI;
                case AI_PLAYER -> new AiPlayer(controller);
                case NETWORK_PLAYER -> new NetworkPlayer(controller);
            };
            controller.setPlayerB(playerB);

            if (gameLog.isPresent()) {
                controller.loadGame(gameLog.get());
            } else {
                controller.newGame();
            }
            controllerThread = new Thread(controller);
        }

        Thread consoleUIThread = new Thread(consoleUI);

        consoleUIThread.start();
        controllerThread.start();
    }

    private enum Opponent {
        HOTSEAT, AI_PLAYER, NETWORK_PLAYER;
    }
}
