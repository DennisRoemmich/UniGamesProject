package gui;

import java.util.Optional;

import buildings.Building;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class BuildingNode extends Region {
	
	protected Optional<Building> building = Optional.empty();
	protected double tileWidth = 100;
	
	public static final double angle30degree = Math.PI / 6;
	
    public BuildingNode() {
        refreshOutput();
    }
    
    public BuildingNode(double width) {
        this.tileWidth = width;
        refreshOutput();
    }
    
    public BuildingNode(double width, Building building) {
        this.tileWidth = width;
        this.building = Optional.of(building);
        refreshOutput();
    }
    
    private void refreshOutput() {
    	
        if(building.isEmpty()) {
            return;
        }
        
        this.getChildren().clear();
        
        double width = tileWidth * 0.3;
        double height = tileWidth * 0.05;
        
        Circle circle = new Circle();
        circle.setRadius(9);
        
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
