package gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import map.Map;
import map.MapTools;
import positions.TilePosition;
import siedlerController.Controller;
import siedlerFramework.PrintToConsole;
import tiles.NeutralTile;
import tiles.PositionedTile;
import tiles.Tile;

public class TileClickedEventHandler implements EventHandler {

    private TilePosition position;
    private SiedlerEventHandler eventHandler;

    public TileClickedEventHandler(TilePosition position, SiedlerEventHandler eventHandler) {
        this.position = position;
        this.eventHandler = eventHandler;
    }

    @Override
    public void handle(Event event) {
        eventHandler.handleTileCLick(position);
    }
}
