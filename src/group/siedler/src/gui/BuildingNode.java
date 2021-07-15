package gui;

import java.util.Optional;


import buildings.Building;
import buildings.BuildingType;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
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
        
        Circle circle = new Circle();
        circle.setRadius(7.5);
        circle.setStroke(Color.BLACK);
        
        Polygon pentagon = new Polygon();
        
        pentagon.getPoints().addAll(new Double[]{         
        		   0.0, -15.0,
        		   10.0, -5.0,
        		   5.0, 5.0, 
        		   -5.0, 5.0,
        		   -10.0, -5.0,  
        		});
        
        this.getChildren().add(circle);
        
        BuildingType type = building.get().getType();
        
        if(type == BuildingType.TOWN) {
            this.getChildren().remove(circle);
            this.getChildren().add(pentagon);
        }
        pentagon.setStroke(Color.BLACK);
        
        circle.setStroke(Color.BLACK);

        switch (building.get().getColor()) {
            case BLUE -> {
            	circle.setFill(Color.BLUE);
            	pentagon.setFill(Color.BLUE);
            }
            case GREEN -> {
            	circle.setFill(Color.GREEN);
            	pentagon.setFill(Color.GREEN);
            }
            case YELLOW -> {
            	circle.setFill(Color.YELLOW);
            	pentagon.setFill(Color.YELLOW);
            }
            case RED -> {
            	circle.setFill(Color.RED);
            	pentagon.setFill(Color.RED);
            }
            case PURPLE -> {
            	circle.setFill(Color.PURPLE);
            	pentagon.setFill(Color.PURPLE);
            }
            case BLACK -> {
            	circle.setFill(Color.BLACK);
            	pentagon.setFill(Color.BLACK);
            }
            case WHITE -> {
            	circle.setFill(Color.WHITE);
            	pentagon.setFill(Color.WHITE);
            }
        }
    }
}
