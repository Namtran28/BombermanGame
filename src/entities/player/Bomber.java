package entities.player;

import entities.bombs.Bomb;
import graphics.Sprite;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import entities.Entity;
import main.BombermanGame;
import playerInputs.KeyHandler;

public class Bomber extends Entity {
    private KeyHandler keyHandler;
    private int unDeadTime = 0;
    private boolean bomItem = false;
    private boolean flameItem = false;
    private boolean flamePass = false;
    private boolean speedItem = false;
    private boolean wallPass = false;
    private int bombCounter = 1;

    public Bomber() {
    }

    public Bomber(int x, int y, Image img, int life, KeyHandler keyHandler) {
        super(x, y, img);
        this.life = life;
        this.keyHandler = keyHandler;
        died = false;
    }

    private void chooseSprite() {
        animate++;
        if (animate > 10000) animate = 0;
        if (beDamaged) {
            img = Sprite.movingSprite(Sprite.player_dead1,
                    Sprite.player_dead2,
                    Sprite.player_dead3,
                    animate,
                    20).getFxImage();
            return;
        }
        Sprite sprite;
        switch (direction) {
            case 'U' -> {
                sprite = Sprite.player_up;
                if (move) {
                    sprite = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, animate, 20);
                }
            }
            case 'D' -> {
                sprite = Sprite.player_down;
                if (move) {
                    sprite = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, animate, 20);
                }
            }
            case 'L' -> {
                sprite = Sprite.player_left;
                if (move) {
                    sprite = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, animate, 20);
                }
            }
            default -> {
                sprite = Sprite.player_right;
                if (move) {
                    sprite = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, animate, 20);
                }
            }
        }
        img = sprite.getFxImage();
    }

    @Override
    public void update(Scene scene) {
        if (beDamaged) {
            if (hurtTick == 30) {
                beDamaged = false;
                hurtTick = 0;
                unDeadTime = 60 * 3 / 2;
                return;
            }
            unDeadTime = Math.max(0, unDeadTime - 1);
            hurtTick++;
            chooseSprite();
            return;
        }
        checkDied(died);
        unDeadTime = Math.max(0, unDeadTime - 1);
        moving();
        chooseSprite();
        move = false;
    }

    private void moving() {
        if (keyHandler.isPressed(KeyCode.A) || keyHandler.isPressed(KeyCode.LEFT)) {
            if (keyHandler.isPressed(KeyCode.W) || keyHandler.isPressed(KeyCode.UP) ||
                    keyHandler.isPressed(KeyCode.S) || keyHandler.isPressed(KeyCode.DOWN) ||
                    keyHandler.isPressed(KeyCode.D) || keyHandler.isPressed(KeyCode.RIGHT)) {
                return;
            }
            if (checkWall(x - 2, y + 4) && checkWall(x - 2, y + Sprite.SCALED_SIZE - 4)) {
                x -= 2;
                move = true;
                direction = 'L';
            }
            //
            if (!(checkWall(x - 2, y + 4))) {
                if ((double) ((y + 4) * 1.0 / Sprite.SCALED_SIZE) > (double) (((int) (y + 4) / Sprite.SCALED_SIZE) + 0.5)) {
                    direction = 'D';
                    move = true;
                    y++;
                }
            } else if (!(checkWall(x - 2, y + Sprite.SCALED_SIZE - 4))) {
                if ((double) ((y + Sprite.SCALED_SIZE - 4) * 1.0 / Sprite.SCALED_SIZE) < (double) ((int) (y + Sprite.SCALED_SIZE - 4) / Sprite.SCALED_SIZE) + 0.5) {
                    direction = 'U';
                    move = true;
                    y--;
                }
            }

        } else if (keyHandler.isPressed(KeyCode.D) || keyHandler.isPressed(KeyCode.RIGHT)) {
            if (keyHandler.isPressed(KeyCode.W) || keyHandler.isPressed(KeyCode.UP) ||
                    keyHandler.isPressed(KeyCode.S) || keyHandler.isPressed(KeyCode.DOWN) ||
                    keyHandler.isPressed(KeyCode.A) || keyHandler.isPressed(KeyCode.LEFT)) {
                return;
            }
            if (checkWall(x + Sprite.SCALED_SIZE + 2 - 5, y + 4) && checkWall(x + Sprite.SCALED_SIZE + 2 - 5, y + Sprite.SCALED_SIZE - 4)) {
                x += 2;
                move = true;
                direction = 'R';
            }
            //
            if (!(checkWall(x + Sprite.SCALED_SIZE + 2 - 5, y + 4))) {
                if ((double) ((y + 4) * 1.0 / Sprite.SCALED_SIZE) > (double) (((int) (y + 4) / Sprite.SCALED_SIZE) + 0.5)) {
                    direction = 'D';
                    move = true;
                    y++;
                }
            } else if (!(checkWall(x + Sprite.SCALED_SIZE + 2 - 5, y + Sprite.SCALED_SIZE - 4))) {
                if ((double) ((y + Sprite.SCALED_SIZE - 4) * 1.0 / Sprite.SCALED_SIZE) < (double) ((int) (y + Sprite.SCALED_SIZE - 4) / Sprite.SCALED_SIZE) + 0.5) {
                    direction = 'U';
                    move = true;
                    y--;
                }
            }
        } else if (keyHandler.isPressed(KeyCode.W) || keyHandler.isPressed(KeyCode.UP)) {
            if (keyHandler.isPressed(KeyCode.A) || keyHandler.isPressed(KeyCode.LEFT) ||
                    keyHandler.isPressed(KeyCode.S) || keyHandler.isPressed(KeyCode.DOWN) ||
                    keyHandler.isPressed(KeyCode.D) || keyHandler.isPressed(KeyCode.RIGHT)) {
                return;
            }
            if (checkWall(x + 4, y - 2) && checkWall(x + Sprite.SCALED_SIZE - 5, y - 2)) {
                y -= 2;
                move = true;
                direction = 'U';
            }
            //
            if (!(checkWall(x + 4, y - 2))) {
                if ((double) ((x + 4) * 1.0 / Sprite.SCALED_SIZE) > (double) (((int) (x + 4) / Sprite.SCALED_SIZE) + 0.5)) {
                    direction = 'R';
                    move = true;
                    x++;
                }
            } else if (!(checkWall(x + Sprite.SCALED_SIZE - 5, y - 2))) {
                if ((double) ((x + Sprite.SCALED_SIZE - 5) * 1.0 / Sprite.SCALED_SIZE) < (double) ((int) (x + Sprite.SCALED_SIZE - 5) / Sprite.SCALED_SIZE) + 0.5) {
                    direction = 'L';
                    move = true;
                    x--;
                }
            }
        } else if (keyHandler.isPressed(KeyCode.S) || keyHandler.isPressed(KeyCode.DOWN)) {
            if (keyHandler.isPressed(KeyCode.W) || keyHandler.isPressed(KeyCode.UP) ||
                    keyHandler.isPressed(KeyCode.A) || keyHandler.isPressed(KeyCode.LEFT) ||
                    keyHandler.isPressed(KeyCode.D) || keyHandler.isPressed(KeyCode.RIGHT)) {
                return;
            }
            if (checkWall(x + 4, y + Sprite.SCALED_SIZE + 2) && checkWall(x + Sprite.SCALED_SIZE - 5, y + Sprite.SCALED_SIZE + 2)) {
                y += 2;
                move = true;
                direction = 'D';
            }
            //
            if (!(checkWall(x + 4, y + Sprite.SCALED_SIZE + 2))) {
                if ((double) ((x + 4) * 1.0 / Sprite.SCALED_SIZE) > (double) (((int) (x + 4) / Sprite.SCALED_SIZE) + 0.5)) {
                    direction = 'R';
                    move = true;
                    x++;
                }
            } else if (!(checkWall(x + Sprite.SCALED_SIZE - 5, y + Sprite.SCALED_SIZE + 2))) {
                if ((double) ((x + Sprite.SCALED_SIZE - 5) * 1.0 / Sprite.SCALED_SIZE) < (double) ((int) (x + Sprite.SCALED_SIZE - 5) / Sprite.SCALED_SIZE) + 0.5) {
                    direction = 'L';
                    move = true;
                    x--;
                }
            }
        }
        if (keyHandler.isPressed(KeyCode.SPACE)) {
            setBomb();
        }
        int px = getXUnit();
        int py = getYUnit();
        if (beDamaged(px, py)) return;
        //if (!(BombermanGame.getTable()[py][px] instanceof Enemy)) BombermanGame.setTable(py, px, this);
    }

    private boolean beDamaged(int px, int py) {
        for (Entity enemy : BombermanGame.getEnemies()) {
            if (enemy.getXUnit() == px && enemy.getYUnit() == py && !isUnDead()) {
                damaged();
                return true;
            }
        }
        return false;
    }

    private void checkDied(boolean died) {
        if (died) System.exit(0);
    }

    @Override
    public int getXUnit() {
        return (x + (75 * Sprite.SCALED_SIZE) / (2 * 100)) / Sprite.SCALED_SIZE;
    }

    @Override
    public int getYUnit() {
        return (y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE;
    }

    public boolean isUnDead() {
        return unDeadTime > 0;
    }

    public boolean isBomItem() {
        return bomItem;
    }

    public boolean canSetBomb() {
        return bombCounter > 0;
    }

    public boolean isWallPass() {
        return wallPass;
    }

    public boolean isSpeedItem() {
        return speedItem;
    }

    public boolean isFlamePass() {
        return flamePass;
    }

    public boolean isFlameItem() {
        return flameItem;
    }

    public void setBomb() {
        if (canSetBomb()) {
            Entity bomb = new Bomb(getXUnit(), getYUnit(), Sprite.bomb.getFxImage());
            BombermanGame.addBomb(bomb);
            BombermanGame.setTable(getYUnit(), getXUnit(), bomb);
        }
    }
}
