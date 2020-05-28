package astar.core;

import java.util.ArrayList;
import java.util.List;

public abstract class AStar {
    private final List<Node> openList = new ArrayList<>();
    final List<Node> closeList = new ArrayList<>();

    final Grids grids;

    private final Node startNode;
    private final Node endNode;

    AStar(final Grids grids, final Node startNode, final Node endNode) {
        this.grids = grids;
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public Grids getGrids() {
        return grids;
    }

    private Node findMinFNodeInOpenList() {
        Node tempNode = openList.get(0);
        for (final Node node : openList) {
            if (node.getF() < tempNode.getF()) {
                tempNode = node;
            }
        }
        return tempNode;
    }

    abstract List<Node> findNeighborNodes(Node currentNode);

    public static boolean exists(final List<Node> nodeList, final int x, final int y) {
        return find(nodeList, x, y) != null;
    }

    private static boolean exists(final List<Node> nodeList, final Node node) {
        return find(nodeList, node.getX(), node.getY()) != null;
    }

    private static Node find(final List<Node> nodeList, final int x, final int y) {
        for (final Node n : nodeList)
            if ((n.getX() == x) && (n.getY() == y)) {
                return n;
            }
        return null;
    }

    private static Node find(final List<Node> nodeList, final Node node) {
        return find(nodeList, node.getX(), node.getY());
    }

    private Node findPath() {
        if (startNode == null || endNode == null) {
            return null;
        }
        // 起点加入openList
        openList.add(startNode);

        while (!openList.isEmpty()) {
            // 遍历openList 查找F值最小的节点 把它作为当前要处理的节点
            final Node currentNode = findMinFNodeInOpenList();
            // 从openList中移除
            openList.remove(currentNode);
            // 把这个节点移到closeList
            closeList.add(currentNode);

            final List<Node> neighborNodes = findNeighborNodes(currentNode);
            for (final Node node : neighborNodes) {
                // 当前节点的相邻节点已经在openList中
                if (exists(openList, node)) {
                    foundPoint(currentNode, node);
                } else {
                    notFoundPoint(currentNode, endNode, node);
                }
            }
            // 终点在openList中 则找到路径
            final Node end = find(openList, endNode);
            if (end != null) {
                return end;
            }
        }

        return find(openList, endNode);
    }

    private void foundPoint(final Node tempStart, final Node node) {
        final int G = calcG(tempStart, node);
        // 途经当前节点tempStart到达该节点node的G更小 则更新
        if (G < node.getG()) {
            node.setParent(tempStart);
            node.setG(G);
        }
    }

    private void notFoundPoint(final Node tempStart, final Node end, final Node node) {
        node.setParent(tempStart);
        node.setG(calcG(tempStart, node));
        node.setH(calcH(end, node));
        openList.add(node);
    }

    abstract int calcG(Node tempStart, Node node);

    abstract int calcH(Node end, Node node);

    /**
     * 从终点开始沿着路径退回起点
     */
    private List<Node> getPaths(final Node endNode) {
        final List<Node> nodeList = new ArrayList<>();
        Node pre = endNode;
        while (pre != null) {
            nodeList.add(new Node(pre.getX(), pre.getY(), pre.getG(), pre.getH(), pre.getParent()));
            pre = pre.getParent();
        }
        return nodeList;
    }

    public List<Node> findAndGetPaths() {
        final Node end = findPath();
        return end != null ? getPaths(end) : null;
    }

    public abstract AStarType getAStarType();

}
