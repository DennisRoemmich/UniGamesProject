
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

public class Cell extends Pane{
	
	private char player = ' ';
	
	//constructor
	public Cell() {
		
		setStyle("-fx-border-color : black");
		this.setPrefSize(300, 300);
	}
	
	//gets player
	public char getPlayer() {
		return this.player;
	}
	
	//sets player
	public void setPlayer(char p, boolean r) {
		this.player = p;
		
		Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
		Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10,10);
		
		
		//draws an X in the rectangle
		if(player == 'X' && !r) {
			
			line1.endXProperty().bind(this.widthProperty().subtract(10));
			line1.endYProperty().bind(this.heightProperty().subtract(10));

			line2.endXProperty().bind(this.widthProperty().subtract(10));
			line2.startYProperty().bind(this.heightProperty().subtract(10));
			
			getChildren().addAll(line1, line2);
		
		//draws O in the rectangle
		}else if (player == 'O' && !r) {
			Ellipse ellipse = new Ellipse(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2 - 10,
					this.getHeight()/2 - 10);

			ellipse.centerXProperty().bind(this.widthProperty().divide(2));
			ellipse.centerYProperty().bind(this.heightProperty().divide(2));
			ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
			ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
			ellipse.setFill(null);
			ellipse.setStroke(Color.BLACK);
			
			getChildren().add(ellipse);
		
		//draws an X of the same color as the background on top of existing X
		} else if (player == 'X' && r) {
			line1.setStroke(Color.rgb(244,244,244));
			line2.setStroke(Color.rgb(244,244,244));
			
			line1.endXProperty().bind(this.widthProperty().subtract(10));
			line1.endYProperty().bind(this.heightProperty().subtract(10));
			line1.setStrokeWidth(5);

			line2.endXProperty().bind(this.widthProperty().subtract(10));
			line2.startYProperty().bind(this.heightProperty().subtract(10));
			line2.setStrokeWidth(5);
			
			getChildren().addAll(line1, line2);
			
			this.player = ' ';
			
			//draws an O of the same color as the background on top of existing O
			
		} else if (player == 'O' && r) {
			
			Ellipse ellipse = new Ellipse(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2 - 10,
					this.getHeight()/2 - 10);
			ellipse.centerXProperty().bind(this.widthProperty().divide(2));
			ellipse.centerYProperty().bind(this.heightProperty().divide(2));
			ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
			ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
			ellipse.setFill(null);
			ellipse.setStroke(Color.rgb(244,244,244));
			ellipse.setStrokeWidth(5);
			getChildren().add(ellipse);

			
			this.player = ' ';
		}
	}
}
