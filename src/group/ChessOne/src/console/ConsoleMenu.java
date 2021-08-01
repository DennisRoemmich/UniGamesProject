package console;

import engine.Controller;
import framework.*;
import network.ClientController;
import network.ConsoleNetworkClientIO;
import network.NetworkPlayer;
import npc.AiPlayer;
import org.json.simple.JSONObject;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Handles the menu system of the console
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class ConsoleMenu {

    private boolean mIsNetworkGame;
    private boolean mIsHost;
    private Opponent mOpponent;
    private Optional<GameLog> mGameLog = Optional.empty();
    private boolean mStandardChess;

    private Scanner mScanner = new Scanner(System.in);
    private boolean mEndFlag = false;

    private BlockingQueue<String> mRequestQueue = new LinkedBlockingQueue<>();

    public void run() {
        PrintToConsole.println("Welcome to Chess!");

        askNetworkOrLocal();
        if (mEndFlag) {
            return;
        }
        if (mIsNetworkGame) {
            askHostOrClient();
        } else {
            askOpponent();
        }
        if (mEndFlag) {
            return;
        }
        if (!mIsNetworkGame) {
            askLoadGame();
            if (mGameLog.isEmpty()) {
                askGameMode();
            }
        }
        if (mEndFlag) {
            return;
        }

        startGame();
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
                    mEndFlag = true;
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
                    mEndFlag = true;
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
                    mEndFlag = true;
                    break;
                default:
                    PrintToConsole.println("Please try it again");
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
                    mGameLog = askGameLog();
                    break;
                case "n", "N":
                    mGameLog = Optional.empty();
                    break;
                case "q", "Q":
                    mEndFlag = true;
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
            String logName = mScanner.nextLine();
            if (!"a".equalsIgnoreCase(logName)) {
                try {
                    JSONObject gameLogJSon = FileController.loadJSon(logName);
                    return Optional.of(GameLog.valueOf(gameLogJSon));
                } catch (Exception e) {
                    PrintToConsole.println("File not found. Please try again.");
                    correctInput = false;
                }
            } else {
                correctInput = true;
            }
        }
        return Optional.empty();
    }


    public void askGameMode() {
        boolean correctInput = false;
        while (!correctInput) {

            PrintToConsole.println("Please choose either [C]lassical Chess or [T]orpedo Chess");
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
                    mEndFlag = true;
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

        Optional<Controller> controller = Optional.empty();
        ConsoleUI consoleUI = new ConsoleUI(mRequestQueue); // Player A
        Thread controllerThread;

        if (mIsNetworkGame && !mIsHost) {
            ClientController clientController = new ClientController(new ConsoleNetworkClientIO(), consoleUI, consoleUI);
            consoleUI.setGameOwner(clientController);
            controllerThread = new Thread(clientController);
            clientController.setupConnection();
        } else {
            controller = Optional.of(new Controller(consoleUI, consoleUI));
            consoleUI.setGameOwner(controller.get());
            String gameModeName = mStandardChess ? "Classical" : "Torpedo";

            Player playerB = switch (mOpponent) {
                case HOTSEAT -> consoleUI;
                case AI_PLAYER -> new AiPlayer(controller.get(), true);
                case NETWORK_PLAYER -> new NetworkPlayer(controller.get(), gameModeName);
            };
            controller.get().setPlayerB(playerB);

            if (mGameLog.isPresent()) {
                controller.get().replayLog(mGameLog.get());
            } else {
                controller.get().setGameMode(mStandardChess);
                controller.get().newGame();
            }
            controllerThread = new Thread(controller.get());
        }

        Thread consoleUiThread = new Thread(consoleUI);

        consoleUiThread.start();
        controllerThread.start();

        gameLoop(controller);

        consoleUI.requestMove(QuickJSon.create("type", "quit"));
        consoleUI.getGameOwner().addMoveToQueue(QuickJSon.create("quit", "quit"));
        PrintToConsole.println("See you soon!");

        try {
            consoleUiThread.join();
            controllerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void gameLoop(Optional<Controller> controller) {
        boolean runningFlag = true;
        while (runningFlag) {
            while (mRequestQueue.peek() == null) {
                // Waiting for request
            }
            String message = mRequestQueue.poll();
            switch (message) {
                case "quit":
                    runningFlag = false;
                    break;
                case "undo":
                    if (controller.isPresent() && !mIsNetworkGame) {
                        int amount = mOpponent == Opponent.AI_PLAYER ? 2 : 1;
                        controller.get().addMoveToQueue(QuickJSon.create("undo", amount));
                    }
                    break;
                default:
                    WriteError.writeErrorLog("Unknown message received.");
            }
        }
    }

    private enum Opponent {
        HOTSEAT, AI_PLAYER, NETWORK_PLAYER;
    }
}
