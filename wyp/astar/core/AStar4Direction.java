package astar.core;

import java.util.ArrayList;
import java.util.List;

public class AStar4Direction extends AStar {
    private static final int[][] direct = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private static final int step = 10;

    public AStar4Direction(Grids grids, Node startNode, Node endNode) {
        super(grids, startNode, endNode);
    }

    @Override
    List<Node> findNeighborNodes(Node currentNode) {
        List<Node> nodeList = new ArrayList<>();
        // 只考虑上下左右，不考虑斜对角
        for (int i = 0; i < 4; i++) {
            int newX = currentNode.getX() + direct[i][0];
            int newY = currentNode.getY() + direct[i][1];
            // 如果当前节点的相邻节点，可达（不是障碍位置） 且 不在闭合链表
            if (grids.canReach(newX, newY) && !exists(closeList, newX, newY)) {
                nodeList.add(new Node(newX, newY));
            }
        }
        return nodeList;
    }

    @Override
    int calcG(Node tempStart, Node node) {
        int parentG = node.getParent() != null ? node.getParent().getG() : 0;
        return step + parentG;
    }

    @Override
    int calcH(Node end, Node node) {
        int dx = Math.abs(node.getX() - end.getX());
        int dy = Math.abs(node.getY() - end.getY());
        return (dx + dy) * step;
    }
}
