package entities;

import entities.bombs.Bomb;
import entities.tiles.Brick;
import entities.tiles.Wall;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import graphics.Sprite;
import main.BombermanGame;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected boolean goUp, goDown, goLeft, goRight;

    protected Image img;
    protected char direction;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public static boolean checkWall(int x, int y) {
        if (x < Sprite.SCALED_SIZE || y < Sprite.SCALED_SIZE || x > Sprite.SCALED_SIZE * (BombermanGame.WIDTH - 1) || y > Sprite.SCALED_SIZE * (BombermanGame.HEIGHT - 1)) {
            return false;
        }

        x /= Sprite.SCALED_SIZE;
        y /= Sprite.SCALED_SIZE;
//        Entity cur = getEntity(x, y);
//        return !(cur instanceof Wall) && !(cur instanceof Brick) && !(cur instanceof Bomb);
        return true;
    }

    public static boolean checkBrick(int x, int y) {
        if (x < 0 || y < 0 || x > Sprite.SCALED_SIZE * BombermanGame.WIDTH || y > Sprite.SCALED_SIZE * BombermanGame.HEIGHT) {
            return false;
        }

        x /= Sprite.SCALED_SIZE;
        y /= Sprite.SCALED_SIZE;
//        Entity cur = getEntity(x, y);
//        return !(cur instanceof Wall) && !(cur instanceof Bomb);
        return true;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update(Scene scene);
}
