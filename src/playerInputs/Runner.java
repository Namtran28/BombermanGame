package playerInputs;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Hold down an arrow key to have your hero move around the screen.
 * Hold down the shift key to have the hero run.
 */
public class Runner extends Application {

    private static final double W = 800, H = 600;

    private static final String HERO_IMAGE_LOC =
            "http://icons.iconarchive.com/icons/raindropmemory/legendora/64/Hero-icon.png";

    private Image heroImage;
    private Node  hero;

    boolean running, goNorth, goSouth, goEast, goWest;

    @Override
    public void start(Stage stage) throws Exception {
        heroImage = new Image(HERO_IMAGE_LOC);
        hero = new ImageView(heroImage);

        Group dungeon = new Group(hero);

        moveHeroTo(W / 2, H / 2);

        Scene scene = new Scene(dungeon, W, H, Color.BLUE);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP, W:    goNorth = true; break;
                case DOWN, S:  goSouth = true; break;
                case LEFT, A:  goWest  = true; break;
                case RIGHT, D: goEast  = true; break;
                case SHIFT: running = true; break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP, W:    goNorth = false; break;
                case DOWN, S:  goSouth = false; break;
                case LEFT, A:  goWest  = false; break;
                case RIGHT, D: goEast  = false; break;
                case SHIFT: running = false; break;
            }
        });

        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx = 0, dy = 0;

                if (goNorth) dy -= 1;
                if (goSouth) dy += 1;
                if (goEast)  dx += 1;
                if (goWest)  dx -= 1;
                if (running) { dx *= 3; dy *= 3; }

                moveHeroBy(dx, dy);
            }
        };
        timer.start();
    }

    private void moveHeroBy(int dx, int dy) {
        if (dx == 0 && dy == 0) return;

        final double cx = hero.getBoundsInLocal().getWidth() / 2;
        final double cy = hero.getBoundsInLocal().getHeight() / 2;

        double x = cx + hero.getLayoutX() + dx;
        double y = cy + hero.getLayoutY() + dy;

        System.out.println("moveHeroBy: " + cx + " " + cy + " " + hero.getLayoutX() + " " + hero.getLayoutY() + "\n");

        moveHeroTo(x, y);
    }

    private void moveHeroTo(double x, double y) {
        final double cx = hero.getBoundsInLocal().getWidth() / 2;
        final double cy = hero.getBoundsInLocal().getHeight() / 2;
        System.out.println("moveHeroTo: " + cx + " " + cy + "\n");

        if (x - cx >= 0 &&
                x + cx <= W &&
                y - cy >= 0 &&
                y + cy <= H) {
            hero.relocate(x - cx, y - cy);
        }
    }

    public static void main(String[] args) { launch(args); }
}