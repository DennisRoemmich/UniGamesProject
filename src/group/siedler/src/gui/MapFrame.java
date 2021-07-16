package gui;


import javafx.scene.layout.Region;
import map.Map;

public class MapFrame extends Region {
    private MapNode mapNode;
    private double relativeWidth = 100;

    public MapFrame(MapNode mapNode) {
        this.mapNode = mapNode;
        getChildren().add(mapNode);
    }

    public MapFrame(Map map) {
        this.mapNode = new MapNode(map);
        getChildren().add(mapNode);
    }

    public MapNode getMapNode() {
        return mapNode;
    }

    public void setMapNode(MapNode mapNode) {
        this.mapNode = mapNode;
    }

    public double getRelativeWidth() {
        return relativeWidth;
    }

    public void setRelativeWidth(double relativeWidth) {
        this.relativeWidth = relativeWidth;
        mapNode.setScaleX(relativeWidth / 100);
        mapNode.setScaleY(relativeWidth / 100);
        mapNode.setScaleZ(relativeWidth / 100);
    }

}
