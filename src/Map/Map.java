package Map;

import entities.Entity;
import entities.characters.*;
import entities.items.*;
import entities.player.Bomber;
import entities.tiles.*;
import graphics.Sprite;
import javafx.scene.Scene;
import playerInputs.KeyHandler;
import main.BombermanGame;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private char[][] map;
    private int level, rows, cols;

    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private List<Entity> items = new ArrayList<>();
    private List<Entity> backGround = new ArrayList<>();
    private char[][] table;

    private Entity item;
    private Entity object;
    private Entity enemy;

    private Entity player;


//    public static /*final*/ int WIDTH;
//    public static /*final*/ int HEIGHT;

    public void readMap(int level) {
        try {
            FileReader file = new FileReader("res\\levels\\Level" + level + ".txt");
            BufferedReader bufferReader = new BufferedReader(file);
            String s = bufferReader.readLine();
            String[] l = s.split(" ");
            this.level = Integer.parseInt(l[0]);
            this.rows = Integer.parseInt(l[1]);
            this.cols = Integer.parseInt(l[2]);

            map = new char[rows][cols];

            for (int i = 0; i < rows; i++) {
                s = bufferReader.readLine();
                for (int j = 0; j < cols; j++) {
                    map[i][j] = s.charAt(j);
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public List<Entity> getBackGround() {
        return backGround;
    }

    public void createMap(KeyHandler keyHandler) {
        //Map map = new Map();
//        int level = this.getLevel();
//        HEIGHT = this.getRows();
//        WIDTH = this.getCols();
//        char[][] _map = this.getMap();
        int xp = 0, yp = 0;
        table = new char[(cols + 1) * Sprite.SCALED_SIZE][(rows + 1) * Sprite.SCALED_SIZE];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                item = null;
                enemy = null;
                object = null;
                //Entity object;
                if (map[j][i] == '#') {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                } else if (map[j][i] == '*') {
                    object = new Brick(i, j, Sprite.brick.getFxImage());
                } else if (map[j][i] == 'x') {
                    item = new Portal(i, j, Sprite.portal.getFxImage());
                } else if (map[j][i] == 'p') {
                    xp = i; yp = j;
                    player = new Bomber(i, j, Sprite.player_right.getFxImage(), keyHandler);
                } else if (map[j][i] == '1') {
                    enemy = new Balloom(i, j, Sprite.balloom_left1.getFxImage());
                } else if (map[j][i] == '2') {
                    enemy = new Oneal(i, j, Sprite.oneal_left1.getFxImage());
                } else if (map[j][i] == '3') {
                    enemy = new Doll(i, j, Sprite.doll_left1.getFxImage());
                } else if (map[j][i] == '4') {
                    enemy = new Kondoria(i, j, Sprite.kondoria_left1.getFxImage());
                } else if (map[j][i] == 'b') {
                    item = new BombItem(i, j, Sprite.powerup_bombs.getFxImage());
                } else if (map[j][i] == 'f') {
                    item = new FlameItem(i, j, Sprite.powerup_flames.getFxImage());
                } else if (map[j][i] == 's') {
                    item = new SpeedItem(i, j, Sprite.powerup_speed.getFxImage());
                } else if (map[j][i] == 'm') {
                    item = new FlamePass(i, j, Sprite.powerup_flamepass.getFxImage());
                } else if (map[j][i] == 'w') {
                    item = new WallPass(i, j, Sprite.powerup_wallpass.getFxImage());
                } else {
                    draw(i, j, map[j][i]);
                }
                backGround.add(new Grass(i, j, Sprite.grass.getFxImage()));

                if (!(enemy == null)) {
                    entities.add(enemy);
                    stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                    draw(i, j, map[j][i]);
                }
                if (!(object == null)) {
                    stillObjects.add(object);
                    draw(i, j, map[j][i]);
                }
                if (!(item == null)) {
                    items.add(item);
                    stillObjects.add(new Brick(i, j, Sprite.brick.getFxImage()));
                    draw(i, j, '*');
                }
            }
        }
        entities.add(player);
        draw(xp, yp, map[yp][xp]);
    }

    public Map(int level) {
        readMap(level);
    }

    public char[][] getMap() {
        return map;
    }

    public int getLevel() {
        return level;
    }

    public int getRows() {
        return rows;
    }

//    public static int getHEIGHT() {
//        return HEIGHT;
//    }
//
//    public static int getWIDTH() {
//        return WIDTH;
//    }

    public char[][] getTable() {
        return table;
    }

    public int getCols() {
        return cols;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Entity> getStillObjects() {
        return stillObjects;
    }

    public List<Entity> getItems() {
        return items;
    }

    private void draw(int x, int y, char c) {
        for (int i = x * Sprite.SCALED_SIZE; i < (x + 1) * Sprite.SCALED_SIZE; i++) {
            for (int j = y * Sprite.SCALED_SIZE; j < (y + 1) * Sprite.SCALED_SIZE; j++) {
                table[i][j] = c;
            }
        }
    }
}
