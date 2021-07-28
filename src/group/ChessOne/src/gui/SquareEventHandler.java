package gui;

import framework.PrintToConsole;
import engine.squares.Square;
import javafx.event.Event;
import javafx.event.EventHandler;

public class SquareEventHandler implements EventHandler {
    private GuiEventHandler eventHandler;
    private Square square;

    public SquareEventHandler(GuiEventHandler eventHandler, Square square) {
        this.eventHandler = eventHandler;
        this.square = square;
    }

    @Override
    public void handle(Event event) {
        PrintToConsole.println(square + " clicked!");
        eventHandler.handleSquareClicked(square);
    }

    public GuiEventHandler getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(GuiEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }
}
