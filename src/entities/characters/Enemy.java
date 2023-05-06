package entities.characters;

import entities.bombs.Bomb;
import entities.bombs.Flame;
import graphics.Sprite;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import entities.Entity;
import algorithm.enemyFindPath;
import javafx.util.Pair;
import main.BombermanGame;
import sound.Sound;

import java.util.Random;


public abstract class Enemy extends Entity {
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

    protected abstract void chooseSprite();

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

    public void moving() {
        BombermanGame.setMoveEntitiesTable(getYUnit(), getXUnit(), null);
        if (!(this instanceof Minvo)) chooseDirectionRandom();
        if (this instanceof Oneal || this instanceof Minvo) {
            chooseDirection();
            //chooseDirectionRandom();
        }
//        BombermanGame.setTable(getYUnit(), getXUnit(), null);
        if (direction == 'D') {
            if (checkWall(x + 3, y + 1 + Sprite.SCALED_SIZE) && checkWall(x - 3 + Sprite.SCALED_SIZE, y + 1 + Sprite.SCALED_SIZE)) {
                y++;
                move = true;
            }
//            if (!(checkWall(x + 3, y + Sprite.SCALED_SIZE + 1))) {
//                if ((double) ((x + 3) * 1.0 / Sprite.SCALED_SIZE) > (double) (((int) (x + 3) / Sprite.SCALED_SIZE) + 0.1)) {
//                    direction = 'R';
//                    move = true;
//                    x++;
//                }
//            } else if (!(checkWall(x + Sprite.SCALED_SIZE - 3, y + Sprite.SCALED_SIZE + 1))) {
//                if ((double) ((x + Sprite.SCALED_SIZE - 3) * 1.0 / Sprite.SCALED_SIZE) < (double) ((int) (x + Sprite.SCALED_SIZE - 3) / Sprite.SCALED_SIZE) + 0.1) {
//                    direction = 'L';
//                    move = true;
//                    x--;
//                }
//            }
        }
        if (direction == 'U') {
            if (checkWall(x + 3, y - 1) && checkWall(x - 3 + Sprite.SCALED_SIZE, y - 1)) {
                y--;
                move = true;
            }
//            if (!(checkWall(x + 3, y - 1))) {
//                if ((double) ((x + 3) * 1.0 / Sprite.SCALED_SIZE) > (double) (((int) (x + 3) / Sprite.SCALED_SIZE) + 0.1)) {
//                    direction = 'R';
//                    move = true;
//                    x++;
//                }
//            } else if (!(checkWall(x + Sprite.SCALED_SIZE - 3, y - 1))) {
//                if ((double) ((x + Sprite.SCALED_SIZE - 3) * 1.0 / Sprite.SCALED_SIZE) < (double) ((int) (x + Sprite.SCALED_SIZE - 3) / Sprite.SCALED_SIZE) + 0.1) {
//                    direction = 'L';
//                    move = true;
//                    x--;
//                }
//            }
        }
        if (direction == 'L') {
            if (checkWall(x - 1, y + 3) && checkWall(x - 1, y - 3 + Sprite.SCALED_SIZE)) {
                x--;
                move = true;
            }
//            if (!(checkWall(x - 1, y + 3))) {
//                if ((double) ((y + 3) * 1.0 / Sprite.SCALED_SIZE) > (double) (((int) (y + 3) / Sprite.SCALED_SIZE) + 0.1)) {
//                    direction = 'D';
//                    move = true;
//                    y++;
//                }
//            } else if (!(checkWall(x - 1, y + Sprite.SCALED_SIZE - 3))) {
//                if ((double) ((y + Sprite.SCALED_SIZE - 3) * 1.0 / Sprite.SCALED_SIZE) < (double) ((int) (y + Sprite.SCALED_SIZE - 3) / Sprite.SCALED_SIZE) + 0.1) {
//                    direction = 'U';
//                    move = true;
//                    y--;
//                }
//            }
        }
        if (direction == 'R') {
            if (checkWall(x + 1 + Sprite.SCALED_SIZE, y + 3) && checkWall(x + 1 + Sprite.SCALED_SIZE, y - 3 + Sprite.SCALED_SIZE)) {
                x++;
                move = true;
            }
//            if (!(checkWall(x + Sprite.SCALED_SIZE + 1, y + 3))) {
//                if ((double) ((y + 3) * 1.0 / Sprite.SCALED_SIZE) > (double) (((int) (y + 3) / Sprite.SCALED_SIZE) + 0.1)) {
//                    direction = 'D';
//                    move = true;
//                    y++;
//                }
//            } else if (!(checkWall(x + Sprite.SCALED_SIZE + 1, y + Sprite.SCALED_SIZE - 3))) {
//                if ((double) ((y + Sprite.SCALED_SIZE - 3) * 1.0 / Sprite.SCALED_SIZE) < (double) ((int) (y + Sprite.SCALED_SIZE - 3) / Sprite.SCALED_SIZE) + 0.1) {
//                    direction = 'U';
//                    move = true;
//                    y--;
//                }
//            }
        }
//        BombermanGame.setTable(getYUnit(), getXUnit(), this);
//        if (BombermanGame.getTable()[getYUnit()][getXUnit()] instanceof Bomb) damaged();
        BombermanGame.setMoveEntitiesTable(getYUnit(), getXUnit(), this);
    }

    protected void gotHurt(Sprite sprite) {
        hurtTick++;
        if (hurtTick == 1) {
            Sound.dieds.playSound();
        }
        img = sprite.getFxImage();
        if (hurtTick >= 20) {
            beDamaged = false;
            hurtTick = 0;
            if (died) {
                Platform.runLater(() -> {
                    BombermanGame.setMoveEntitiesTable(getYUnit(), getXUnit(), null);
                    BombermanGame.removeEnemy(this);
                });
            }
        }
    }
}
