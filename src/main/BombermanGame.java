package main;

import Map.Map;
import entities.bombs.Bomb;
import entities.player.Bomber;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import entities.Entity;
import graphics.Sprite;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.List;
import playerInputs.KeyHandler;
import sound.Sound;

public class BombermanGame extends Application {

    public static FUNCTION gameFunction = FUNCTION.MENU;
    public static /*final*/ int WIDTH;
    public static /*final*/ int HEIGHT;
    public static Sound music;
    public static long FPS_GAME = 1080 / 60;
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
    public static boolean endGame = false;
    public static boolean replay = false;
    private boolean running = true;
    private Group root = null;
    private Text textLife = null;
    private Text textLevel = null;
    private Text textEnemy = null;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void menu(Stage stage) {
        if (music != null) music.stopSound();
        music = Sound.title_screen;
        music.loop();

//      Tạo các button(Hành động)
        Button start_button = new Button();
//        Button AI_button = new Button();
        Button exit_button = new Button();
//      Player start
        start_button.setStyle("-fx-background-color: transparent; ");
        start_button.setPrefSize(166, 66);
        start_button.setTranslateX(5);
        start_button.setTranslateY(5);
        InputStream stream = null;
        try {
            stream = new FileInputStream("res/start.png");
        } catch (Exception e) {
            e.getMessage();
        }
        Image img = new Image(stream);
        ImageView view = new ImageView();
        view.setFitHeight(66);
        view.setFitWidth(166);
        view.setImage(img);
        start_button.setGraphic(view);
        start_button.setOnMouseEntered(e -> start_button.setEffect(new DropShadow()));
        start_button.setOnMouseExited(e -> start_button.setEffect(null));
        start_button.setOnAction(event -> {
            gameFunction = FUNCTION.PLAY;
            Platform.runLater(() -> {
                play(stage);
            });
        });

//      AI start
//        AI_button.setStyle("-fx-background-color: transparent; ");
//        AI_button.setPrefSize(166, 66);
//        AI_button.setTranslateX(5);
//        AI_button.setTranslateY(5 + 66 + 10);
//        try {
//            stream = new FileInputStream("res/AI_button.png");
//        } catch (Exception e) {
//            e.getMessage();
//        }
//        img = new Image(stream);
//        view = new ImageView();
//        view.setFitHeight(66);
//        view.setFitWidth(166);
//        view.setImage(img);
//        AI_button.setGraphic(view);

//      Exit
        exit_button.setStyle("-fx-background-color: transparent; ");
        exit_button.setPrefSize(166, 66);
        exit_button.setTranslateX(Sprite.SCALED_SIZE * 30 - 166 - 10);
        exit_button.setTranslateY(Sprite.SCALED_SIZE * 15 - 66 - 5);
        try {
            stream = new FileInputStream("res/exit.png");
        } catch (Exception e) {
            e.getMessage();
        }
        img = new Image(stream);
        view = new ImageView();
        view.setFitHeight(66);
        view.setFitWidth(166);
        view.setImage(img);
        exit_button.setGraphic(view);
        exit_button.setOnMouseEntered(e -> exit_button.setEffect(new DropShadow()));
        exit_button.setOnMouseExited(e -> exit_button.setEffect(null));
        exit_button.setOnAction(event -> {
            gameFunction = FUNCTION.EXIT;
        });

//      Background
        try {
            stream = new FileInputStream("res/menu.jpeg");
        } catch (Exception e) {
            e.getMessage();
        }
        Image image = new Image(stream);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setX(0);
        imageView.setY(0);
        imageView.setFitHeight(Sprite.SCALED_SIZE * 15);
        imageView.setFitWidth(Sprite.SCALED_SIZE * 30);

        this.root = new Group(imageView);
        this.root.getChildren().add(start_button);
//        this.root.getChildren().add(AI_button);
        this.root.getChildren().add(exit_button);
        Scene scene = new Scene(this.root, Sprite.SCALED_SIZE * 30, Sprite.SCALED_SIZE * 15);
        stage.setTitle("Bomberman L&N");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/Mew.jpg"));
        stage.setResizable(false);
        stage.show();
    }

