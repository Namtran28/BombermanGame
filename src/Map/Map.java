package Map;

import entities.Entity;
import entities.characters.*;
import entities.items.*;
import entities.player.Bomber;
import entities.tiles.*;
import graphics.Sprite;
import playerInputs.KeyHandler;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private char[][] map;
    private int rows, cols;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private List<Entity> items = new ArrayList<>();
    private List<Entity> backGround = new ArrayList<>();
    private Entity player = new Bomber();
    private Entity[][] table;


    public void readMap(int level) {
        try {
            FileReader file = new FileReader("res\\levels\\Level" + level + ".txt");
            BufferedReader bufferReader = new BufferedReader(file);
            String s = bufferReader.readLine();
            String[] l = s.split(" ");
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
        Entity item;
        Entity object;
        Entity enemy;
        int xp = 0, yp = 0;
        table = new Entity[rows][cols];
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
                    table[j][i] = new Grass(i, j, Sprite.grass.getFxImage());
                    player = new Bomber(i, j, Sprite.player_right.getFxImage(), 3, keyHandler);
                } else if (map[j][i] == '1') {
                    enemy = new Balloom(i, j, Sprite.balloom_left1.getFxImage(), 1);
                } else if (map[j][i] == '2') {
                    enemy = new Oneal(i, j, Sprite.oneal_left1.getFxImage(), 1);
                } else if (map[j][i] == '3') {
                    enemy = new Doll(i, j, Sprite.doll_left1.getFxImage(), 2);
                } else if (map[j][i] == '4') {
                    enemy = new Kondoria(i, j, Sprite.kondoria_left1.getFxImage(), 1);
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
                    table[j][i] = new Grass(i, j, Sprite.grass.getFxImage());
                }
                backGround.add(new Grass(i, j, Sprite.grass.getFxImage()));

                if (!(enemy == null)) {
                    entities.add(enemy);
                    stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                    table[j][i] = new Grass(i, j, Sprite.grass.getFxImage());
                }
                if (!(object == null)) {
                    stillObjects.add(object);
                    table[j][i] = object;
                }
                if (!(item == null)) {
                    items.add(item);
                    stillObjects.add(new Brick(i, j, Sprite.brick.getFxImage()));
                    table[j][i] = new Brick(i, j, Sprite.brick.getFxImage());
                }
            }
        }
    }

    public Map(int level) {
        readMap(level);
    }

    public int getRows() {
        return rows;
    }

    public Entity[][] getTable() {
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

    public Entity getPlayer() {
        return player;
    }
}
