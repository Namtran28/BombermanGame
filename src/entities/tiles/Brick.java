package entities.tiles;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import entities.Entity;

public class Brick extends Entity {
    private boolean exploded = false;
    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    public void setExploded() {
        exploded = true;
    }
    @Override
    public void update(Scene scene) {

    }
}
