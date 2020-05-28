package astar.core;

import java.util.ArrayList;
import java.util.List;

public abstract class AStar {
    private final List<Node> openList = new ArrayList<>();
    final List<Node> closeList = new ArrayList<>();

    final Grids grids;

    private final Node startNode;
    private final Node endNode;

    AStar(Grids grids, Node startNode, Node endNode) {
        this.grids = grids;
        this.startNode = startNode;
        this.endNode = endNode;
    }

    private Node findMinFNodeInOpenList() {
        Node tempNode = openList.get(0);
        for (Node node : openList) {
            if (node.getF() < tempNode.getF()) {
                tempNode = node;
            }
        }
        return tempNode;
    }

    abstract List<Node> findNeighborNodes(Node currentNode);


    static boolean exists(List<Node> nodeList, int x, int y) {
        return find(nodeList, x, y) != null;
    }

    private static boolean exists(List<Node> nodeList, Node node) {
        return find(nodeList, node.getX(), node.getY()) != null;
    }

    private static Node find(List<Node> nodeList, int x, int y) {
        for (Node n : nodeList)
            if ((n.getX() == x) && (n.getY() == y)) {
                return n;
            }
        return null;
    }

    private static Node find(List<Node> nodeList, Node node) {
        return find(nodeList, node.getX(), node.getY());
    }

    public Node findPath() {
        if (startNode == null || endNode == null) {
            return null;
        }
        // 把起点加入 open list
        openList.add(startNode);

        while (!openList.isEmpty()) {
            // 遍历 open list ，查找 F值最小的节点，把它作为当前要处理的节点
            Node currentNode = findMinFNodeInOpenList();
            // 从open list中移除
            openList.remove(currentNode);
            // 把这个节点移到 close list
            closeList.add(currentNode);

            List<Node> neighborNodes = findNeighborNodes(currentNode);
            for (Node node : neighborNodes) {
                //当前节点的相邻界节点已经在开放链表中
                if (exists(openList, node)) {
                    foundPoint(currentNode, node);
                } else {
                    notFoundPoint(currentNode, endNode, node);
                }
            }
            //终点在开放链表中，则找到路径
            Node end = find(openList, endNode);
            if (end != null) {
                return end;
            }
        }

        return find(openList, endNode);
    }

    private void foundPoint(Node tempStart, Node node) {
        int G = calcG(tempStart, node);
        //途径当前节点tempStart到达该节点node的距离G更小时，更新F
        if (G < node.getG()) {
            node.setParent(tempStart);
            node.setG(G);
        }
    }

    private void notFoundPoint(Node tempStart, Node end, Node node) {
        node.setParent(tempStart);
        node.setG(calcG(tempStart, node));
        node.setH(calcH(end, node));
        openList.add(node);
    }

    abstract int calcG(Node tempStart, Node node);

    abstract int calcH(Node end, Node node);

    public void findAndPrintPaths() {
        Node end = findPath();
        if (end != null) {
            grids.printPaths(grids.getPaths(end));
        } else {
            System.out.println("not find path from " + startNode + " to " + endNode);
        }
    }

}
