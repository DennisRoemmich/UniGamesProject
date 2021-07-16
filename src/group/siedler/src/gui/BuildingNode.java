package gui;

import java.util.Optional;


import buildings.Building;
import buildings.BuildingType;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import positions.NodePosition;

public class BuildingNode extends Region {

    protected Optional<Building> building = Optional.empty();
	protected NodePosition position;
	
    public BuildingNode(NodePosition position) {
        this.position = position;
        refreshOutput();
    }
    
    public BuildingNode(Building building) {
        this.building = Optional.of(building);
        this.position = building.getPosition();
        refreshOutput();
    }
    
    private void refreshOutput() {
    	
        if(building.isEmpty()) {
            this.getChildren().clear();
            Circle circle = new Circle();
            circle.setRadius(10);
            circle.setStroke(Color.BLACK);
            circle.setFill(Color.TRANSPARENT);
            this.getChildren().add(circle);
            return;
        }
        
        this.getChildren().clear();

        BuildingType type = building.get().getType();
        
        if(type == BuildingType.TOWN) {
            Polygon pentagon = new Polygon();
            pentagon.getPoints().addAll(0.0, -15.0,
                    10.0, -5.0,
                    5.0, 5.0,
                    -5.0, 5.0,
                    -10.0, -5.0);
            pentagon.setStroke(Color.BLACK);
            pentagon.setFill(building.get().getColor().getColor());
            this.getChildren().add(pentagon);
        } else {
            Circle circle = new Circle();
            circle.setRadius(7.5);
            circle.setStroke(Color.BLACK);
            circle.setFill(building.get().getColor().getColor());
            this.getChildren().add(circle);
        }
    }
}
