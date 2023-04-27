package entities.bombs;

import entities.Entity;
import graphics.Sprite;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.List;

public class Flame extends Entity {
    private final List<Entity> entities;
    private final char direction;
    private int animation;

    public Flame(int x, int y, Image img,char direction, List<Entity> entities) {
        super(x, y, img);
        this.entities = entities;
        this.direction = direction;
    }

    public void getImage() {
        Sprite sprite = null;
        if (direction == 'U') {
            sprite = Sprite.movingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, animate, 20);
        } else if (direction == 'D') {
            sprite = Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, animate, 20);
        } else if (direction == 'L') {
            sprite = Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2, animate, 20);
        } else if (direction == 'R') {
            sprite = Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, animate, 20);
        } else if (direction == 'H') {
            sprite = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, animate, 20);
        } else if (direction == 'V') {
            sprite = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, animate, 20);
        }
        img = sprite.getFxImage();
    }
    @Override
    public void update(Scene scene) {
        animate++;
        if (animate == 10) {
            Platform.runLater(() -> entities.remove(this));
        }
        getImage();
    }
}
