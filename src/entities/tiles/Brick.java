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
                    //System.out.println("1");
                    int dx = x / Sprite.SCALED_SIZE;
                    int dy = y / Sprite.SCALED_SIZE;
                    BombermanGame.setTable(dy, dx, new Grass(dx, dy, Sprite.grass.getFxImage()));
                    for (Entity item : BombermanGame.getItems()) {
                        if (item.getX() == x && item.getY() == y) {
                            //System.out.println(dy + " " + dx + " " + item.getClass().getName());
                            BombermanGame.setTable(dy, dx, item);
                            break;
                        }
                    }
                    //System.out.println(dy + " " + dx + " " + BombermanGame.getTable()[dy][dx].getClass().getName());
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
