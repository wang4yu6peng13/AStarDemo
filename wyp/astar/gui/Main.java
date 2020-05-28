package astar.gui;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane menu = new GridPane();
        GridsPane gridsPane = new GridsPane();
        GridPane pane = new GridPane();

        ToggleGroup group = new ToggleGroup();
        RadioButton rbtAStar4 = new RadioButton("4方向");
        rbtAStar4.setToggleGroup(group);
        rbtAStar4.setUserData(AStarType.ASTAR_4);
        RadioButton rbtAStar8NoCorner = new RadioButton("8方向不穿墙角");
        rbtAStar8NoCorner.setToggleGroup(group);
        rbtAStar8NoCorner.setUserData(AStarType.ASTAR_8_NO_CORNER);
        RadioButton rbtAStar8Corner = new RadioButton("8方向可穿墙角");
        rbtAStar8Corner.setToggleGroup(group);
        rbtAStar8Corner.setUserData(AStarType.ASTAR_8_CORNER);
        RadioButton rbtClear = new RadioButton("重新指定起点终点");
        rbtClear.setToggleGroup(group);
        rbtClear.setUserData(AStarType.CLEAR);
        group.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
                    if (group.getSelectedToggle() != null) {
                        AStarType aStarType = (AStarType) group.getSelectedToggle().getUserData();
                        gridsPane.start(pane, aStarType);
                    }
                });
        ToggleButton tbMakeGrid = new ToggleButton("画地图:点击画图");
        tbMakeGrid.setOnMouseClicked(event -> {
            boolean gridMaking = gridsPane.makeGrid(pane);
            tbMakeGrid.setSelected(gridMaking);
            tbMakeGrid.setText(String.format("画地图:点击%s", gridMaking ? "结束" : "画图"));
        });
        Button btResetMap = new Button("重置地图");
        btResetMap.setOnMouseClicked(event -> gridsPane.resetMap(pane));

        menu.setAlignment(Pos.CENTER);
        menu.setVgap(10);
        menu.add(rbtAStar4, 0, 0);
        menu.add(rbtAStar8NoCorner, 0, 1);
        menu.add(rbtAStar8Corner, 0, 2);
        menu.add(rbtClear, 0, 3);
        menu.add(tbMakeGrid, 0, 4);
        menu.add(btResetMap, 0, 5);

        pane.setHgap(20);
        pane.setVgap(20);
        pane.setAlignment(Pos.TOP_LEFT);
        pane.add(gridsPane, 0, 0);
        pane.add(menu, 1, 0);


        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(400);
        primaryStage.setResizable(false);
        primaryStage.setTitle("AStar Demo");
        primaryStage.show();
    }

}
