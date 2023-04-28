package entities.items;

import graphics.Sprite;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class SpeedItem extends Item {
    public SpeedItem(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update(Scene scene) {
        setChange(Sprite.powerup_speed.getFxImage());
    }
}
