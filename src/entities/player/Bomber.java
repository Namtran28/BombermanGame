package entities.player;

import entities.tiles.Grass;
import graphics.Sprite;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import entities.Entity;
import main.BombermanGame;
import playerInputs.KeyHandler;

public class Bomber extends Entity {
    private final KeyHandler keyHandler;
    private boolean move = false;
    private int animate = 0;

    public Bomber(int x, int y, Image img, KeyHandler keyHandler) {
        super(x, y, img);
        this.keyHandler = keyHandler;
    }

    private void chooseSprite() {
        animate++;
        if (animate > 100000) animate = 0;
        Sprite sprite;
        switch (direction) {
            case 'U' -> {
                sprite = Sprite.player_up;
                if (move) {
                    sprite = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, animate, 20);
                }
            }
            case 'D' -> {
                sprite = Sprite.player_down;
                if (move) {
                    sprite = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, animate, 20);
                }
            }
            case 'L' -> {
                sprite = Sprite.player_left;
                if (move) {
                    sprite = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, animate, 20);
                }
            }
            default -> {
                sprite = Sprite.player_right;
                if (move) {
                    sprite = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, animate, 20);
                }
            }
        }
        img = sprite.getFxImage();
        move = false;
    }

    @Override
    public void update(Scene scene) {
        chooseSprite();
        if (keyHandler.isPressed(KeyCode.A) || keyHandler.isPressed(KeyCode.LEFT)) {
            if (keyHandler.isPressed(KeyCode.W) || keyHandler.isPressed(KeyCode.UP) ||
                    keyHandler.isPressed(KeyCode.S) || keyHandler.isPressed(KeyCode.DOWN) ||
                    keyHandler.isPressed(KeyCode.D) || keyHandler.isPressed(KeyCode.RIGHT)) {
                return;
            }
            if (checkWall(x - 2, y + 4) && checkWall(x - 2, y + Sprite.SCALED_SIZE - 4)) {
                x -= 2;
                move = true;
                direction = 'L';
            }
            if (!(checkWall(x - 2, y + 4))) {
                if ((double)((y + 4) * 1.0/ Sprite.SCALED_SIZE)  > (double)(((int)(y + 4) / Sprite.SCALED_SIZE) + 0.5)) {
                    y++;
                }
            } else if (!(checkWall(x - 2, y + Sprite.SCALED_SIZE - 4))) {
                if ((double)((y + Sprite.SCALED_SIZE - 4) * 1.0 / Sprite.SCALED_SIZE) < (double)((int)(y + Sprite.SCALED_SIZE - 4) / Sprite.SCALED_SIZE) + 0.5) {
                    y--;
                }
            }

        } else if (keyHandler.isPressed(KeyCode.D) || keyHandler.isPressed(KeyCode.RIGHT)) {
            if (keyHandler.isPressed(KeyCode.W) || keyHandler.isPressed(KeyCode.UP) ||
                    keyHandler.isPressed(KeyCode.S) || keyHandler.isPressed(KeyCode.DOWN) ||
                    keyHandler.isPressed(KeyCode.A) || keyHandler.isPressed(KeyCode.LEFT)) {
                return;
            }
            if (checkWall(x + Sprite.SCALED_SIZE + 2 - 5, y + 4) && checkWall(x + Sprite.SCALED_SIZE + 2 - 5, y + Sprite.SCALED_SIZE - 4)) {
                x += 2;
                move = true;
                direction = 'R';
            }
            if (!(checkWall(x + Sprite.SCALED_SIZE + 2 - 5, y + 4))) {
                if ((double)((y + 4) * 1.0/ Sprite.SCALED_SIZE)  > (double)(((int)(y + 4) / Sprite.SCALED_SIZE) + 0.5)) {
                    y++;
                }
            } else if (!(checkWall(x + Sprite.SCALED_SIZE + 2 - 5, y + Sprite.SCALED_SIZE - 4))) {
                if ((double)((y + Sprite.SCALED_SIZE - 4) * 1.0 / Sprite.SCALED_SIZE) < (double)((int)(y + Sprite.SCALED_SIZE - 4) / Sprite.SCALED_SIZE) + 0.5) {
                    y--;
                }
            }
        } else if (keyHandler.isPressed(KeyCode.W) || keyHandler.isPressed(KeyCode.UP)) {
            if (keyHandler.isPressed(KeyCode.A) || keyHandler.isPressed(KeyCode.LEFT) ||
                    keyHandler.isPressed(KeyCode.S) || keyHandler.isPressed(KeyCode.DOWN) ||
                    keyHandler.isPressed(KeyCode.D) || keyHandler.isPressed(KeyCode.RIGHT)) {
                return;
            }
            if (checkWall(x + 4, y - 2) && checkWall(x + Sprite.SCALED_SIZE - 5, y - 2)) {
                y -= 2;
                move = true;
                direction = 'U';
            }
            if (!(checkWall(x + 4, y - 2))) {
                if ((double)((x + 4) * 1.0/ Sprite.SCALED_SIZE)  > (double)(((int)(x + 4) / Sprite.SCALED_SIZE) + 0.5)) {
                    x++;
                }
            } else if (!(checkWall(x + Sprite.SCALED_SIZE - 5, y - 2))) {
                if ((double)((x + Sprite.SCALED_SIZE - 5) * 1.0 / Sprite.SCALED_SIZE) < (double)((int)(x + Sprite.SCALED_SIZE - 5) / Sprite.SCALED_SIZE) + 0.5) {
                    x--;
                }
            }
        } else if (keyHandler.isPressed(KeyCode.S) || keyHandler.isPressed(KeyCode.DOWN)) {
            if (keyHandler.isPressed(KeyCode.W) || keyHandler.isPressed(KeyCode.UP) ||
                    keyHandler.isPressed(KeyCode.A) || keyHandler.isPressed(KeyCode.LEFT) ||
                    keyHandler.isPressed(KeyCode.D) || keyHandler.isPressed(KeyCode.RIGHT)) {
                return;
            }
            if (checkWall(x + 4, y + Sprite.SCALED_SIZE + 2) && checkWall(x + Sprite.SCALED_SIZE - 5, y + Sprite.SCALED_SIZE + 2)) {
                y += 2;
                move = true;
                direction = 'D';
            }
            if (!(checkWall(x + 4, y + Sprite.SCALED_SIZE + 2))) {
                if ((double)((x + 4) * 1.0/ Sprite.SCALED_SIZE)  > (double)(((int)(x + 4) / Sprite.SCALED_SIZE) + 0.5)) {
                    x++;
                }
            } else if (!(checkWall(x + Sprite.SCALED_SIZE - 5, y + Sprite.SCALED_SIZE + 2))) {
                if ((double)((x + Sprite.SCALED_SIZE - 5) * 1.0 / Sprite.SCALED_SIZE) < (double)((int)(x + Sprite.SCALED_SIZE - 5) / Sprite.SCALED_SIZE) + 0.5) {
                    x--;
                }
            }
        }
    }
}
