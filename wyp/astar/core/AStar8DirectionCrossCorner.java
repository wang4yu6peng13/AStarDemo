package astar.core;

import java.util.ArrayList;
import java.util.List;

public class AStar8DirectionCrossCorner extends AStar8DirectionNotCrossCorner {
    public AStar8DirectionCrossCorner(Grids grids, Node startNode, Node endNode) {
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
            // 可达 不在闭合
            if (grids.canReach(newX, newY) && !exists(closeList, newX, newY)) {
                nodeList.add(new Node(newX, newY));
            }
        }
        return nodeList;
    }
}