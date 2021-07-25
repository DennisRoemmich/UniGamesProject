package gui;

import positions.EdgePosition;
import positions.NodePosition;
import positions.TilePosition;

public class GuiPosition {
    private double x;
    private double y;

    public GuiPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public static GuiPosition valueOf(TilePosition position) {
        double x = (position.getX() * 2 + position.getY()) * 35;
        double y = position.getY() * 60;
        return new GuiPosition(x,y);
    }

    public static GuiPosition valueOf(EdgePosition position) {
        double x, y;
        switch(position.getZ()) {
            case A -> {
                x = 38 + (position.getX() * 2 + position.getY()) * 35;
                y = 8 + position.getY() * 60;
            }
            case B -> {
                x = 38 + (position.getX() * 2 + position.getY()) * 35;
                y = 68 + position.getY() * 60;
            }
            case C -> {
                x = -14 +(position.getX() * 2 + position.getY()) * 35;
                y = 38 + position.getY() * 60;
            }
            default -> {
                x = 0;
                y = 0;
            }
        }
        return new GuiPosition(x,y);
    }

    public static GuiPosition valueOf(NodePosition position) {
        double x, y;
        if(position.isZ()) {
            x = 1 + (position.getX() * 2 + position.getY()) * 35;
            y = 20 + position.getY() * 60;
        } else {
            x = 71 + (position.getX() * 2 + position.getY()) * 35;
            y = 62 + position.getY() * 60;
        }
        return new GuiPosition(x,y);
    }
}
