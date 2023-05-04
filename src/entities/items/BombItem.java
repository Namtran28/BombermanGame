package entities.items;

import graphics.Sprite;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class BombItem extends Item {
    public BombItem(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        setChange(Sprite.powerup_bombs.getFxImage());
    }
}
