package entities.bombs;

import entities.Entity;
import entities.items.Item;
import entities.items.Portal;
import entities.tiles.Brick;
import entities.tiles.Wall;
import graphics.Sprite;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import main.BombermanGame;
import sound.Sound;

import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Entity {
    private final int size;
//    private int animate = 0;
    private boolean exploded = false;

    public static int cnt = 0;
//    private final List<Entity> entities;

    private Entity current;

    public Bomb(int x, int y, Image img/*,List<Entity> entities*/, int size) {
        super(x, y, img);
        this.size = size;
        current = BombermanGame.getTable()[y][x];
        BombermanGame.setTable(y, x, this);
        cnt++;
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
        //if (cur != null) System.out.println(x + " " + y + " " + cur.getClass().getName());
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
        Entity enemy = BombermanGame.getMoveEntitiesTable()[j][i];
        if (enemy != null) {
            for (Entity e : BombermanGame.getEnemies()) {
                if (e.getXUnit() == i && e.getYUnit() == j) {
                    e.damaged();
                }
            }
        }
        if (cur instanceof Item && !(cur instanceof Portal)) ((Item) cur).setIsPassed();
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
        if (animate == 70) {
            exploded = true;
            Sound.explosion.playSound();
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
        }
        if (animate == 80) {
            Platform.runLater(() -> {
                cnt--;
                BombermanGame.setTable(y / Sprite.SCALED_SIZE, x / Sprite.SCALED_SIZE, current);
                BombermanGame.removeFlames(this);
            });
        }
        if (animate > 100000) {
            animate = 0;
        }
        getImage();
    }
}
