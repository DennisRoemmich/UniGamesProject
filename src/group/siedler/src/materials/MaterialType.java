package materials;

import helper.ListUtility;
import java.util.Arrays;

/**
 * Getter methods to access the tiles, nodes and edges on the map. 
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public enum MaterialType {
    WOOD, CLAY, WHEAT, WOOL, ORE;

    public static MaterialType getRandom() {
        var types = Arrays.asList(MaterialType.values());
        return ListUtility.getRandomElement(types);
    }
}
