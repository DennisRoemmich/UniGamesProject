package gui;

import positions.EdgePosition;
import positions.NodePosition;
import positions.TilePosition;

public interface SiedlerEventHandler {
    void handleTileCLick(TilePosition position);
    
    void handleStreetClick(EdgePosition position);
    
    void handleBuildingClick(NodePosition position);
}
