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
        if (beDamaged) {
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

    public  void moving() {
        chooseDirection();
        if (direction == 'D' && checkWall(x + 3, y + 1 + Sprite.SCALED_SIZE) && checkWall(x - 3 + Sprite.SCALED_SIZE, y + 1 + Sprite.SCALED_SIZE)) {
            y++;
        }
        if (direction == 'U' && checkWall(x + 3, y - 1) && checkWall(x - 3 + Sprite.SCALED_SIZE, y - 1)) {
            y--;
        }
        if (direction == 'L' && checkWall(x - 1, y + 3) && checkWall(x - 1, y - 3 + Sprite.SCALED_SIZE)) {
            x--;
        }
        else if (direction == 'R' && checkWall(x + 1 + Sprite.SCALED_SIZE, y + 3) && checkWall(x + 1 + Sprite.SCALED_SIZE, y - 3 + Sprite.SCALED_SIZE)) {
            x++;
        }
    }
    @Override
    public void update(Scene scene) {
        moving();
        chooseSprite();
        move = false;
    }
}
