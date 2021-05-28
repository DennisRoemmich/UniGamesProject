package rummikub_game;

import java.awt.*;

public class Rack {

    static final int GRIDHEIGHT = 2;
    static final int GRIDWIDTH = 15;

    private final GridTile[][] grid;
    private int size;

    public Rack() {

        this.grid = new GridTile[GRIDWIDTH][GRIDHEIGHT];
        for (int i = 0; i < GRIDHEIGHT * GRIDWIDTH; i++) {

            this.grid[i / GRIDWIDTH][i % GRIDWIDTH].setPosition(i);
        }

        this.size = 0;
    }

    public int getSize() {

        return this.size;
    }

    public boolean isEmpty() {

        return this.size == 0;
    }

    public int getSum(){

        int sum = 0;

        for (int i = 0; i < GRIDHEIGHT * GRIDWIDTH; i++) {

            sum = sum + this.grid[i / GRIDWIDTH][i % GRIDWIDTH].getTile().getValue();
        }
        return sum;
    }

    public GridTile[][] getGrid() {

        return this.grid;
    }

    public int getPosition(Point point) {

        return point.x + point.y;
    }

    public GridTile pointToGridTile(Point point) {

        return this.grid[point.x][point.y];
    }

    public Point gridTileToPoint(GridTile gridTile) {

        return new Point(gridTile.getPosition() / GRIDWIDTH, gridTile.getPosition() % GRIDWIDTH);
    }

    public void addTile(Tile tile) {

        for (int i = 0; i < GRIDHEIGHT * GRIDWIDTH; i++) {

            if (this.grid[i / GRIDWIDTH][i % GRIDWIDTH].isEmpty()) {

                this.grid[i / GRIDWIDTH][i % GRIDWIDTH].setTile(tile);
                this.size++;

                return;
            }
        }
    }

    public void addTiles(Tile[] tiles) {

        for (Tile tile : tiles) {

            this.addTile(tile);
        }
    }

    public void removeTile(Point pos) {

        this.pointToGridTile(pos).removeTile();
    }

    public boolean moveTile(Point from, Point to) {

        GridTile toMove = this.pointToGridTile(from);
        GridTile target = this.pointToGridTile(to);

        if (!toMove.isEmpty()) {

            if (!target.isEmpty()) {

                if (this.getFirstEmpty(to) != null) {

                    GridTile gridTile = this.getFirstEmpty(to);
                    GridTile nextGridTile;

                    while (gridTile != target) {

                        nextGridTile = this.getPrevious(this.gridTileToPoint(gridTile));

                        gridTile.setTile(nextGridTile.getTile());
                        gridTile = this.getPrevious(this.gridTileToPoint(gridTile));
                    }
                } else {

                    Tile help = target.getTile();

                    target.setTile(toMove.getTile());
                    toMove.setTile(help);

                    return true;
                }
            }
            target.setTile(toMove.getTile());
            toMove.removeTile();

            return true;
        }
        return false;
    }

    private GridTile getNext(Point gridTile) {

        int pos = gridTile.x + gridTile.y;

        return this.grid[(pos + 1) / GRIDWIDTH][(pos + 1) % GRIDWIDTH];
    }

    private GridTile getPrevious(Point gridTile) {

        int pos = gridTile.x + gridTile.y;

        return this.grid[(pos - 1) / GRIDWIDTH][(pos - 1) % GRIDWIDTH];
    }

    private GridTile getFirstEmpty(Point start) {

        int pos = getPosition(start);

        for (int i = pos + 1; i < GRIDHEIGHT * GRIDWIDTH; i++) {

            if (this.grid[i / GRIDWIDTH][i % GRIDWIDTH].isEmpty()) {

                return this.grid[i / GRIDWIDTH][i % GRIDWIDTH];
            }
        }
        return null;
    }

    public void sortForGroup() {


    }

    public void sortForRun() {


    }
}
