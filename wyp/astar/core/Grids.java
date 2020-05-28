package astar.core;

import java.util.ArrayList;
import java.util.List;

public class Grids {
    public int[][] maps = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
    };

    public Grids() {
    }

    public Grids(int[][] maps) {
        this.maps = maps;
    }

    boolean canReach(int x, int y) {
        if (x >= 0 && x < maps[0].length && y >= 0 && y < maps.length) {
            return maps[y][x] == 0;
        }
        return false;
    }

    public void printGrids() {
        for (int i = 0; i < maps[0].length; i++) {
            System.out.print("\t" + i + ",");
        }
        System.out.print("\n-----------------------------------------\n");
        int count = 0;
        for (int[] map : maps) {
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

    //从终点开始沿着路径退回起点
    public List<Node> getPaths(Node endNode) {
        List<Node> arrayList = new ArrayList<>();
        Node pre = endNode;
        while (pre != null) {
            arrayList.add(new Node(pre.getX(), pre.getY(), pre.getG(), pre.getH(), pre.getParent()));
            pre = pre.getParent();
        }
        return arrayList;
    }


    void printPaths(List<Node> nodeList) {
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
        System.out.print(" Node.size=" + nodeList.size() + " F=" + nodeList.get(0).getF());
        System.out.println();
    }
}