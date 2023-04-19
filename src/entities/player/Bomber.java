package entities.player;

import graphics.Sprite;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import entities.Entity;
import playerInputs.KeyHandler;
import main.BombermanGame;

public class Bomber extends Entity {
    private KeyHandler keyHandler;
    private boolean move = false;
    private char direction;
    private int animate = 0;

    public Bomber(int x, int y, Image img, KeyHandler keyHandler) {
        super( x, y, img);
        this.keyHandler = keyHandler;
    }

    private void chooseSprite() {
        animate++;

        Sprite sprite;
        switch (direction) {
            case 'U':
                sprite = Sprite.player_up;
                if (move) {
                    sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 20);
                }
                break;
            case 'D':
                sprite = Sprite.player_down;
                if (move) {
                    sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 20);
                }
                break;
            case 'L':
                sprite = Sprite.player_left;
                if (move) {
                    sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 20);
                }
                break;
            default:
                sprite = Sprite.player_right;
                if (move) {
                    sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
                }
                break;
        }
        img = sprite.getFxImage();
        move = false;
    }

    @Override
    public void update(Scene scene) {
        chooseSprite();
        if (keyHandler.isPressed(KeyCode.A) || keyHandler.isPressed(KeyCode.LEFT)) {
            if (checkWall(x - 2, y) && checkWall(x - 2, y + Sprite.SCALED_SIZE)) {
                x -= 2;
                move = true;
                direction = 'L';
            }
        }
        if (keyHandler.isPressed(KeyCode.D) || keyHandler.isPressed(KeyCode.RIGHT)) {
            if (checkWall(x + 2 + Sprite.SCALED_SIZE - 5, y) && checkWall(x + 2 + Sprite.SCALED_SIZE - 5, y + Sprite.SCALED_SIZE)) {
                x += 2;
                move = true;
                direction = 'R';
            }
        }
        if (keyHandler.isPressed(KeyCode.W) || keyHandler.isPressed(KeyCode.UP)) {
            if (checkWall(x, y - 2) && checkWall(x + Sprite.SCALED_SIZE - 5, y - 2)) {
                y -= 2;
                move = true;
                direction = 'U';
            }
        }
        if (keyHandler.isPressed(KeyCode.S) || keyHandler.isPressed(KeyCode.DOWN)) {
            if (checkWall(x, y + 2 + Sprite.SCALED_SIZE) && checkWall(x + Sprite.SCALED_SIZE - 5, y + 2 + Sprite.SCALED_SIZE)) {
                y += 2;
                move = true;
                direction = 'D';
            }
        }

    }

}
