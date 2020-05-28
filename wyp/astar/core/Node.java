package astar.core;

public class Node {
    private final int x;
    private final int y;
    private int G;
    private int H;
    private Node parent;

    public Node(final int x, final int y) {
        this(x, y, 0, 0, null);
    }

    public Node(final int x, final int y, final int G, final int H, final Node parent) {
        this.x = x;
        this.y = y;
        this.G = G;
        this.H = H;
        this.parent = parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getF() {
        return G + H;
    }

    public int getG() {
        return G;
    }

    public int getH() {
        return H;
    }

    public Node getParent() {
        return parent;
    }

    public void setG(final int g) {
        G = g;
    }

    public void setH(final int h) {
        H = h;
    }

    public void setParent(final Node parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
