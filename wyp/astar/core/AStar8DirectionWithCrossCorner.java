package astar.core;

import java.util.ArrayList;
import java.util.List;

public class AStar8DirectionWithCrossCorner extends AStar8DirectionNoCrossCorner {
    public AStar8DirectionWithCrossCorner(final Grids grids, final Node startNode, final Node endNode) {
        super(grids, startNode, endNode);
    }

    @Override
    List<Node> findNeighborNodes(final Node currentNode) {
        final List<Node> nodeList = new ArrayList<>();
        // 直方向
        nodeList.addAll(find4NeighborNodes(currentNode, straightDirection));
        // 斜方向
        nodeList.addAll(find4NeighborNodes(currentNode, slopingDirection));
        return nodeList;
    }

    @Override
    public AStarType getAStarType() {
        return AStarType.ASTAR_8_WITH_CORNER;
    }
}