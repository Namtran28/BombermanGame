package Map;

import entities.Entity;
import entities.characters.Balloom;
import entities.characters.Doll;
import entities.characters.Kondoria;
import entities.characters.Oneal;
import entities.items.*;
import entities.player.Bomber;
import entities.tiles.Brick;
import entities.tiles.Grass;
import entities.tiles.Portal;
import entities.tiles.Wall;
import graphics.Sprite;
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
    public static /*final*/ int WIDTH;
    public static /*final*/ int HEIGHT;

    public void loadMap() {
        try {
            FileReader file = new FileReader("res\\levels\\Level1.txt");
            BufferedReader bufferReader = new BufferedReader(file);
            String s = bufferReader.readLine();
            String[] l = s.split(" ");
            level = Integer.parseInt(l[0]);
            rows = Integer.parseInt(l[1]);
            cols = Integer.parseInt(l[2]);

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

    public void createMap() {
        //Map map = new Map();
        int level = this.getLevel();
        HEIGHT = this.getRows();
        WIDTH = this.getCols();
        char[][] _map = this.getMap();

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                //Entity object;
                if (_map[j][i] == '#') {
                    stillObjects.add(new Wall(i, j, Sprite.wall.getFxImage()));
                } else if (_map[j][i] == '*') {
                    stillObjects.add(new Brick(i, j, Sprite.brick.getFxImage()));
                } else if (_map[j][i] == 'x') {
                    stillObjects.add(new Portal(i, j, Sprite.portal.getFxImage()));
                    entities.add(new Brick(i, j, Sprite.brick.getFxImage()));
                } else if (_map[j][i] == 'p') {
                    stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                    //stillObjects.add(object);
                    entities.add(new Bomber(i, j, Sprite.player_right.getFxImage()));
                } else if (_map[j][i] == '1') {
                    stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                    entities.add(new Balloom(i, j, Sprite.balloom_left1.getFxImage()));
                } else if (_map[j][i] == '2') {
                    stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                    entities.add(new Oneal(i, j, Sprite.oneal_left1.getFxImage()));
                } else if (_map[j][i] == '3') {
                    stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                    entities.add(new Doll(i, j, Sprite.doll_left1.getFxImage()));
                } else if (_map[j][i] == '4') {
                    stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                    entities.add(new Kondoria(i, j, Sprite.kondoria_left1.getFxImage()));
                } else if (_map[j][i] == 'b') {
                    entities.add(new Brick(i, j, Sprite.brick.getFxImage()));
                    items.add(new BombItem(i, j, Sprite.powerup_bombs.getFxImage()));
                } else if (_map[j][i] == 'f') {
                    items.add(new FlameItem(i, j, Sprite.powerup_flames.getFxImage()));
                    entities.add(new Brick(i, j, Sprite.brick.getFxImage()));
                } else if (_map[j][i] == 's') {
                    items.add(new SpeedItem(i, j, Sprite.powerup_speed.getFxImage()));
                    entities.add(new Brick(i, j, Sprite.brick.getFxImage()));
                } else if (_map[j][i] == 'm') {
                    items.add(new FlamePass(i, j, Sprite.powerup_flamepass.getFxImage()));
                    entities.add(new Brick(i, j, Sprite.brick.getFxImage()));
                } else if (_map[j][i] == 'w') {
                    items.add(new WallPass(i, j, Sprite.powerup_wallpass.getFxImage()));
                    entities.add(new Brick(i, j, Sprite.brick.getFxImage()));
                } else {
                    stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                }
                //stillObjects.add(object);
            }
        }
    }


    public Map() {
        loadMap();
        createMap();
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

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getWIDTH() {
        return WIDTH;
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
}
