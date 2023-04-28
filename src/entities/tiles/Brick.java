package entities.tiles;

import graphics.Sprite;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import entities.Entity;
import main.BombermanGame;

import java.util.List;

public class Brick extends Entity {
    private boolean exploded = false;
    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    public void setExploded() {
        exploded = true;
    }
    public void beExploded() {
        if (exploded) {
            Sprite sprite = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animate, 20);
            img = sprite.getFxImage();
            animate++;
            if (animate == 10) {
                Platform.runLater(() -> {
                    int dx = x / Sprite.SCALED_SIZE;
                    int dy = y / Sprite.SCALED_SIZE;
                    for (Entity item : BombermanGame.getItems()) {
                        if (item.getX() == x && item.getY() == y) {
                            BombermanGame.setTable(dy, dx, item);
                            break;
                        }
                    }
                    BombermanGame.removeBrick(this);
                });
            }
        }
    }
    @Override
    public void update(Scene scene) {
        beExploded();
    }
}
