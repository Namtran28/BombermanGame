package entities.characters;

import graphics.Sprite;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Kondoria extends Enemy {
    public Kondoria(int x, int y, Image image, int life) {
        super(x, y, image, life);
    }

    @Override
    public void chooseSprite() {
        animate++;
        if (animate > 100000) animate = 0;
        if (died) {
            img = Sprite.movingSprite(Sprite.kondoria_dead,
                    Sprite.mob_dead2,
                    Sprite.mob_dead3,
                    animate,
                    20).getFxImage();
            return;
        }
        Sprite sprite;
        switch (direction) {
            case 'D', 'L' -> {
                sprite = Sprite.kondoria_left1;
                if (move) {
                    sprite = Sprite.movingSprite(Sprite.kondoria_left3, Sprite.kondoria_left2, Sprite.kondoria_left3, animate, 20);
                }
            }

            default -> {
                sprite = Sprite.kondoria_right1;
                if (move) {
                    sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, animate, 20);
                }
            }
        }
        move = false;
        img = sprite.getFxImage();
    }

    @Override
    public void update(Scene scene) {
        if (died) {
            gotHurt(Sprite.kondoria_dead);
            chooseSprite();
            return;
        }
        moving();
        chooseSprite();
        move = false;
    }
}
