package playerInputs;

import graphics.Sprite;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import Map.Map;
import javafx.stage.Stage;

public class Runner extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Map map = new Map(2);
        Group group = new Group();
        Scene scene = new Scene(group);
        map.createMap(new KeyHandler(scene));
        for (int i = 0; i < map.getRows(); i++) {
            for (int j = 0; j < map.getCols(); j++) {
                System.out.print(map.getTable()[i][j].getClass().getName() + "<--->");
            }
            System.out.println();
        }
        stage.setScene(scene);
        stage.show();
    }
}