package astar.core;

public class Grids {
    public int[][] maps;

    public Grids() {
        this.maps = new int[][]{
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
    }

    public Grids(final int[][] maps) {
        this.maps = maps;
    }

    /**
     * 指定坐标是否可达
     */
    boolean canReach(final int x, final int y) {
        if (x >= 0 && x < maps[0].length && y >= 0 && y < maps.length) {
            return maps[y][x] == 0;
        }
        return false;
    }

}