package entities.items;

import entities.tiles.Grass;
import graphics.Sprite;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import entities.Entity;
import main.BombermanGame;
import sound.Sound;

import static main.BombermanGame.music;

public class Item extends Entity {

    public Item(int x, int y, Image img) {
        super( x, y, img);
    }
    protected boolean isPassed = false;

    @Override
    public void update(Scene scene) {

    }

    public void setIsPassed() {
        this.isPassed = true;
        Sound.collect_item.playSound();
        music.stopSound();
        music = Sound.powerup_get;
        music.loop();
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setChange(Image image) {
        img = image;
        if (isPassed) {
            Platform.runLater(() -> {
                BombermanGame.setTable(y / Sprite.SCALED_SIZE,
                                    x / Sprite.SCALED_SIZE,
                                        new Grass(x / Sprite.SCALED_SIZE,
                                                y / Sprite.SCALED_SIZE,
                                                Sprite.grass.getFxImage()));
                BombermanGame.removeItem(this);
            });
        }
    }
}
