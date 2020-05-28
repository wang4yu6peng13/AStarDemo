package astar.core;

import java.util.List;

public class AStar8DirectionNoCrossCorner extends AStar4Direction {
    static final int[][] slopingDirection = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
    private static final int slopingStep = 14;

    public AStar8DirectionNoCrossCorner(final Grids grids, final Node startNode, final Node endNode) {
        super(grids, startNode, endNode);
    }

    @Override
    List<Node> findNeighborNodes(final Node currentNode) {
        final List<Node> nodeList = super.findNeighborNodes(currentNode);
        // 斜方向
        for (int i = 0; i < 4; i++) {
            final int newX = currentNode.getX() + slopingDirection[i][0];
            final int newY = currentNode.getY() + slopingDirection[i][1];
            final int neighborX1 = currentNode.getX();
            final int neighborY1 = currentNode.getY() + slopingDirection[i][1];
            final int neighborX2 = currentNode.getX() + slopingDirection[i][0];
            final int neighborY2 = currentNode.getY();
            // 可达 不在闭合 角相邻可达
            if (grids.canReach(newX, newY) && !exists(closeList, newX, newY) && grids.canReach(neighborX1, neighborY1) && grids.canReach(neighborX2, neighborY2)) {
                nodeList.add(new Node(newX, newY));
            }
        }
        return nodeList;
    }

    @Override
    int calcG(final Node tempStart, final Node node) {
        // 两节点在直方向: 父节点G + 直步长
        if (tempStart.getX() == node.getX() || tempStart.getY() == node.getY()) {
            return super.calcG(tempStart, node);
        }
        // 在斜方向: 父节点G + 斜步长
        return getParentG(node) + slopingStep;
    }

    @Override
    int calcH(final Node end, final Node node) {
        final int dx = getDx(end, node);
        final int dy = getDy(end, node);
        return super.calcH(end, node) + (slopingStep - 2 * straightStep) * Math.min(dx, dy);
    }

    @Override
    public AStarType getAStarType() {
        return AStarType.ASTAR_8_NO_CORNER;
    }
}
