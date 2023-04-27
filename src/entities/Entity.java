package entities;

import entities.characters.Enemy;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import graphics.Sprite;
import main.BombermanGame;
import entities.tiles.*;
import entities.bombs.*;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;
    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Sprite sprite;
    protected boolean goUp, goDown, goLeft, goRight;
    protected Image img;
    protected char direction;
    protected char[] directions = {'L','R','U','D','H','V'};
    protected boolean move = false;
    protected int animate = 0;
    protected int life;
    protected boolean died = false;
    protected boolean beDamaged = false;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public Entity() {
    }

    public static boolean checkWall(int x, int y) {
        if (x < Sprite.SCALED_SIZE || y < Sprite.SCALED_SIZE || x > Sprite.SCALED_SIZE * (BombermanGame.WIDTH - 1) || y > Sprite.SCALED_SIZE * (BombermanGame.HEIGHT - 1)) {
            return false;
        }

        x /= Sprite.SCALED_SIZE;
        y /= Sprite.SCALED_SIZE;
        Entity cur = getEntity(y, x);
        return !(cur instanceof Wall) && !(cur instanceof Brick);
    }

    public static boolean checkBrick(int x, int y) {
        if (x < 0 || y < 0 || x > Sprite.SCALED_SIZE * BombermanGame.WIDTH || y > Sprite.SCALED_SIZE * BombermanGame.HEIGHT) {
            return false;
        }

        x /= Sprite.SCALED_SIZE;
        y /= Sprite.SCALED_SIZE;
        Entity cur = getEntity(y, x);
        return !(cur instanceof Wall) && !(cur instanceof Bomb);
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update(Scene scene);

    public static Entity getEntity(int x, int y) {
        return BombermanGame.getTable()[x][y];
    }

    protected void damaged() {
        life--;
        beDamaged = true;
        if (life == 0) died = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getXUnit() {
        return (x + (75 * Sprite.SCALED_SIZE) / (2 * 100)) / Sprite.SCALED_SIZE;
    }

    public int getYUnit() {
        return (y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE;
    }
}
