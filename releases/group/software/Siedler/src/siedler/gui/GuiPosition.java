package siedler.gui;

import positions.EdgePosition;
import positions.EdgePositionZCord;
import positions.NodePosition;
import positions.TilePosition;

/**
 * Places the tiles and its edge and nodepositions on the screen.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class GuiPosition {
    private double mValueX;
    private double mValueY;

    public GuiPosition(double x, double y) {
        this.mValueX = x;
        this.mValueY = y;
    }

    public double getX() {
        return mValueX;
    }

    public void setX(double x) {
        this.mValueX = x;
    }

    public double getY() {
        return mValueY;
    }

    public void setY(double y) {
        this.mValueY = y;
    }

    public static GuiPosition valueOf(TilePosition position) {
        double x = (position.getX() * 2 + position.getY()) * 35.0;
        double y = position.getY() * 60.0;
        return new GuiPosition(x, y);
    }

    public static GuiPosition valueOf(EdgePosition position) {
        double x;
        double y;
        if (position.getZ() == EdgePositionZCord.A) {
            x = 38.0 + (position.getX() * 2 + position.getY()) * 35;
            y = 8.0 + position.getY() * 60;
        } else if (position.getZ() == EdgePositionZCord.B) {
            x = 38.0 + (position.getX() * 2 + position.getY()) * 35;
            y = 68.0 + position.getY() * 60;
        } else if (position.getZ() == EdgePositionZCord.C) {
            x = -14.0 + (position.getX() * 2 + position.getY()) * 35;
            y = 38.0 + position.getY() * 60;
        } else {
            x = 0;
            y = 0;
        }
        return new GuiPosition(x, y);
    }

    public static GuiPosition valueOf(NodePosition position) {
        double x;
        double y;
        if (position.isZ()) {
            x = 1.0 + (position.getX() * 2 + position.getY()) * 35;
            y = 20.0 + position.getY() * 60;
        } else {
            x = 71.0 + (position.getX() * 2 + position.getY()) * 35;
            y = 62.0 + position.getY() * 60;
        }
        return new GuiPosition(x, y);
    }
}
