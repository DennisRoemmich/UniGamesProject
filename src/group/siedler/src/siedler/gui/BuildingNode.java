package siedler.gui;

import java.util.Optional;
import buildings.Building;
import buildings.BuildingType;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import positions.NodePosition;

/**
 * Represents a node on a tile where a building could be placed.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class BuildingNode extends Group {

    protected Optional<Building> mBuilding = Optional.empty();
	protected NodePosition mPosition;
	
    public BuildingNode(NodePosition position) {
        this.mPosition = position;
        refreshOutput();
    }
    
    public BuildingNode(Building building) {
        this.mBuilding = Optional.of(building);
        this.mPosition = building.getPosition();
        refreshOutput();
    }
    
    private void refreshOutput() {
    	
        if (mBuilding.isEmpty()) {
            this.getChildren().clear();
            Circle circle = new Circle();
            circle.setRadius(10);
            circle.setStroke(Color.BLACK);
            circle.setFill(Color.TRANSPARENT);
            this.getChildren().add(circle);
            return;
        }
        
        this.getChildren().clear();

        BuildingType type = mBuilding.get().getType();
        
        if (type == BuildingType.TOWN) {
            Polygon pentagon = new Polygon();
            pentagon.getPoints().addAll(0.0, -15.0,
                    10.0, -5.0,
                    5.0, 5.0,
                    -5.0, 5.0,
                    -10.0, -5.0);
            pentagon.setStroke(Color.BLACK);
            pentagon.setFill(mBuilding.get().getColor().getColor());
            this.getChildren().add(pentagon);
        } else {
            Circle circle = new Circle();
            circle.setRadius(7.5);
            circle.setStroke(Color.BLACK);
            circle.setFill(mBuilding.get().getColor().getColor());
            this.getChildren().add(circle);
        }
    }
}
