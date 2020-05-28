package astar.gui;

import astar.core.*;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Random;

class GridsPane extends GridPane {
    private Grids grids;
    private final Rectangle[][] bg;
    private Canvas canvas;
    private Node startNode;
    private Node endNode;
    private boolean gridMaking = false;

    GridsPane() {
        setPadding(new Insets(10, 10, 10, 10));
        setVgap(2);
        setHgap(2);
        setLayoutX(5);
        setLayoutY(5);
        grids = new Grids(randomMaps());
        bg = new Rectangle[grids.maps.length][grids.maps[0].length];
        fillGrid();
    }

    private void fillGrid() {
        for (int y = 0; y < grids.maps.length; y++) {
            for (int x = 0; x < grids.maps[0].length; x++) {
                bg[y][x] = new Rectangle(30, 30);
                bg[y][x].setFill(isWallGrid(x, y) ? Colors.wallColor() : Colors.walkColor());
                final int row = y;
                final int col = x;
                bg[y][x].setOnMouseEntered(e -> {
                    if (!gridMaking) {
                        if (isNormalGrid(col, row)) {
                            bg[row][col].setFill(Colors.mouseEnterColor());
                        }
                    }
                });
                bg[y][x].setOnMouseExited(e -> {
                    if (!gridMaking) {
                        // 非画地图
                        if (isNormalGrid(col, row)) {
                            bg[row][col].setFill(Colors.walkColor());
                        }
                    }
                });
                bg[y][x].setOnMouseClicked(e -> {
                    final MouseButton button = e.getButton();
                    if (!gridMaking) {
                        // 非画地图模式
                        if (!isWallGrid(col, row)) {
                            switch (button) {
                                case PRIMARY:
                                    if (startNode != null) {
                                        bg[startNode.getY()][startNode.getX()].setFill(Colors.walkColor());
                                    }
                                    startNode = new Node(col, row);
                                    bg[row][col].setFill(Colors.startColor());
                                    break;
                                case SECONDARY:
                                    if (endNode != null) {
                                        bg[endNode.getY()][endNode.getX()].setFill(Colors.walkColor());
                                    }
                                    endNode = new Node(col, row);
                                    bg[row][col].setFill(Colors.endColor());
                                    break;
                            }
                        }
                    } else {
                        //画地图模式
                        switch (button) {
                            case PRIMARY:
                                grids.maps[row][col] = 1;
                                bg[row][col].setFill(Colors.wallColor());
                                break;
                            case SECONDARY:
                                grids.maps[row][col] = 0;
                                bg[row][col].setFill(Colors.walkColor());
                                break;
                        }
                    }
                });
                add(bg[y][x], x, y);
            }
        }
    }

    boolean makeGrid(final Pane pane) {
        if (!gridMaking) {
            clear(pane);
            gridMaking = true;
        } else {
            gridMaking = false;
        }
        return gridMaking;
    }


    void start(final Pane pane, final AStarType type) {
        switch (type) {
            case ASTAR_4:
                runAndShow(pane, new AStar4Direction(grids, startNode, endNode));
                break;
            case ASTAR_8_NO_CORNER:
                runAndShow(pane, new AStar8DirectionNoCrossCorner(grids, startNode, endNode));
                break;
            case ASTAR_8_WITH_CORNER:
                runAndShow(pane, new AStar8DirectionWithCrossCorner(grids, startNode, endNode));
                break;
        }
    }

    private void runAndShow(final Pane pane, final AStar aStar) {
        if (startNode == null || endNode == null) {
            alert(Alert.AlertType.ERROR, "起点或终点没有指定！！！");
            return;
        }
        clearPath(pane);
        final List<Node> paths = aStar.findAndGetPaths();
        if (paths != null) {
            canvas = new Canvas(getWidth(), getHeight());
            final GraphicsContext gc = canvas.getGraphicsContext2D();
            for (int i = 0; i < paths.size() - 1; i++) {
                final Node node1 = paths.get(i);
                final Node node2 = paths.get(i + 1);
                if (i != 0) {
                    bg[node1.getY()][node1.getX()].setFill(Colors.pathGridColor());
                }
                final Rectangle rectangle1 = bg[node1.getY()][node1.getX()];
                final Rectangle rectangle2 = bg[node2.getY()][node2.getX()];
                // gc.save();
                gc.setStroke(Colors.pathLineColor());
                gc.setLineWidth(2);                //设置线的宽度
                gc.strokeLine(rectangle1.getLayoutX() + rectangle1.getWidth() / 2, rectangle1.getLayoutY() + rectangle1.getHeight() / 2, rectangle2.getLayoutX() + rectangle2.getWidth() / 2, rectangle2.getLayoutY() + rectangle2.getHeight() / 2);      //绘制直线
                // gc.restore();
            }
            pane.getChildren().add(canvas);
        } else {
            alert(Alert.AlertType.WARNING, "找不到路径！！！");
        }
    }

    private void clearPath(final Pane pane) {
        pane.getChildren().remove(canvas);
        for (int y = 0; y < grids.maps.length; y++) {
            for (int x = 0; x < grids.maps[0].length; x++) {
                if (grids.maps[y][x] == 0 && isNormalGrid(x, y)) {
                    bg[y][x].setFill(Colors.walkColor());
                }
            }
        }
    }

    void clear(final Pane pane) {
        pane.getChildren().remove(canvas);
        for (int y = 0; y < grids.maps.length; y++) {
            for (int x = 0; x < grids.maps[0].length; x++) {
                if (grids.maps[y][x] == 0) {
                    bg[y][x].setFill(Colors.walkColor());
                }
            }
        }
        startNode = null;
        endNode = null;
    }

    private boolean isNormalGrid(final int x, final int y) {
        if (startNode != null && startNode.getX() == x && startNode.getY() == y) {
            return false;
        }
        if (endNode != null && endNode.getX() == x && endNode.getY() == y) {
            return false;
        }
        if (isWallGrid(x, y)) {
            return false;
        }
        return true;
    }

    private boolean isWallGrid(final int x, final int y) {
        return grids.maps[y][x] == 1;
    }

    private int[][] randomMaps() {
        final int[][] maps = new int[20][30];
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 30; x++) {
                maps[y][x] = new Random().nextBoolean() ? 1 : 0;
            }
        }
        return maps;
    }

    void resetMap(final Pane pane) {
        clear(pane);
        grids = new Grids(randomMaps());
        fillGrid();
    }

    private void alert(final Alert.AlertType alertType, final String content) {
        final Alert alert = new Alert(alertType);
        alert.setTitle("注意");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
