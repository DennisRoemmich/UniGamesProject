package gui;

import java.util.Optional;

import buildings.Building;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
            return;
        }
        
        this.getChildren().clear();
        
        double width = 30;
        double height = 5;
        
        Circle circle = new Circle();
        circle.setRadius(7.5);
        circle.setStroke(Color.BLACK);
        
        this.getChildren().add(circle);
        circle.setStroke(Color.BLACK);

        switch (building.get().getColor()) {
            case BLUE -> {
            	circle.setFill(Color.BLUE);
            }
            case GREEN -> {
            	circle.setFill(Color.GREEN);
            }
            case YELLOW -> {
            	circle.setFill(Color.YELLOW);
            }
            case RED -> {
            	circle.setFill(Color.RED);
            }
            case PURPLE -> {
            	circle.setFill(Color.PURPLE);
            }
            case BLACK -> {
            	circle.setFill(Color.BLACK);
            }
            case WHITE -> {
            	circle.setFill(Color.WHITE);
            }
        }
    }
}
