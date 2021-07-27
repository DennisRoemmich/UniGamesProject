package map;

import positions.*;

public final class MapTools {
	
	private MapTools() {
		//Unused
	}

    // Tile -> Nodes
    // Kann für Rohstoffverteilung benutzt werden (edit: wird es auch)
    public static NodePosition[] getNodePositions(TilePosition tilePosition) {
        int x = tilePosition.getX();
        int y = tilePosition.getY();
        NodePosition[] nodePositions = new NodePosition[6];
        nodePositions[0] = new NodePosition(x, y, NodePositionZCord.A);
        nodePositions[1] = new NodePosition(x, y, NodePositionZCord.B);
        nodePositions[2] = new NodePosition(x, y + 1, NodePositionZCord.A);
        nodePositions[3] = new NodePosition(x, y - 1, NodePositionZCord.B);
        nodePositions[4] = new NodePosition(x + 1, y, NodePositionZCord.A);
        nodePositions[5] = new NodePosition(x - 1, y, NodePositionZCord.B);
        return nodePositions;
    }
    
    // Node -> Nodes (vollständig getestet)
    // Notwendig für Bauregeln (keine Siedlung direkt neben der nächsten)
    public static NodePosition[] getNodePositions(NodePosition nodePosition) {
        NodePosition[] nodePos = new NodePosition[3];
        int step = nodePosition.isZ() ? -1 : 1;
        nodePos[0] = new NodePosition(nodePosition.getX() + step, nodePosition.getY(), !nodePosition.isZ());
        nodePos[1] = new NodePosition(nodePosition.getX(), nodePosition.getY() + step, !nodePosition.isZ());
        nodePos[2] = new NodePosition(nodePosition.getX() + step, nodePosition.getY() + step, !nodePosition.isZ());
        return nodePos;
    }

    // Edge -> Node (vollständig getestet)
    // Notwendig für Bauregeln
    public static NodePosition[] getNodePositions(EdgePosition edgePosition) {
        NodePosition[] nodePositions = new NodePosition[2];
        int x = edgePosition.getX();
        int y = edgePosition.getY();
        switch (edgePosition.getZ()) {
            case A:
                nodePositions[0] = new NodePosition(x + 1, y, true);
                nodePositions[1] = new NodePosition(x, y - 1, false);
                break;
            case B:
                nodePositions[0] = new NodePosition(x, y + 1, true);
                nodePositions[1] = new NodePosition(x, y, false);
                break;
            case C:
                nodePositions[0] = new NodePosition(x, y, true);
                nodePositions[1] = new NodePosition(x - 1, y, false);
                break;
        }
        return nodePositions;
    }

    // Tile -> Edges
    // Für Bauregeln: Schiff vs Straße
    public static TilePosition[] getTilesPositions(EdgePosition position) {
        TilePosition[] tilePositions = new TilePosition[2];
        int x = position.getX();
        int y = position.getY();

        tilePositions[0] = new TilePosition(x, y);
        tilePositions[1] = switch (position.getZ()) {
            case A -> new TilePosition(x + 1, y - 1);
            case B -> new TilePosition(x, y + 1);
            case C -> new TilePosition(x - 1, y);
        };
        return tilePositions;
    }

    // Node -> Tiles
    // Kann für Rohstoffverteilung benutzt werden (edit: wird es nicht)
    public static TilePosition[] getTilesPositions(NodePosition nodePosition) {
        TilePosition[] tilePositions = new TilePosition[3];
        int x = nodePosition.getX();
        int y = nodePosition.getY();
        tilePositions[0] = new TilePosition(x, y);
        if (nodePosition.isZ()) {
            tilePositions[1] = new TilePosition(x - 1, y);
            tilePositions[2] = new TilePosition(x, y - 1);
        } else {
            tilePositions[1] = new TilePosition(x + 1, y);
            tilePositions[2] = new TilePosition(x, y + 1);
        }
        return tilePositions;
    }



    // Node -> Edges (vollständig getestet)
    // Notwendig für Bauregeln
    public static EdgePosition[] getEdgePositions(NodePosition nodePosition) {
        EdgePosition[] edgePositions = new EdgePosition[3];
        int x = nodePosition.getX();
        int y = nodePosition.getY();
        if (nodePosition.isZ()) {
            edgePositions[0] = new EdgePosition(x, y, EdgePositionZCord.C);
            edgePositions[1] = new EdgePosition(x, y - 1, EdgePositionZCord.B);
            edgePositions[2] = new EdgePosition(x - 1, y, EdgePositionZCord.A);
        } else {
            edgePositions[0] = new EdgePosition(x, y, EdgePositionZCord.B);
            edgePositions[1] = new EdgePosition(x, y + 1, EdgePositionZCord.A);
            edgePositions[2] = new EdgePosition(x + 1, y, EdgePositionZCord.C);
        }
        return edgePositions;
    }



    // Edge -> Edges (vollständig getestet)
    // Notwendig für Bauregeln & für längste Handelsstraße
    public static EdgePosition[] getEdgePositions(EdgePosition edgePosition) {
        EdgePosition[] edgePositions = new EdgePosition[4];
        int x = edgePosition.getX();
        int y = edgePosition.getY();
        switch (edgePosition.getZ()) {
            case A:
                edgePositions[0] = new EdgePosition(x + 1, y - 1, EdgePositionZCord.B);
                edgePositions[1] = new EdgePosition(x + 1, y - 1, EdgePositionZCord.C);
                edgePositions[2] = new EdgePosition(x, y - 1, EdgePositionZCord.B);
                edgePositions[3] = new EdgePosition(x + 1, y, EdgePositionZCord.C);
                break;
            case B: 
                edgePositions[0] = new EdgePosition(x, y + 1, EdgePositionZCord.A);
                edgePositions[1] = new EdgePosition(x, y + 1, EdgePositionZCord.C);
                edgePositions[2] = new EdgePosition(x - 1, y + 1, EdgePositionZCord.A);
                edgePositions[3] = new EdgePosition(x + 1, y, EdgePositionZCord.C);
                break;
            case C:
                edgePositions[0] = new EdgePosition(x - 1, y, EdgePositionZCord.A);
                edgePositions[1] = new EdgePosition(x - 1, y, EdgePositionZCord.B);
                edgePositions[2] = new EdgePosition(x - 1, y + 1, EdgePositionZCord.A);
                edgePositions[3] = new EdgePosition(x, y - 1, EdgePositionZCord.B);
                break;
            }   
        return edgePositions;
    }

    /*
    Checks if an edgePosition lays on a map (or if its ot of bound).
     */
    public static boolean isPositionValid(Map map, EdgePosition position) {
        var neighbourTiles = getTilesPositions(position);
        for (TilePosition tilePosition : neighbourTiles) {
            if (map.getTiles().stream().anyMatch(t -> t.getPosition().equals(tilePosition))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPositionValid(Map map, NodePosition position) {
        var neighbourTiles = getTilesPositions(position);
        for (TilePosition tilePosition : neighbourTiles) {
            if (map.getTiles().stream().anyMatch(t -> t.getPosition().equals(tilePosition))) {
                return true;
            }
        }
        return false;
    }
}
