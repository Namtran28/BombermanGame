package entities.items;

import graphics.Sprite;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import entities.Entity;

public class Portal extends Item {

    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        setChange(Sprite.portal.getFxImage());
    }
}
