package gui;

import org.w3c.dom.Node;
import positions.EdgePosition;
import positions.NodePosition;
import positions.TilePosition;

public interface SiedlerEventHandler {
    void handleTileCLick(TilePosition position);
    void handleStreetClick(EdgePosition position);
    void handleBuildingClick(NodePosition position);
}
