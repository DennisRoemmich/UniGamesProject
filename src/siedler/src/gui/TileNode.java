package gui;


import javafx.scene.image.ImageView;
import map.Tile;

import java.util.Optional;

public class TileNode extends ImageView {

    protected Optional<Tile> tile = Optional.empty();

}