    public void play(Stage stage) {
        AnimationTimer playTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (levelChanged) {
                    levelChanged = false;
                    load(stage, level);
                }
            }
        };
        playTimer.start();
    }

    public void end(Stage stage) {
        InputStream stream = null;
        if (music != null) music.stopSound();
        Button end_button = new Button();
        end_button.setStyle("-fx-background-color: transparent; ");
        end_button.setPrefSize(166, 66);
        if (gameFunction == FUNCTION.END) {
            music = Sound.stage_clear;
            music.playSound();
            try {
                stream = new FileInputStream("res/youwin.png");
            } catch (Exception e) {
                e.getMessage();
            }
        } else if (gameFunction == FUNCTION.REPLAY) {
            music = Sound.miss;
            music.playSound();
            try {
                stream = new FileInputStream("res/replay.png");
            } catch (Exception e) {
                e.getMessage();
            }
        }
        Image img = new Image(stream);
        ImageView view = new ImageView();
        view.setFitHeight(60);
        view.setFitWidth(170);
        view.setImage(img);
        end_button.setGraphic(view);
        end_button.setTranslateX(Sprite.SCALED_SIZE * 15 - 170 / 2);
        end_button.setTranslateY(Sprite.SCALED_SIZE * 10 - 10);
        end_button.setOnMouseEntered(e -> end_button.setEffect(new DropShadow()));
        end_button.setOnMouseExited(e -> end_button.setEffect(null));
        end_button.setOnAction(event -> {
            if (gameFunction == FUNCTION.END) {
                gameFunction = FUNCTION.MENU;
                level = 0;
                running = true;
                levelChanged = true;
            }
            if (gameFunction == FUNCTION.REPLAY) {
                gameFunction = FUNCTION.PLAY;
                play(stage);
            }
        });

        root = new Group();
        try {
            stream = new FileInputStream("res/endgame.jpeg");
        } catch (Exception e) {
            e.getMessage();
        }
        Image image = new Image(stream);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setX(0);
        imageView.setY(0);
        imageView.setFitHeight(Sprite.SCALED_SIZE * 15);
        imageView.setFitWidth(Sprite.SCALED_SIZE * 30);

        root.getChildren().add(imageView);
        root.getChildren().add(end_button);
        Scene scene = new Scene(root, Sprite.SCALED_SIZE * 30, Sprite.SCALED_SIZE * 15);
        stage.setTitle("Bomberman L&N");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/Mew.jpg"));
        stage.show();
    }

    public void gameLoop(Stage stage) {
        if (gameFunction == FUNCTION.MENU) {
            Platform.runLater(() -> {
                menu(stage);
            });
//            menu(stage);
        } else if (gameFunction == FUNCTION.PLAY) {
            Platform.runLater(() -> {
                play(stage);
            });
//            play(stage);
        } else if (gameFunction == FUNCTION.EXIT) {
            Platform.exit();
        }/* else {
//            System.out.println("error");
            throw new IllegalArgumentException("Invalid game state");
        }*/
    }

    public void load(Stage stage, int level) {
        stage.setTitle("Bomberman L&N");
        Image icon = new Image("/Mew.jpg");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        if (music != null) music.stopSound();
        music = Sound.main_bgm;
        music.loop();
        Bomb.cnt = 0;
        Map map = new Map(level);
        HEIGHT = map.getRows();
        WIDTH = map.getCols();
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT + 30);
        gc = canvas.getGraphicsContext2D();

        // Tao this.root container
        this.root = new Group();
        this.root.getChildren().add(canvas);
        // Tao scene
        Scene scene = new Scene(this.root);
        keyHandler = new KeyHandler(scene);
        map.createMap(keyHandler);
        items = map.getItems();
        stillObjects = map.getStillObjects();
        enemies = map.getEnemies();
        backGround = map.getBackGround();
        table = map.getTable();
        moveEntitiesTable = map.getMoveEntitiesTable();
        itemsTable = map.getItemsTable();
        player = map.getPlayer();

        // Add scene vao stage
//        stage.setTitle("Bomberman L&N");
        stage.setScene(scene);
//        Image icon = new Image("/Mew.jpg");
//        stage.getIcons().add(icon);
//        stage.setResizable(false);
//        stage.set
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (levelChanged) {
                    stop();
                    return;
                } else if (gameFunction == FUNCTION.MENU) {
                    stop();
                    return;
                } else {
                    update();
                    render(stage);
                }
                long frameTime = (now - lastUpdate) / 1000000;
//                System.out.println(frameTime);
                if (frameTime < FPS_GAME) {
                    try {
                        if (level > 0) Thread.sleep(FPS_GAME - (int) (frameTime / 3));
                        else {
                            if (frameTime * 3 < FPS_GAME) Thread.sleep(FPS_GAME - frameTime * 3);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                lastUpdate = System.nanoTime();
            }
        };
        timer.start();
    }

    @Override
    public void start(Stage stage) {
        AnimationTimer test = new AnimationTimer() {
            @Override
            public void handle(long ns) {
//                play(stage);
                if (running) {
                    gameLoop(stage);
                    running = false;
                }
                if (gameFunction == FUNCTION.EXIT) {
                    Platform.exit();
                }
            }
        };
        test.start();
    }

    public void update() {
        player.update();
        for (Entity enemy : enemies) {
            enemy.update();
        }
        for (Entity item : items) {
            item.update();
        }
        for (Entity entity : stillObjects) {
            entity.update();
        }
    }

    public void render(Stage stage) {
        if(gameFunction == FUNCTION.PLAY) {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            backGround.forEach((g -> g.render(gc)));
            items.forEach(g -> g.render(gc));
            stillObjects.forEach(g -> g.render(gc));
            enemies.forEach(g -> g.render(gc));
            player.render(gc);

            /*this.*/
            root.getChildren().remove(textLife);
            /*this.*/
            root.getChildren().remove(textEnemy);
            /*this.*/
            root.getChildren().remove(textLevel);

            Font font = new Font("pixels", 20);

            textLife = new Text(10, Sprite.SCALED_SIZE * HEIGHT + 22, "LIFE: " + player.getLife());
            textLife.setFont(font);
            textLife.setFill(Color.BLACK);

            textEnemy = new Text(100, Sprite.SCALED_SIZE * HEIGHT + 22, "ENEMY: " + enemies.size());
            textEnemy.setFont(font);
            textEnemy.setFill(Color.BLACK);

            textLevel = new Text(Sprite.SCALED_SIZE * WIDTH / 2 - 40, Sprite.SCALED_SIZE * HEIGHT + 22, "LEVEL: " + level);
            textLevel.setFont(font);
            textLevel.setFill(Color.BLACK);

            /*this.*/
            root.getChildren().add(textLevel);
            /*this.*/
            root.getChildren().add(textLife);
            /*this.*/
            root.getChildren().add(textEnemy);
        }
        if (gameFunction == FUNCTION.END) {
            if (endGame) {
                end(stage);
                endGame = false;
            }
        }
        if (gameFunction == FUNCTION.REPLAY) {
            if(replay) {
                end(stage);
                replay = false;
            }
        }
    }


    public static void addBomb(Entity bomb) {
        stillObjects.add(bomb);
    }

    public static void removeEnemy(Entity enemy) {
        enemies.remove(enemy);
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

    public enum FUNCTION {
        MENU, END, PLAY, EXIT, REPLAY
    }
}
