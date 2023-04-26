package entities.characters;

import graphics.Sprite;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Doll extends Enemy {
    public Doll(int x, int y, Image image, int life) {
        super(x, y, image, life);
    }

    @Override
    public void chooseSprite() {
        animate++;
        if (animate > 100000) animate = 0;
        if (beDamaged) {
            img = Sprite.movingSprite(Sprite.doll_dead,
                    Sprite.mob_dead2,
                    Sprite.mob_dead3,
                    animate,
                    20).getFxImage();
            return;
        }
        Sprite sprite;
        switch (direction) {
            case 'D', 'L' -> {
                sprite = Sprite.doll_left1;
                if (move) {
                    sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animate, 20);
                }
            }

            default -> {
                sprite = Sprite.doll_right1;
                if (move) {
                    sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animate, 20);
                }
            }
        }
        move = false;
        img = sprite.getFxImage();
    }

    public void moving() {
        chooseDirection();
        if (direction == 'D') {
            if (checkWall(x + 3, y + 1 + Sprite.SCALED_SIZE) && checkWall(x - 3 + Sprite.SCALED_SIZE, y + 1 + Sprite.SCALED_SIZE)) {
                y++;
                move = true;
            }
            if (!(checkWall(x + 3, y + Sprite.SCALED_SIZE + 1))) {
                if ((double) ((x + 3) * 1.0 / Sprite.SCALED_SIZE) > (double) (((int) (x + 3) / Sprite.SCALED_SIZE) + 0.1)) {
                    direction = 'R';
                    move = true;
                    x++;
                }
            } else if (!(checkWall(x + Sprite.SCALED_SIZE - 3, y + Sprite.SCALED_SIZE + 1))) {
                if ((double) ((x + Sprite.SCALED_SIZE - 3) * 1.0 / Sprite.SCALED_SIZE) < (double) ((int) (x + Sprite.SCALED_SIZE - 3) / Sprite.SCALED_SIZE) + 0.1) {
                    direction = 'L';
                    move = true;
                    x--;
                }
            }
        }
        if (direction == 'U') {
            if (checkWall(x + 3, y - 1) && checkWall(x - 3 + Sprite.SCALED_SIZE, y - 1)) {
                y--;
                move = true;
            }
            if (!(checkWall(x + 3, y - 1))) {
                if ((double) ((x + 3) * 1.0 / Sprite.SCALED_SIZE) > (double) (((int) (x + 3) / Sprite.SCALED_SIZE) + 0.1)) {
                    direction = 'R';
                    move = true;
                    x++;
                }
            } else if (!(checkWall(x + Sprite.SCALED_SIZE - 3, y - 1))) {
                if ((double) ((x + Sprite.SCALED_SIZE - 3) * 1.0 / Sprite.SCALED_SIZE) < (double) ((int) (x + Sprite.SCALED_SIZE - 3) / Sprite.SCALED_SIZE) + 0.1) {
                    direction = 'L';
                    move = true;
                    x--;
                }
            }
        }
        if (direction == 'L') {
            if (checkWall(x - 1, y + 3) && checkWall(x - 1, y - 3 + Sprite.SCALED_SIZE)) {
                x--;
                move = true;
            }
            if (!(checkWall(x - 1, y + 3))) {
                if ((double) ((y + 3) * 1.0 / Sprite.SCALED_SIZE) > (double) (((int) (y + 3) / Sprite.SCALED_SIZE) + 0.1)) {
                    direction = 'D';
                    move = true;
                    y++;
                }
            } else if (!(checkWall(x - 1, y + Sprite.SCALED_SIZE - 3))) {
                if ((double) ((y + Sprite.SCALED_SIZE - 3) * 1.0 / Sprite.SCALED_SIZE) < (double) ((int) (y + Sprite.SCALED_SIZE - 3) / Sprite.SCALED_SIZE) + 0.1) {
                    direction = 'U';
                    move = true;
                    y--;
                }
            }
        }
        if (direction == 'R') {
            if (checkWall(x + 1 + Sprite.SCALED_SIZE, y + 3) && checkWall(x + 1 + Sprite.SCALED_SIZE, y - 3 + Sprite.SCALED_SIZE)) {
                x++;
                move = true;
            }
            if (!(checkWall(x + Sprite.SCALED_SIZE + 1, y + 3))) {
                if ((double) ((y + 3) * 1.0 / Sprite.SCALED_SIZE) > (double) (((int) (y + 3) / Sprite.SCALED_SIZE) + 0.1)) {
                    direction = 'D';
                    move = true;
                    y++;
                }
            } else if (!(checkWall(x + Sprite.SCALED_SIZE + 1, y + Sprite.SCALED_SIZE - 3))) {
                if ((double) ((y + Sprite.SCALED_SIZE - 3) * 1.0 / Sprite.SCALED_SIZE) < (double) ((int) (y + Sprite.SCALED_SIZE - 3) / Sprite.SCALED_SIZE) + 0.1) {
                    direction = 'U';
                    move = true;
                    y--;
                }
            }
        }
    }

    @Override
    public void update(Scene scene) {
        moving();
        chooseSprite();
        move = false;
    }
}
