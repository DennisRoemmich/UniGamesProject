package core;

import core.map.NeutralTile;
import core.map.ResourceTile;
import core.positions.TilePosition;
import core.rohstoffe.ResourceType;
import gui.MapNode;
import gui.TileNode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import core.map.Map;
import core.map.MapGenerator;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();
        MapNode node = new MapNode();
        //ResourceTile node = new ResourceTile(new TilePosition(0,0), ResourceType.WOOL, 8);
        //TileNode tileNode = new TileNode(250, woodTile);
        pane.getChildren().add(node);
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(pane, 700,500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        //SiedlerMap core.map = SiedlerMap.getDesertMap();
        //System.out.println(core.map.toString());
        Map map = MapGenerator.generateBasicMap();
    }

}
