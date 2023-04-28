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
        if (exploded == true) {
            Sprite sprite = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animate, 20);
            img = sprite.getFxImage();
            animate++;
            if (animate == 10) {
                Platform.runLater(() -> {
                    int dx = x / Sprite.SCALED_SIZE;
                    int dy = x / Sprite.SCALED_SIZE;
                    BombermanGame.removeBrick(this);
                    for (Entity item : BombermanGame.getItems()) {
                        if (item.getX() / Sprite.SCALED_SIZE == x && item.getY() / Sprite.SCALED_SIZE == y) {
                            BombermanGame.setTable(y, x, item);
                            break;
                        }
                    }
                });
            }
        }
    }
    @Override
    public void update(Scene scene) {
        beExploded();
    }
}
