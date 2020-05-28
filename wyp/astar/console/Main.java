package astar.console;

import astar.core.*;

public class Main {
    public static void main(String[] args) {
        Grids grids = new Grids();
        grids.printGrids();

        //定点:起点终点
        Node startNode = new Node(5, 1);
        Node endNode = new Node(2, 4);
        // 4
        new AStar4Direction(grids, startNode, endNode).findAndPrintPaths();
        // 8 no corner
        new AStar8DirectionNotCrossCorner(grids, startNode, endNode).findAndPrintPaths();
        // 8 corner
        new AStar8DirectionCrossCorner(grids, startNode, endNode).findAndPrintPaths();
    }
}
