package core.map;

import core.positions.EdgePosition;
import core.positions.EdgePositionZCord;
import core.positions.NodePosition;
import core.positions.TilePosition;

public final class MapTools {

    // Tile -> Tiles
    // Bisher kein Verwendungszweck bekannt

    // Tile -> Nodes
    // Kann für Rohstoffverteilung benutzt werden
    public static NodePosition[] getNodePositions(TilePosition tilePosition) {
        int x = tilePosition.getX();
        int y = tilePosition.getY();
        NodePosition[] nodePositions = new NodePosition[6];
        nodePositions[0] = new NodePosition(x,y,true);
        nodePositions[1] = new NodePosition(x,y,false);
        nodePositions[2] = new NodePosition(x,y+1,false);
        nodePositions[3] = new NodePosition(x,y-1,true);
        nodePositions[4] = new NodePosition(x+1,y,true);
        nodePositions[5] = new NodePosition(x-1,y,false);
        return nodePositions;
    }

    // Tile -> Edges
    // Bisher kein Verwendungszweck bekannt

    // Node -> Tiles
    // Kann für Rohstoffverteilung benutzt werden
    public static TilePosition[] getTilesPositions(NodePosition nodePosition) {
        TilePosition[] tilePositions = new TilePosition[3];
        int x = nodePosition.getX();
        int y = nodePosition.getY();
        tilePositions[0] = new TilePosition(x,y);
        if(nodePosition.isZ()) {
            tilePositions[1] = new TilePosition(x-1,y);
            tilePositions[2] = new TilePosition(x,y-1);
        } else {
            tilePositions[1] = new TilePosition(x + 1, y);
            tilePositions[2] = new TilePosition(x, y + 1);
        }
        return tilePositions;
    }

    // Node -> Nodes
    // Notwendig für Bauregeln (keine Siedlung direkt neben der nächsten)

    // Node -> Edges
    // Notwendig für Bauregeln
    public static EdgePosition[] getEdgePositions(NodePosition nodePosition) {
        EdgePosition[] edgePositions = new EdgePosition[3];
        int x = nodePosition.getX();
        int y = nodePosition.getY();
        if(nodePosition.isZ()) {
            edgePositions[0] = new EdgePosition(x, y, EdgePositionZCord.C);
            edgePositions[1] = new EdgePosition(x, y-1, EdgePositionZCord.B);
            edgePositions[2] = new EdgePosition(x-1, y, EdgePositionZCord.A);
        } else {
            edgePositions[0] = new EdgePosition(x, y, EdgePositionZCord.B);
            edgePositions[1] = new EdgePosition(x, y+1, EdgePositionZCord.A);
            edgePositions[2] = new EdgePosition(x+1, y, EdgePositionZCord.C);
        }
        return edgePositions;
    }

    // Edge -> Tiles
    // Notwendig für Bauregeln (Schiff/Straße)

    // Edge -> Node
    // Notwendig für Bauregeln
    public static NodePosition[] getNodePositions(EdgePosition edgePosition) {
        NodePosition[] nodePositions = new NodePosition[2];
        int x = edgePosition.getX();
        int y = edgePosition.getY();
        switch (edgePosition.getZ()) {
            case A -> {
                nodePositions[0] = new NodePosition(x + 1, y, true);
                nodePositions[1] = new NodePosition(x, y - 1, false);
            }
            case B -> {
                nodePositions[0] = new NodePosition(x, y + 1, true);
                nodePositions[1] = new NodePosition(x, y, false);
            }
            case C -> {
                nodePositions[0] = new NodePosition(x, y, true);
                nodePositions[1] = new NodePosition(x - 1, y, false);
            }
        }
        return nodePositions;
    }

    // Edge -> Edges
    // Notwendig für Bauregeln & für längste Handelsstraße
    public static EdgePosition[] getEdgePositions(EdgePosition edgePosition) {
        EdgePosition[] edgePositions = new EdgePosition[4];
        int x = edgePosition.getX();
        int y = edgePosition.getY();
        switch (edgePosition.getZ()) {
            case A -> {
                edgePositions[0] = new EdgePosition(x+1,y-1,EdgePositionZCord.B);
                edgePositions[1] = new EdgePosition(x+1,y-1,EdgePositionZCord.C);
                edgePositions[2] = new EdgePosition(x,y-1,EdgePositionZCord.B);
                edgePositions[3] = new EdgePosition(x+1,y,EdgePositionZCord.C);
            }
            case B -> {
                edgePositions[0] = new EdgePosition(x,y+1,EdgePositionZCord.A);
                edgePositions[1] = new EdgePosition(x,y+1,EdgePositionZCord.C);
                edgePositions[2] = new EdgePosition(x-1,y+1,EdgePositionZCord.A);
                edgePositions[3] = new EdgePosition(x+1,y,EdgePositionZCord.C);
            }
            case C -> {
                edgePositions[0] = new EdgePosition(x-1,y,EdgePositionZCord.A);
                edgePositions[1] = new EdgePosition(x-1,y,EdgePositionZCord.B);
                edgePositions[2] = new EdgePosition(x-1,y+1,EdgePositionZCord.A);
                edgePositions[3] = new EdgePosition(x,y-1,EdgePositionZCord.B);
            }
        }
        return edgePositions;
    }

}
