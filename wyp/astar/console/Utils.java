package astar.console;

import astar.core.AStar;
import astar.core.AStarType;
import astar.core.Grids;
import astar.core.Node;

import java.util.List;

class Utils {
    private Utils() {
    }

    /**
     * 打印地图格子
     */
    static void printGrids(final Grids grids) {
        final int[][] maps = grids.maps;
        for (int i = 0; i < maps[0].length; i++) {
            System.out.print("\t" + i + ",");
        }
        System.out.print("\n-----------------------------------------\n");
        int count = 0;
        for (final int[] map : maps) {
            for (int j = 0; j < maps[0].length; j++) {
                if (j == 0) {
                    System.out.print(count++ + "|\t");
                }
                System.out.print(String.format("%s\t", map[j] == 0 ? " " : map[j]));
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * 打印路径
     */
    private static void printPaths(final Grids grids, final List<Node> nodeList, final AStarType aStarType) {
        final int[][] maps = grids.maps;
        // 地图形式
        for (int x = 0; x < maps[0].length; x++) {
            System.out.print("\t" + x + ",");
        }
        System.out.print("\n-----------------------------------------\n");
        int count = 0;

        for (int y = 0; y < maps.length; y++) {
            for (int x = 0; x < maps[0].length; x++) {
                if (x == 0) {
                    System.out.print(count++ + "|\t");
                }
                if (AStar.exists(nodeList, x, y)) {
                    System.out.print("*");
                } else {
                    System.out.print(maps[y][x] == 0 ? " " : maps[y][x]);
                }
                System.out.print("\t");
            }
            System.out.println();
        }
        System.out.println();
        // 路径形式
        for (int i = nodeList.size() - 1; i >= 0; i--) {
            if (i == 0)
                System.out.print(nodeList.get(i));
            else
                System.out.print(nodeList.get(i) + "->");
        }
        System.out.println(String.format(" %s Node.size=%d F=%d", aStarType.getDesc(), nodeList.size(), nodeList.get(0).getF()));
    }

    /**
     * 寻路并打印路径
     */
    static void findAndPrintPaths(final AStar aStar) {
        final List<Node> paths = aStar.findAndGetPaths();
        if (paths == null) {
            System.out.println("not find path from " + aStar.getStartNode() + " to " + aStar.getEndNode());
        } else {
            Utils.printPaths(aStar.getGrids(), paths, aStar.getAStarType());
        }
    }
}
