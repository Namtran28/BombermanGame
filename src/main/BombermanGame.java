package main;

import Map.Map;
import entities.characters.*;
import entities.items.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import entities.player.Bomber;
import entities.Entity;
import entities.tiles.*;
import graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static /*final*/ int WIDTH;
    public static /*final*/ int HEIGHT;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        createMap();
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Add scene vao stage
        stage.setScene(scene);
        Image icon = new Image("/Mew.jpg");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        /*Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);*/
    }

    public void createMap() {
        /*for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                }
                else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }*/
        Map map = new Map();
        int level = map.getLevel();
        HEIGHT = map.getRows();
        WIDTH = map.getCols();
        char[][] _map = map.getMap();

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                //Entity object;
                if (_map[j][i] == '#') {
                    stillObjects.add(new Wall(i, j, Sprite.wall.getFxImage()));
                }
                else if (_map[j][i] == '*') {
                    stillObjects.add(new Brick(i, j, Sprite.brick.getFxImage()));
                }
                else if (_map[j][i] == 'x') {
                    stillObjects.add(new Portal(i, j, Sprite.portal.getFxImage()));
                    entities.add(new Brick(i, j, Sprite.brick.getFxImage()));
                }
                else if (_map[j][i] == 'p') {
                    stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                    //stillObjects.add(object);
                    entities.add(new Bomber(i, j, Sprite.player_right.getFxImage()));
                }
                else if (_map[j][i] == '1') {
                    stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                    entities.add(new Balloon(i, j, Sprite.balloom_left1.getFxImage()));
                }
                else if (_map[j][i] == '2') {
                    stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                    entities.add(new Oneal(i, j, Sprite.oneal_left1.getFxImage()));
                }
                else if (_map[j][i] == 'b') {
                    entities.add(new Brick(i, j, Sprite.brick.getFxImage()));
                    stillObjects.add(new BombItem(i, j, Sprite.powerup_bombs.getFxImage()));
                }
                else if (_map[j][i] == 'f') {
                    stillObjects.add(new FlameItem(i, j, Sprite.powerup_flames.getFxImage()));
                    entities.add(new Brick(i, j, Sprite.brick.getFxImage()));
                }
                else if (_map[j][i] == 's') {
                    stillObjects.add(new SpeedItem(i, j, Sprite.powerup_speed.getFxImage()));
                    entities.add(new Brick(i, j, Sprite.brick.getFxImage()));
                }
                else if (_map[j][i] == 'm') {
                    stillObjects.add(new FlamePass(i, j, Sprite.powerup_flamepass.getFxImage()));
                    entities.add(new Brick(i, j, Sprite.brick.getFxImage()));
                }
                else if (_map[j][i] == 'w') {
                    stillObjects.add(new WallPass(i, j, Sprite.powerup_wallpass.getFxImage()));
                    entities.add(new Brick(i, j, Sprite.brick.getFxImage()));
                }
                else {
                    stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                }
                //stillObjects.add(object);
            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
