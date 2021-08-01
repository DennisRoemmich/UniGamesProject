package gui;

import positions.EdgePosition;
import positions.NodePosition;
import positions.TilePosition;

/**
 * Interface of the three EventHandlers not implemented as FXML but raw JavaFX
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public interface SiedlerEventHandler {
    void handleTileCLick(TilePosition position);
    
    void handleStreetClick(EdgePosition position);
    
    void handleBuildingClick(NodePosition position);
}
