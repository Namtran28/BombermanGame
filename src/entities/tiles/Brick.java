package entities.tiles;

import graphics.Sprite;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import entities.Entity;
import main.BombermanGame;

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
                    BombermanGame.setTable(dy, dx, new Grass(dx, dy, Sprite.grass.getFxImage()));
                    Entity item = BombermanGame.getItemsTable()[dy][dx];
                    if (item != null) {
                        BombermanGame.setTable(dy, dx, item);
                        BombermanGame.setItemsTable(dy, dx, null);
                    }
                    BombermanGame.removeBrick(this);
                });
            }
        }
    }
    @Override
    public void update() {
        beExploded();
    }
}
