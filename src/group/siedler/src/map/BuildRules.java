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
            var nodes = MapTools.getNodePositions(street.getPosition());
            var filtered = Arrays.stream(nodes).filter(p -> MapTools.isPositionValid(map, p)).toList();
            if (buildingType == BuildingType.VILLAGE) {
                filtered = filtered.stream().filter(nodePosition -> map.getBuilding(nodePosition).isEmpty()).toList();
                filtered = filtered.stream().filter(nodePosition -> isNodeValidForNewBuilding(map, nodePosition)).toList();
            } else {
                filtered = filtered.stream().filter(nodePosition -> canBeUpgradedToTown(map, color, nodePosition)).toList();
            }
            ListUtility.addAllWithoutDuplicates(filtered, validPositions);
        }
        return validPositions;
    }

    public static List<StreetType> getPossibleStreetType(Map map, EdgePosition position) {
        List<StreetType> types = new ArrayList<>();
        TilePosition[] tilePositions = MapTools.getTilesPositions(position);
        List<Tile> tiles = Arrays.stream(tilePositions).map(tp -> map.getTile(tp)).filter(Optional::isPresent).map(t -> t.get()).toList();

        if (tiles.stream().anyMatch(tile -> !tile.isWater())) {
            types.add(StreetType.ROAD);
        }

        if (tiles.stream().anyMatch(tile -> tile.isWater())) {
            types.add(StreetType.SHIP);
        }

        return types;
    }

    public static List<EdgePosition> getValidEdgePositions(Map map, PlayerColor color, StreetType streetType) {
        List<EdgePosition> validPositions = new ArrayList<>();
        var playersStreets = map.getStreets(color).stream().filter(s -> s.getObject().getType().equals(streetType)).toList();
        var playersBuildings = map.getBuildings(color);
        for (PositionedStreet street : playersStreets) {
            var neighbourStreets = MapTools.getEdgePositions(street.getPosition());
            ListUtility.addAllWithoutDuplicates(Arrays.stream(neighbourStreets).toList(), validPositions);
        }
        for (Building building : playersBuildings) {
            var neighbourStreets = MapTools.getEdgePositions(building.getPosition());
            ListUtility.addAllWithoutDuplicates(Arrays.stream(neighbourStreets).toList(), validPositions);
        }
        validPositions = validPositions.stream().filter(ep -> map.getStreet(ep).isEmpty()).toList();
        validPositions = validPositions.stream().filter(p -> MapTools.isPositionValid(map, p)).toList();
        validPositions = validPositions.stream().filter(p -> BuildRules.getPossibleStreetType(map, p).contains(streetType)).toList();
        return validPositions;
    }

    public static boolean isNodeValidForNewBuilding(Map map, NodePosition position) {
        return map.getBuilding(position).isEmpty() && areNeighbourNodesFree(map, position) && !isNodeInWater(map, position);
    }

    public static boolean areNeighbourNodesFree(Map map, NodePosition position) {
        return !Arrays.stream(MapTools.getNodePositions(position)).anyMatch(np -> map.getBuilding(np).isPresent());
    }

    public static boolean isNodeInWater(Map map, NodePosition position) {
        return !Arrays.stream(MapTools.getTilesPositions(position)).anyMatch(tp -> isTileLand(map, tp));
    }

    public static boolean isTileLand(Map map, TilePosition position) {
        Optional<Tile> tile = map.getTile(position);
        if (tile.isPresent()) {
            return !tile.get().isWater();
        }
        return false;
    }

    public static boolean canBeUpgradedToTown(Map map, PlayerColor color,  NodePosition position) {
        if (map.getBuilding(position).isPresent()) {
            Building building = map.getBuilding(position).get();
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
        var nodePositions = new ArrayList<>(map.getBuildings(color).stream().map(b -> b.getPosition()).toList());
        nodePositions = new ArrayList<>(nodePositions.stream().filter(nP -> !Arrays.stream(MapTools.getEdgePositions(nP)).anyMatch(eP -> map.getStreet(eP).isPresent())).toList());
        edgePositions = Arrays.stream(MapTools.getEdgePositions(nodePositions.get(0))).toList();
        edgePositions = new ArrayList<>(edgePositions.stream().filter(eP -> MapTools.isPositionValid(map, eP) && BuildRules.getPossibleStreetType(map, eP).contains(type)).toList());
        return edgePositions;
    }
}
