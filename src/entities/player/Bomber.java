package entities.player;

import entities.bombs.Bomb;
import entities.characters.Enemy;
import entities.items.*;
import entities.tiles.Brick;
import entities.tiles.Wall;
import graphics.Sprite;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import entities.Entity;
import main.BombermanGame;
import playerInputs.KeyHandler;
import sound.Sound;

public class Bomber extends Entity {
    private KeyHandler keyHandler;
    private int unDeadTime = 0;
    private boolean flamePass = false;
    private boolean wallPass = false;
    private int bombCounter = 1;
    private int bomb_size = 1;
    public Entity bomb;
    private int _life;

    public Bomber() {
    }

    public Bomber(int x, int y, Image img, int life, KeyHandler keyHandler) {
        super(x, y, img);
        this.life = life;
        this.keyHandler = keyHandler;
        died = false;
        flamePass = false;
        wallPass = false;
        STEP = 2;
        _life = life;
    }

    private void chooseSprite() {
        animate++;
        if (animate > 100000) animate = 0;
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
    public void update() {
        _life = this.life;
        if (beDamaged) {
            if (hurtTick == 0) {
                Sound.dieds.playSound();
            }
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
        move = false;
        checkDied(died);
        unDeadTime = Math.max(0, unDeadTime - 1);
        moving();
        getItem();
        chooseSprite();
        if (keyHandler.isPressed(KeyCode.SPACE)) {
            setBomb();
        }
//        System.out.println(this.life);
    }

    private void moving() {
        if (keyHandler.isPressed(KeyCode.A) || keyHandler.isPressed(KeyCode.LEFT)) {
            if (keyHandler.isPressed(KeyCode.W) || keyHandler.isPressed(KeyCode.UP) ||
                    keyHandler.isPressed(KeyCode.S) || keyHandler.isPressed(KeyCode.DOWN) ||
                    keyHandler.isPressed(KeyCode.D) || keyHandler.isPressed(KeyCode.RIGHT)) {
                return;
            }
            if (checkWall(x - STEP, y + 4) && checkWall(x - STEP, y + Sprite.SCALED_SIZE - 4)) {
                x -= STEP;
                move = true;
                direction = 'L';
            }
            //
            if (!(checkWall(x - STEP, y + 4))) {
                if ((double)((y + 4) * 1.0 / Sprite.SCALED_SIZE) > ((int)((y + 4) / Sprite.SCALED_SIZE) + 0.5)) {
                    direction = 'D';
                    move = true;
                    y++;
                }
            } else if (!(checkWall(x - STEP, y + Sprite.SCALED_SIZE - 4))) {
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
            if (checkWall(x + Sprite.SCALED_SIZE + STEP - 5, y + 4) && checkWall(x + Sprite.SCALED_SIZE + STEP - 5, y + Sprite.SCALED_SIZE - 4)) {
                x += STEP;
                move = true;
                direction = 'R';
            }
            //
            if (!(checkWall(x + Sprite.SCALED_SIZE + STEP - 5, y + 4))) {
                if ((double) ((y + 4) * 1.0 / Sprite.SCALED_SIZE) > (double) (((int) (y + 4) / Sprite.SCALED_SIZE) + 0.5)) {
                    direction = 'D';
                    move = true;
                    y++;
                }
            } else if (!(checkWall(x + Sprite.SCALED_SIZE + STEP - 5, y + Sprite.SCALED_SIZE - 4))) {
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
            if (checkWall(x + 4, y - STEP) && checkWall(x + Sprite.SCALED_SIZE - 5, y - STEP)) {
                y -= STEP;
                move = true;
                direction = 'U';
            }
            //
            if (!(checkWall(x + 4, y - STEP))) {
                if ((double) ((x + 4) * 1.0 / Sprite.SCALED_SIZE) > (double) (((int) (x + 4) / Sprite.SCALED_SIZE) + 0.5)) {
                    direction = 'R';
                    move = true;
                    x++;
                }
            } else if (!(checkWall(x + Sprite.SCALED_SIZE - 5, y - STEP))) {
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
            if (checkWall(x + 4, y + Sprite.SCALED_SIZE + STEP) && checkWall(x + Sprite.SCALED_SIZE - 5, y + Sprite.SCALED_SIZE + STEP)) {
                y += STEP;
                move = true;
                direction = 'D';
            }
            //
            if (!(checkWall(x + 4, y + Sprite.SCALED_SIZE + STEP))) {
                if ((double) ((x + 4) * 1.0 / Sprite.SCALED_SIZE) > (double) (((int) (x + 4) / Sprite.SCALED_SIZE) + 0.5)) {
                    direction = 'R';
                    move = true;
                    x++;
                }
            } else if (!(checkWall(x + Sprite.SCALED_SIZE - 5, y + Sprite.SCALED_SIZE + STEP))) {
                if ((double) ((x + Sprite.SCALED_SIZE - 5) * 1.0 / Sprite.SCALED_SIZE) < (double) ((int) (x + Sprite.SCALED_SIZE - 5) / Sprite.SCALED_SIZE) + 0.5) {
                    direction = 'L';
                    move = true;
                    x--;
                }
            }
        }
        if (move && animate % 15 == 0) {
            Sound.move.playSound();
        }

        if (beDamaged()) {
            return;
        }
        //if (!(BombermanGame.getTable()[py][px] instanceof Enemy)) BombermanGame.setTable(py, px, this);
    }

    private boolean beDamaged() {
        if (BombermanGame.getMoveEntitiesTable()[getYUnit()][getXUnit()] instanceof Enemy && !isUnDead()) {
            damaged();
            return true;
        }
        return false;
    }

    private void checkDied(boolean died) {
        if (died) {
            BombermanGame.gameFunction = BombermanGame.FUNCTION.REPLAY;
            BombermanGame.replay = true;
        }
    }

//    @Override
//    public int getXUnit() {
//        return (x + (75 * Sprite.SCALED_SIZE) / (2 * 100)) / Sprite.SCALED_SIZE;
//    }
//
//    @Override
//    public int getYUnit() {
//        return (y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE;
//    }

    public boolean isUnDead() {
        return unDeadTime > 0;
    }


    public boolean canSetBomb() {
        if (BombermanGame.getTable()[getYUnit()][getXUnit()] instanceof Wall ||
                BombermanGame.getTable()[getYUnit()][getXUnit()] instanceof Brick) {
            return false;
        }
        return Bomb.cnt < bombCounter;
    }

    public boolean isWallPass() {
        return wallPass;
    }

    public boolean isFlamePass() {
        return flamePass;
    }

    public void setBomb() {
        if (canSetBomb()) {
            Platform.runLater(() -> {
                bomb = new Bomb(getXUnit(), getYUnit(), Sprite.bomb.getFxImage(), bomb_size);
                BombermanGame.addBomb(bomb);
            });
            Sound.place_bomb.playSound();
        }
    }

    public int getLife() {
        return life;
    }

    public void getItem() {
        int px = getYUnit();
        int py = getXUnit();
        Entity e = BombermanGame.getTable()[px][py];
        //System.out.println(px + " " + py + " " + e.getClass().getName());
        if (e instanceof FlameItem) {
            if (!((FlameItem) e).isPassed()) {
                bomb_size++;
            }
            ((FlameItem) e).setIsPassed();
        } else if (e instanceof SpeedItem) {
            if (!((SpeedItem) e).isPassed()) {
                STEP++;
            }
            ((SpeedItem) e).setIsPassed();
        } else if (e instanceof BombItem) {
            if (!((BombItem) e).isPassed()) {
                bombCounter++;
//                System.out.println("get Bomb " + bombCounter);
            }
            ((BombItem) e).setIsPassed();
        } else if (e instanceof FlamePass) {
            if (!((FlamePass) e).isPassed()) {
                flamePass = true;
            }
            ((FlamePass) e).setIsPassed();
        } else if (e instanceof WallPass) {
            if (!((WallPass) e).isPassed()) {
                wallPass = true;
            }
            ((WallPass) e).setIsPassed();
        } else if (e instanceof Portal) {
            if (BombermanGame.getEnemies().isEmpty() && BombermanGame.getLevel() <= 1) {
                int _level = BombermanGame.getLevel() + 1;
                if (_level == 2) {
                    BombermanGame.gameFunction = BombermanGame.FUNCTION.END;
                    BombermanGame.endGame = true;
                    return;
                }
                BombermanGame.setLevel(_level);
//                Sound.ending.playSound();
                BombermanGame.levelChanged = true;
                BombermanGame.setNull();
            }
        }
    }
}
