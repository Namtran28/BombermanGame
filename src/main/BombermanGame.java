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

import playerInputs.KeyHandler;

public class BombermanGame extends Application {

    public static /*final*/ int WIDTH;
    public static /*final*/ int HEIGHT;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private List<Entity> items = new ArrayList<>();
    private List<Entity> backGround = new ArrayList<>();
    private KeyHandler keyHandler;
    private static Entity[][] table;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void load(Stage stage, int level) {
        Map map = new Map(level);
        this.HEIGHT = map.getRows();
        this.WIDTH = map.getCols();
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        keyHandler = new KeyHandler(scene);
        map.createMap(keyHandler);
        this.items = map.getItems();
        this.stillObjects = map.getStillObjects();
        this.entities = map.getEntities();
        this.backGround = map.getBackGround();
        this.table = map.getTable();

        // Add scene vao stage
        stage.setTitle("Bomberman");
        stage.setScene(scene);
        Image icon = new Image("/Mew.jpg");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update(scene);
            }
        };
        timer.start();

        /*Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);*/
    }

    @Override
    public void start(Stage stage) {
        load(stage, 2);
    }

//    public void createMap(int level) {
//        Map map = new Map(level);
//        map.createMap(keyHandler);
//        this.items = map.getItems();
//        this.stillObjects = map.getStillObjects();
//        this.entities = map.getEntities();
//        this.HEIGHT = map.getRows();
//        this.WIDTH = map.getCols();
//    }

    public void update(Scene scene) {
        for (Entity entity : entities) {
            entity.update(scene);
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        backGround.forEach((g->g.render(gc)));
        items.forEach(g -> g.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public static Entity[][] getTable() {
        return table;
    }

    public static void setTable(int px, int py, Entity entity) {
        table[px][py] = entity;
    }
}
