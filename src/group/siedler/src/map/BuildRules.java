package map;

import buildings.Building;
import buildings.BuildingType;
import helper.ListCombiner;
import player.PlayerColor;
import positions.EdgePosition;
import positions.NodePosition;
import streets.Street;
import streets.StreetType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuildRules {
    public static boolean isValid(Map map, PlayerColor color, NodePosition position, BuildingType type) {
        return getValidPositions(map, color, type).contains(position);
    }

    public static List<NodePosition> getValidPositions(Map map, PlayerColor color, BuildingType buildingType) {
        List<NodePosition> validPositions = new ArrayList<>();
        var playersStreets = map.getStreets(color);
        var playersBuildings = map.getBuildings(color);
        for(Street street : playersStreets) {
            var nodes = MapTools.getNodePositions(street.getPosition());
            var filtered = Arrays.stream(nodes).filter(nodePosition -> MapTools.isPositionValid(map, nodePosition));
            if(buildingType == BuildingType.VILLAGE) {
                filtered = filtered.filter(nodePosition -> map.getBuilding(nodePosition).isEmpty());
                filtered = filtered.filter(nodePosition -> isNodeValidForNewBuilding(map, nodePosition));
            } else {
                filtered = filtered.filter(nodePosition -> canBeUpgradedToTown(map, color, nodePosition));
            }
            ListCombiner.addAllWithoutDuplicates(filtered.toList(), validPositions);
        }
        return validPositions;
    }


    public static List<EdgePosition> getValidPositions(Map map, PlayerColor color) {
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
        validPositions = validPositions.stream().filter(ep -> MapTools.isPositionValid(map, ep)).toList();
        validPositions = validPositions.stream().filter(ep -> map.getStreet(ep).isEmpty()).toList();
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
