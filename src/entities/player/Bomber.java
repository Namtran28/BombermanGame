package entities.player;

import graphics.Sprite;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import entities.Entity;
import playerInputs.KeyHandler;

public class Bomber extends Entity {
    private final KeyHandler keyHandler;
    private boolean move = false;
    private int animate = 0;

    public Bomber(int x, int y, Image img, KeyHandler keyHandler) {
        super( x, y, img);
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
                    keyHandler.isPressed(KeyCode.D) || keyHandler.isPressed(KeyCode.RIGHT))
            {
                return;
            }
            if (checkWall(x - 2 + 7, y + 7) && checkWall(x - 2 + 7, y + Sprite.SCALED_SIZE - 7)) {
                x -= 2;
                move = true;
                direction = 'L';
            }
        }
        else if (keyHandler.isPressed(KeyCode.D) || keyHandler.isPressed(KeyCode.RIGHT)) {
            if (keyHandler.isPressed(KeyCode.W) || keyHandler.isPressed(KeyCode.UP) ||
                    keyHandler.isPressed(KeyCode.S) || keyHandler.isPressed(KeyCode.DOWN) ||
                    keyHandler.isPressed(KeyCode.A) || keyHandler.isPressed(KeyCode.LEFT))
            {
                return;
            }
            if (checkWall(x + 2 + Sprite.SCALED_SIZE - 7, y + 7) && checkWall(x + 2 + Sprite.SCALED_SIZE - 7, y + Sprite.SCALED_SIZE - 5)) {
                x += 2;
                move = true;
                direction = 'R';
            }
        }
        else if (keyHandler.isPressed(KeyCode.W) || keyHandler.isPressed(KeyCode.UP)) {
            if (keyHandler.isPressed(KeyCode.A) || keyHandler.isPressed(KeyCode.LEFT) ||
                    keyHandler.isPressed(KeyCode.S) || keyHandler.isPressed(KeyCode.DOWN) ||
                    keyHandler.isPressed(KeyCode.D) || keyHandler.isPressed(KeyCode.RIGHT))
            {
                return;
            }
            if (checkWall(x + 7, y - 2 + 7) && checkWall(x + Sprite.SCALED_SIZE - 7, y - 2 + 5)) {
                y -= 2;
                move = true;
                direction = 'U';
            }
        }
        else if (keyHandler.isPressed(KeyCode.S) || keyHandler.isPressed(KeyCode.DOWN)) {
            if (keyHandler.isPressed(KeyCode.W) || keyHandler.isPressed(KeyCode.UP) ||
                    keyHandler.isPressed(KeyCode.A) || keyHandler.isPressed(KeyCode.LEFT) ||
                    keyHandler.isPressed(KeyCode.D) || keyHandler.isPressed(KeyCode.RIGHT))
            {
                return;
            }
            if (checkWall(x + 7, y + 2 + 7) && checkWall(x + Sprite.SCALED_SIZE - 7, y + 2 + Sprite.SCALED_SIZE - 5)) {
                y += 2;
                move = true;
                direction = 'D';
            }
        }

    }

}
