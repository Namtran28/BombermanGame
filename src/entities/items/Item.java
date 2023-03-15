package entities.items;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import entities.Entity;

public class Item extends Entity {

    public Item(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {

    }
}
