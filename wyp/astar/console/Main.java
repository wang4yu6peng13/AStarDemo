package astar.console;

import astar.core.*;

class Main {
    public static void main(String[] args) {
        final Grids grids = new Grids();
        Utils.printGrids(grids);

        // 起点 终点
        final Node startNode = new Node(5, 1);
        final Node endNode = new Node(2, 4);

        // 4
        Utils.findAndPrintPaths(new AStar4Direction(grids, startNode, endNode));
        // 8 no corner
        Utils.findAndPrintPaths(new AStar8DirectionNoCrossCorner(grids, startNode, endNode));
        // 8 corner
        Utils.findAndPrintPaths(new AStar8DirectionWithCrossCorner(grids, startNode, endNode));
    }
}
