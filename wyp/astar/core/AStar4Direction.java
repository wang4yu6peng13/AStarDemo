package astar.core;

import java.util.ArrayList;
import java.util.List;

public class AStar4Direction extends AStar {
    static final int[][] straightDirection = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    static final int straightStep = 10;

    public AStar4Direction(final Grids grids, final Node startNode, final Node endNode) {
        super(grids, startNode, endNode);
    }

    List<Node> find4NeighborNodes(final Node currentNode, final int[][] direction) {
        final List<Node> nodeList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            final int newX = currentNode.getX() + direction[i][0];
            final int newY = currentNode.getY() + direction[i][1];
            // 如果当前节点的相邻节点 可达（不是障碍位置） 且 不在closeList
            if (grids.canReach(newX, newY) && !exists(closeList, newX, newY)) {
                nodeList.add(new Node(newX, newY));
            }
        }
        return nodeList;
    }

    @Override
    List<Node> findNeighborNodes(final Node currentNode) {
        // 只考虑上下左右 不考虑斜对角
        return find4NeighborNodes(currentNode, straightDirection);
    }

    int getParentG(final Node node) {
        return node.getParent() != null ? node.getParent().getG() : 0;
    }

    @Override
    int calcG(final Node tempStart, final Node node) {
        // 父节点G + 步长
        return getParentG(node) + straightStep;
    }

    @Override
    int calcH(final Node end, final Node node) {
        // 曼哈顿距离
        final int dx = getDx(end, node);
        final int dy = getDy(end, node);
        return (dx + dy) * straightStep;
    }

    @Override
    public AStarType getAStarType() {
        return AStarType.ASTAR_4;
    }

    int getDx(final Node end, final Node node) {
        return Math.abs(node.getX() - end.getX());
    }

    int getDy(final Node end, final Node node) {
        return Math.abs(node.getY() - end.getY());
    }

}
