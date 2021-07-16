package map;

import buildings.Building;
import buildings.BuildingType;
import helper.ListCombiner;
import player.PlayerColor;
import positions.EdgePosition;
import positions.NodePosition;
import positions.TilePosition;
import streets.Street;
import streets.StreetType;
import tiles.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BuildRules {
    public static boolean isValid(Map map, PlayerColor color, NodePosition position, BuildingType type) {
        return getValidNodePositions(map, color, type).contains(position);
    }

    public static List<NodePosition> getValidNodePositions(Map map, PlayerColor color, BuildingType buildingType) {
        List<NodePosition> validPositions = new ArrayList<>();
        var playersStreets = map.getStreets(color);
        for(Street street : playersStreets) {
            var nodes = MapTools.getNodePositions(street.getPosition());
            var filtered = Arrays.stream(nodes).filter(p -> MapTools.isPositionValid(map, p)).toList();
            if(buildingType == BuildingType.VILLAGE) {
                filtered = filtered.stream().filter(nodePosition -> map.getBuilding(nodePosition).isEmpty()).toList();
                filtered = filtered.stream().filter(nodePosition -> isNodeValidForNewBuilding(map, nodePosition)).toList();
            } else {
                filtered = filtered.stream().filter(nodePosition -> canBeUpgradedToTown(map, color, nodePosition)).toList();
            }
            ListCombiner.addAllWithoutDuplicates(filtered, validPositions);
        }
        return validPositions;
    }

    public static List<EdgePosition> getValidEdgePositions(Map map, PlayerColor color, StreetType type) {
        return getValidEdgePositions(map, color).stream().filter(ep -> getPossibleStreetType(map, ep).contains(type)).toList();
    }

    public static List<StreetType> getPossibleStreetType(Map map, EdgePosition position) {
        List<StreetType> types = new ArrayList<>();
        TilePosition[] tilePositions = MapTools.getTilesPositions(position);
        List<Tile> tiles = Arrays.stream(tilePositions).map(tp -> map.getTile(tp)).filter(Optional::isPresent).map(t -> t.get()).toList();

        if(tiles.stream().anyMatch(tile -> !tile.toString().contains("WATER"))) {
            types.add(StreetType.ROAD);
        }

        if(tiles.stream().anyMatch(tile -> tile.toString().contains("WATER"))) {
            types.add(StreetType.SHIP);
        }

        return types;
    }

    public static List<EdgePosition> getValidEdgePositions(Map map, PlayerColor color) {
        List<EdgePosition> validPositions = new ArrayList<>();
        var playersStreets = map.getStreets(color);
        var playersBuildings = map.getBuildings(color);
        for(Street street : playersStreets) {
            var neighbourStreets = MapTools.getEdgePositions(street.getPosition());
            ListCombiner.addAllWithoutDuplicates(Arrays.stream(neighbourStreets).toList(), validPositions);
        }
        for(Building building : playersBuildings) {
            var neighbourStreets = MapTools.getEdgePositions(building.getPosition());
            ListCombiner.addAllWithoutDuplicates(Arrays.stream(neighbourStreets).toList(), validPositions);
        }
        validPositions = validPositions.stream().filter(ep -> map.getStreet(ep).isEmpty()).toList();
        validPositions = validPositions.stream().filter(p -> MapTools.isPositionValid(map, p)).toList();
        return validPositions;
    }

    public static boolean isNodeValidForNewBuilding(Map map, NodePosition position) {
        return !Arrays.stream(MapTools.getNodePositions(position)).anyMatch(np -> map.getBuilding(np).isPresent());
    }

    public static boolean canBeUpgradedToTown(Map map, PlayerColor color,  NodePosition position) {
        if(map.getBuilding(position).isPresent()) {
            Building building = map.getBuilding(position).get();
            return building.getColor() == color && building.getType() == BuildingType.VILLAGE;
        } else {
            return false;
        }
    }

}
