package entities.bombs;

import entities.Entity;
import entities.characters.Enemy;
import entities.items.Item;
import entities.player.Bomber;
import entities.tiles.Brick;
import entities.tiles.Grass;
import entities.tiles.Wall;
import graphics.Sprite;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import main.BombermanGame;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Entity {
    private final int size;
//    private int animate = 0;
    private boolean exploded = false;
//    private final List<Entity> entities;
    private Entity cur;

    public Bomb(int x, int y, Image img/*,List<Entity> entities*/, int size) {
        super(x, y, img);
        this.size = size;
        cur = BombermanGame.getTable()[y][x];
        BombermanGame.setTable(y, x, this);
//        this.entities = entities;
    }

    public void getImage() {
        if (exploded) {
            sprite = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, animate, 20);
        } else {
            sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 15);
        }
        img = sprite.getFxImage();
    }

    private boolean checkBreak(int x, int y) {
        Entity cur = BombermanGame.getTable()[y][x];
        if (cur instanceof Wall) {
            return true;
        }
        if (cur instanceof Brick) {
            ((Brick) cur).setExploded();
            return true;
        }
        return false;
    }

    public boolean isExploded() {
        return exploded;
    }
    private void damage(int i, int j) {
        Entity cur = BombermanGame.getTable()[j][i];
        //if (cur instanceof Enemy) {
            for (Entity e : BombermanGame.getEnemies()) {
                if (e.getXUnit() == i && e.getYUnit() == j) {
                    e.damaged();
                }
            }
        //}
//        if (cur instanceof Oneal) cur.setDied();
//        if (cur instanceof Item) cur.setDied();
//        if (cur instanceof SpeedItem) cur.setDied();
//        if (cur instanceof BombItem) cur.setDied();
        if (cur instanceof Bomb && !((Bomb) cur).isExploded()) ((Bomb) cur).setExplode();
        if (BombermanGame.player.getXUnit() == i &&
                BombermanGame.player.getYUnit() == j &&
                !BombermanGame.player.isFlamePass() &&
                !BombermanGame.player.isUnDead()) {
            BombermanGame.player.damaged();
        }
    }

    public void setExplode() {
        this.animate = 69;
    }

    @Override
    public void update(Scene scene) {
        animate++;
        int px = x / Sprite.SCALED_SIZE;
        int py = y / Sprite.SCALED_SIZE;

        if (animate == 70) {
            exploded = true;
            Platform.runLater(
                    () -> {
                        for (int c = 1; c <= size; c++) {
                            int i = x / Sprite.SCALED_SIZE - c, j = y / Sprite.SCALED_SIZE;
                            if (checkBreak(i, j)) break;
                            if (c < size) {
                                BombermanGame.addBomb(new Flame(i, j, Sprite.explosion_horizontal.getFxImage(), 'H'));
                            } else {
                                BombermanGame.addBomb(new Flame(i, j, Sprite.explosion_horizontal_left_last.getFxImage(), 'L'));
                            }
                        }
                        for (int c = 1; c <= size; c++) {
                            int i = x / Sprite.SCALED_SIZE + c, j = y / Sprite.SCALED_SIZE;
                            if (checkBreak(i, j)) break;
                            if (c < size) {
                                BombermanGame.addBomb(new Flame(i, j, Sprite.explosion_horizontal.getFxImage(), 'H'));
                            } else {
                                BombermanGame.addBomb(new Flame(i, j, Sprite.explosion_horizontal_right_last.getFxImage(), 'R'));
                            }
                        }
                        for (int c = 1; c <= size; c++) {
                            int i = x / Sprite.SCALED_SIZE, j = y / Sprite.SCALED_SIZE - c;
                            if (checkBreak(i, j)) break;
                            if (c < size) {
                                BombermanGame.addBomb(new Flame(i, j, Sprite.explosion_vertical.getFxImage(), 'V'));
                            } else {
                                BombermanGame.addBomb(new Flame(i, j, Sprite.explosion_vertical_top_last.getFxImage(), 'U'));
                            }
                        }
                        for (int c = 1; c <= size; c++) {
                            int i = x / Sprite.SCALED_SIZE, j = y / Sprite.SCALED_SIZE + c;
                            if (checkBreak(i, j)) break;
                            if (c < size) {
                                BombermanGame.addBomb(new Flame(i, j, Sprite.explosion_vertical.getFxImage(), 'V'));
                            } else {
                                BombermanGame.addBomb(new Flame(i, j, Sprite.explosion_vertical_down_last.getFxImage(), 'D'));
                            }
                        }
                        Timer bombTimer = new Timer();
                        bombTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                for (int c = 0; c <= size; c++) {
                                    int i = x / Sprite.SCALED_SIZE - c, j = y / Sprite.SCALED_SIZE;
                                    if (checkBreak(i, j)) break;
                                    damage(i, j);
                                }
                                for (int c = 1; c <= size; c++) {
                                    int i = x / Sprite.SCALED_SIZE + c, j = y / Sprite.SCALED_SIZE;
                                    if (checkBreak(i, j)) break;
                                    damage(i, j);
                                }
                                for (int c = 1; c <= size; c++) {
                                    int i = x / Sprite.SCALED_SIZE, j = y / Sprite.SCALED_SIZE - c;
                                    if (checkBreak(i, j)) break;
                                    damage(i, j);
                                }
                                for (int c = 1; c <= size; c++) {
                                    int i = x / Sprite.SCALED_SIZE, j = y / Sprite.SCALED_SIZE + c;
                                    if (checkBreak(i, j)) break;
                                    damage(i, j);
                                }
                            }
                        }, 10);
                    });
//            Platform.runLater(() -> {
//                BombermanGame.removeFlames(this);
//            });
        }
        if (animate == 80) {
            Platform.runLater(() -> {
                BombermanGame.removeFlames(this);
                BombermanGame.player.reduceBombCounter();
                BombermanGame.setTable(py, px, cur);
            });
        }
        if (animate > 100000) {
            animate = 0;
        }
        getImage();
    }
}
