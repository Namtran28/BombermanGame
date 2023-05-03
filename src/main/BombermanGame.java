package main;

import Map.Map;
import entities.bombs.Bomb;
import entities.player.Bomber;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import entities.Entity;
import graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import playerInputs.KeyHandler;
import sound.Sound;

public class BombermanGame extends Application {

    public static /*final*/ int WIDTH;
    public static /*final*/ int HEIGHT;
    public static Sound music;
    private static GraphicsContext gc;
    private static Canvas canvas;
    private static List<Entity> enemies = new ArrayList<>(); // list of enemies.
    private static List<Entity> stillObjects = new ArrayList<>(); // list of Wall, Brick and Bomb.
    private static List<Entity> items = new ArrayList<>(); // list items.
    private static List<Entity> backGround = new ArrayList<>(); // only Grass
    private static KeyHandler keyHandler;
    private static Entity[][] table; // table contain Wall, Brick, Bomb, Item, Grass --- not contain Bomber and Enemy.
    private static Entity[][] moveEntitiesTable;
    private static Entity[][] itemsTable;
    public static Bomber player;
    private static int level = 0;
    public static boolean levelChanged = true;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void load(Stage stage, int level) {
        if (music != null) music.stopSound();
        music = Sound.main_bgm;
        music.loop();
        Map map = new Map(level);
        HEIGHT = map.getRows();
        WIDTH = map.getCols();
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
        items = map.getItems();
        stillObjects = map.getStillObjects();
        enemies = map.getEnemies();
        backGround = map.getBackGround();
        table = map.getTable();
        moveEntitiesTable = map.getMoveEntitiesTable();
        itemsTable = map.getItemsTable();
        Bomb.cnt = 0;
        player = map.getPlayer();

        // Add scene vao stage
        stage.setTitle("Bomberman L&N");
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
    }

    public static void addBomb(Entity bomb) {
        stillObjects.add(bomb);
    }

    public static void removeEnemy(Entity enemy) {
        enemies.remove(enemy);
    }

    @Override
    public void start(Stage stage) {
        AnimationTimer test = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (levelChanged) {
                    levelChanged = false;
                    load(stage, level);
                }
            }
        };
        test.start();
    }

    public void update(Scene scene) {
        player.update(scene);
        for (Entity enemy : enemies) {
            enemy.update(scene);
        }
        for (Entity item : items) {
            item.update(scene);
        }
        for (Entity entity : stillObjects) {
            entity.update(scene);
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        backGround.forEach((g -> g.render(gc)));
        items.forEach(g -> g.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));
        player.render(gc);
    }

    public static Entity[][] getTable() {
        return table;
    }

    public static void setTable(int px, int py, Entity entity) {
        table[px][py] = entity;
    }

    public static List<Entity> getEnemies() {
        return enemies;
    }

    public static void removeFlames(Entity flame) {
        stillObjects.remove(flame);
    }

    public static void removeBrick(Entity brick) {
        stillObjects.remove(brick);
    }

    public static List<Entity> getItems() {
        return items;
    }

    public static void removeItem(Entity item) {
        items.remove(item);
    }

    public static Entity[][] getMoveEntitiesTable() {
        return moveEntitiesTable;
    }

    public static Entity[][] getItemsTable() {
        return itemsTable;
    }

    public static void setMoveEntitiesTable(int px, int py, Entity entity) {
        moveEntitiesTable[px][py] = entity;
    }
    public static void setItemsTable(int px, int py, Entity entity) {
        itemsTable[px][py] = entity;
    }

    public static void setLevel(int level) {
        BombermanGame.level = level;
    }

    public static int getLevel() {
        return level;
    }
    public static void setNull() {
//        music = null;
//        gc = null;
//        canvas = null;
//        enemies = null;
//        stillObjects = null;
//        items = null;
//        backGround = null;
//        keyHandler = null;
//        table = null;
//        moveEntitiesTable = null;
//        itemsTable = null;
//        player = null;
    }
}
