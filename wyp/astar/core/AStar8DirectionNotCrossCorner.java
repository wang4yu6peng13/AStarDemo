package astar.core;

import java.util.ArrayList;
import java.util.List;

public class AStar8DirectionNotCrossCorner extends AStar {
    static final int[][] direct = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    static final int[][] direct2 = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
    private static final int step = 10;
    private static final int step2 = 14;

    public AStar8DirectionNotCrossCorner(Grids grids, Node startNode, Node endNode) {
        super(grids, startNode, endNode);
    }

    @Override
    List<Node> findNeighborNodes(Node currentNode) {
        List<Node> nodeList = new ArrayList<>();
        // 直方向
        for (int i = 0; i < 4; i++) {
            int newX = currentNode.getX() + direct[i][0];
            int newY = currentNode.getY() + direct[i][1];
            // 如果当前节点的相邻节点，可达（不是障碍位置） 且 不在闭合链表
            if (grids.canReach(newX, newY) && !exists(closeList, newX, newY)) {
                nodeList.add(new Node(newX, newY));
            }
        }
        // 斜方向
        for (int i = 0; i < 4; i++) {
            int newX = currentNode.getX() + direct2[i][0];
            int newY = currentNode.getY() + direct2[i][1];
            int neighborX1 = currentNode.getX();
            int neighborY1 = currentNode.getY() + direct2[i][1];
            int neighborX2 = currentNode.getX() + direct2[i][0];
            int neighborY2 = currentNode.getY();
            // 可达 不在闭合 角相邻可达
            if (grids.canReach(newX, newY) && !exists(closeList, newX, newY) && grids.canReach(neighborX1, neighborY1) && grids.canReach(neighborX2, neighborY2)) {
                nodeList.add(new Node(newX, newY));
            }
        }
        return nodeList;
    }

    @Override
    int calcG(Node tempStart, Node node) {
        int parentG = node.getParent() != null ? node.getParent().getG() : 0;
        int s;
        if (tempStart.getX() == node.getX() || tempStart.getY() == node.getY()) {
            s = step;
        } else {
            s = step2;
        }
        return s + parentG;
    }

    @Override
    int calcH(Node end, Node node) {
        int dx = Math.abs(node.getX() - end.getX());
        int dy = Math.abs(node.getY() - end.getY());
        return step * (dx + dy) + (step2 - 2 * step) * Math.min(dx, dy);
    }
}
