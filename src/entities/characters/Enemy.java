package entities.characters;

import graphics.Sprite;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import entities.Entity;
import algorithm.enemyFindPath;
import javafx.util.Pair;
import main.BombermanGame;

import java.util.Random;


public class Enemy extends Entity {
    protected char direction = ' ';
    public Enemy(int x, int y, Image img, int life) {
        super( x, y, img);
        this.life = life;
        died = false;
    }

    protected void chooseDirectionRandom() {
        if (animate > 100000) animate = 0;
        if (animate % 30 == 0) {
            direction = directions[new Random().nextInt(4)];
        }
    }

    @Override
    public void update(Scene scene) {

    }

    protected void chooseSprite() {
    }

    public void chooseDirection() {
        //if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
            Pair<Integer, Integer> nextStep = enemyFindPath.getNextStepByBFS(getYUnit(), getXUnit());
            int dy = nextStep.getKey();
            int dx = nextStep.getValue();
            if (dx < this.getXUnit()) direction = 'L';
            if (dx > this.getXUnit()) direction = 'R';
            if (dy < this.getYUnit()) direction = 'U';
            if (dy > this.getYUnit()) direction = 'D';
        //}
    }

}
