package map;

import buildings.Building;
import buildings.BuildingType;
import helper.ListUtility;
import player.PlayerColor;
import positions.EdgePosition;
import positions.NodePosition;
import positions.PositionedObject;
import positions.TilePosition;
import streets.PositionedStreet;
import streets.StreetType;
import tiles.Tile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The game rules by which the players are allowed to build their streets and buildings
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public final class BuildRules {
	
	private BuildRules() {
		//Unused
	}
	
    public static boolean isValid(Map map, PlayerColor color, NodePosition position, BuildingType type) {
        return getValidNodePositions(map, color, type).contains(position);
    }

    public static List<NodePosition> getValidNodePositions(Map map, PlayerColor color, BuildingType buildingType) {
        List<NodePosition> validPositions = new ArrayList<>();
        var playersStreets = map.getStreets(color);
        for (PositionedStreet street : playersStreets) {
            var filtered = filterNodePositions(map, color, buildingType, street.getPosition());
            ListUtility.addAllWithoutDuplicates(filtered, validPositions);
        }
        return validPositions;
    }

    public static List<NodePosition> filterNodePositions(Map map, PlayerColor color, BuildingType type, EdgePosition streetPosition) {

        var nodes = MapTools.getNodePositions(streetPosition);
        List<NodePosition> filtered = new ArrayList<>();
        for(NodePosition nodePosition : nodes) {
            if (type == BuildingType.VILLAGE) {
                if (isNodeValidForNewBuilding(map, nodePosition)) {
                    filtered.add(nodePosition);
                }
            } else {
                if (canBeUpgradedToTown(map, color, nodePosition)) {
                    filtered.add(nodePosition);
                }
            }
        }
        return filtered;
    }

    public static List<StreetType> getPossibleStreetType(Map map, EdgePosition position) {
        List<StreetType> types = new ArrayList<>();
        TilePosition[] tilePositions = MapTools.getTilesPositions(position);
        for(TilePosition tilePosition : tilePositions) {
            var tile = map.getTile(tilePosition);
            if(tile.isPresent()) {
                var streetType = getPossibleStreetType(tile.get());
                if (!types.contains(streetType)) {
                    types.add(streetType);
                }
            }
        }

        return types;
    }

    public static StreetType getPossibleStreetType(Tile tile) {
	    return tile.isWater() ? StreetType.SHIP : StreetType.ROAD;
    }

    public static List<EdgePosition> getValidEdgePositions(Map map, PlayerColor color, StreetType streetType) {
        var playersStreets = map.getStreets(color).stream().filter(s -> s.getObject().getType().equals(streetType)).toList();
        var playersBuildings = map.getBuildings(color);

        List<EdgePosition> possiblePositions = new ArrayList<>();

        // Get EdgePositions next to buildings
        for (PositionedStreet street : playersStreets) {
            if (street.getObject().getType() == streetType) {
                var neighbourStreets = MapTools.getEdgePositions(street.getPosition());
                ListUtility.addAllWithoutDuplicates(Arrays.stream(neighbourStreets).toList(), possiblePositions);
            }
        }

        // Get EdgePositions next to streets
        for (Building building : playersBuildings) {
            var neighbourStreets = MapTools.getEdgePositions(building.getPosition());
            ListUtility.addAllWithoutDuplicates(Arrays.asList(neighbourStreets), possiblePositions);
        }

        List<EdgePosition> validPositions = new ArrayList<>();
        for (EdgePosition position : possiblePositions) {
            if(isNodeValidForNewStreet(map, position, streetType)) {
                validPositions.add(position);
            }
        }

        return validPositions;
    }

    public static boolean isNodeValidForNewStreet(Map map, EdgePosition position, StreetType type) {
	    if (MapTools.isPositionValid(map, position) && map.getStreet(position).isEmpty()) {
	            return getPossibleStreetType(map, position).contains(type);
        }
	    return false;
    }

    public static boolean isNodeValidForNewBuilding(Map map, NodePosition position) {
        return map.getBuilding(position).isEmpty() && areNeighbourNodesFree(map, position) && !isNodeInWater(map, position);
    }

    public static boolean areNeighbourNodesFree(Map map, NodePosition position) {
        return Arrays.stream(MapTools.getNodePositions(position)).noneMatch(np -> map.getBuilding(np).isPresent());
    }

    public static boolean isNodeInWater(Map map, NodePosition position) {
        return Arrays.stream(MapTools.getTilesPositions(position)).noneMatch(tp -> isTileLand(map, tp));
    }

    public static boolean isTileLand(Map map, TilePosition position) {
        Optional<Tile> tile = map.getTile(position);
        if (tile.isPresent()) {
            return !tile.get().isWater();
        }
        return false;
    }

    public static boolean canBeUpgradedToTown(Map map, PlayerColor color,  NodePosition position) {
        var m = map.getBuilding(position);
    	if (m.isPresent()) {
            Building building = m.get();
            return building.getColor() == color && building.getType() == BuildingType.VILLAGE;
        } else {
            return false;
        }
    }

    public static List<NodePosition> getStartNodePositions(Map map) {
        List<TilePosition> tilePositions = map.getTiles().stream().map(PositionedObject::getPosition).toList();
        List<NodePosition> nodePositions = new ArrayList<>();
        for (TilePosition position : tilePositions) {
            ListUtility.addAllWithoutDuplicates(Arrays.stream(MapTools.getNodePositions(position)).toList(), nodePositions);
        }
        nodePositions = nodePositions.stream().filter(nP -> BuildRules.isNodeValidForNewBuilding(map, nP)).toList();
        return nodePositions;
    }

    public static List<EdgePosition> getStartEdgePositions(Map map, PlayerColor color) {
        List<EdgePosition> edgePositions = getStartEdgePositions(map, color, StreetType.ROAD);
        ListUtility.addAllWithoutDuplicates(edgePositions, getStartEdgePositions(map, color, StreetType.SHIP));
        return edgePositions;
    }

    public static List<EdgePosition> getStartEdgePositions(Map map, PlayerColor color, StreetType type) {
        List<EdgePosition> edgePositions;
        var nodePositions = new ArrayList<>(map.getBuildings(color).stream().map(Building::getPosition).toList());
        nodePositions = new ArrayList<>(nodePositions.stream().filter(nP -> Arrays.stream(MapTools.getEdgePositions(nP)).noneMatch(eP -> map.getStreet(eP).isPresent())).toList());
        edgePositions = Arrays.stream(MapTools.getEdgePositions(nodePositions.get(0))).toList();
        edgePositions = new ArrayList<>(edgePositions.stream().filter(eP -> MapTools.isPositionValid(map, eP) && BuildRules.getPossibleStreetType(map, eP).contains(type)).toList());
        return edgePositions;
    }
}
