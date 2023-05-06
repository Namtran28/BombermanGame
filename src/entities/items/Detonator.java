package entities.items;

import graphics.Sprite;
import javafx.scene.image.Image;

public class Detonator extends Item {
    public Detonator(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        setChange(Sprite.powerup_detonator.getFxImage());
    }
}
