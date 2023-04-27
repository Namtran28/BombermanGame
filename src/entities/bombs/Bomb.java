package entities.bombs;

import entities.Entity;
import entities.tiles.Brick;
import entities.tiles.Wall;
import graphics.Sprite;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import main.BombermanGame;

import java.util.List;

public class Bomb extends Entity {
    private int animation = 0;
    private final int size;
    private boolean exploded = false;
    private final List<Entity> entities;

    public Bomb(int x, int y, Image img,List<Entity> entities, int size) {
        super(x, y, img);
        this.size = size;
        this.entities = entities;
    }

    public void getImage() {
        if (exploded) {
            sprite = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, animate, 20);
        } else {
            sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 20);
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

    @Override
    public void update(Scene scene) {
        animation++;
        int px = x / Sprite.SCALED_SIZE;
        int py = y / Sprite.SCALED_SIZE;

        if (animation == 70) {
            exploded = true;
            Platform.runLater(
                    () -> {
                        for (int c = 1; c <= size; c++) {
                            int i = x / Sprite.SCALED_SIZE - c, j = y / Sprite.SCALED_SIZE;
                            if (checkBreak(i, j)) break;
                            if (c < size) {
                                entities.add(new Flame(i, j, null, 'H', entities));
                            } else {
                                entities.add(new Flame(i, j, null, 'L', entities));
                            }
                        }
                        for (int c = 1; c <= size; c++) {
                            int i = x / Sprite.SCALED_SIZE + c, j = y / Sprite.SCALED_SIZE;
                            if (checkBreak(i, j)) break;
                            if (c < size) {
                                entities.add(new Flame(i, j, Sprite.explosion_horizontal.getFxImage(), 'H', entities));
                            } else {
                                entities.add(new Flame(i, j, Sprite.explosion_horizontal_right_last.getFxImage(), 'R', entities));
                            }
                        }
                        for (int c = 1; c <= size; c++) {
                            int i = x / Sprite.SCALED_SIZE, j = y / Sprite.SCALED_SIZE - c;
                            if (checkBreak(i, j)) break;
                            if (c < size) {
                                entities.add(new Flame(i, j, Sprite.explosion_vertical.getFxImage(), 'V', entities));
                            } else {
                                entities.add(new Flame(i, j, Sprite.explosion_vertical_top_last.getFxImage(), 'U', entities));
                            }
                        }
                        for (int c = 1; c <= size; c++) {
                            int i = x / Sprite.SCALED_SIZE, j = y / Sprite.SCALED_SIZE + c;
                            if (checkBreak(i, j)) break;
                            if (c < size) {
                                entities.add(new Flame(i, j, Sprite.explosion_vertical.getFxImage(), 'V', entities));
                            } else {
                                entities.add(new Flame(i, j, Sprite.explosion_vertical_down_last.getFxImage(), 'D', entities));
                            }
                        }
                    });
        }
        if (animation > 1000000) {
            animation = 0;
        }
        getImage();
    }
}
