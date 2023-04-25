package algorithm;
import entities.Entity;
import entities.tiles.Brick;
import entities.tiles.Wall;
import graphics.Sprite;
import javafx.util.Pair;
import main.BombermanGame;

import java.util.*;

public class enemyFindPath {
    private static int[] dx = {-1, 1, 0, 0};
    private static int[] dy = {0, 0, -1, 1};
    private static Entity[][] _table;
    private static Pair<Integer, Integer>[][] pathTo = new Pair[BombermanGame.HEIGHT][BombermanGame.WIDTH];
    private static boolean[][] visited = new boolean[BombermanGame.HEIGHT][BombermanGame.WIDTH];

    public enemyFindPath() {

    }

    private static void dfs(int x, int y) {
        visited[x][y] = true;
        for (int i = 0; i < 4; i++) {
            int x1 = x + dy[i];
            int y1 = y + dx[i];
            if (x1 >= 0 && y1 >= 0 && x1 < BombermanGame.HEIGHT && y1 < BombermanGame.WIDTH &&
                    !(_table[x1][y1] instanceof Brick || _table[x1][y1] instanceof Wall) &&
                    !visited[x1][y1]) {
                dfs(x1, y1);
                pathTo[x1][y1] = new Pair<>(x, y);
            }
        }
    }

    private static void bfs(int x, int y) {
        if ((_table[x][y] instanceof Brick || _table[x][y] instanceof Wall) &&
                visited[x][y]) return;
        Queue<Pair<Integer, Integer>>  queue = new LinkedList<>();
        queue.add(new Pair<>(x, y));
        visited[x][y] = true;
        while (!queue.isEmpty()) {
            Pair<Integer, Integer> u = queue.poll();
            for (int i = 0; i < 4; i++) {
                int x1 = u.getKey() + dx[i];
                int y1 = u.getValue() + dy[i];
                if (x1 >= 0 && y1 >= 0 && x1 < BombermanGame.HEIGHT && y1 < BombermanGame.WIDTH &&
                        !(_table[x1][y1] instanceof Brick || _table[x1][y1] instanceof Wall) &&
                        !visited[x1][y1]) {
                    queue.add(new Pair<>(x1, y1));
                    pathTo[x1][y1] = new Pair<>(u.getKey(), u.getValue());
                    visited[x1][y1] = true;
                }
            }
        }
    }

    public static Pair<Integer, Integer> getNextStepByDFS(int x, int y) {
        Pair<Integer, Integer> nextStep = new Pair<>(x, y);
        try {
            _table = BombermanGame.getTable();

            dfs(x, y);
            int py = BombermanGame.player.getXUnit();
            int px = BombermanGame.player.getYUnit();
            while (px != x && py != y) {
                nextStep = new Pair<>(px, py);
                int tmp = px;
                px = pathTo[tmp][py].getKey();
                py = pathTo[tmp][py].getValue();
            }
        } catch (Exception e) {
//            int id = new Random().nextInt(4);
//            System.out.println("Exception " + id + " " + x + " " + y + " " + BombermanGame.getTable()[x][y].getClass().getName() + " " + visited[x][y]);
//            if (!(BombermanGame.getTable()[x + dx[id]][y + dy[id]] instanceof Brick || BombermanGame.getTable()[x + dx[id]][y + dy[id]] instanceof Wall)) {
//                nextStep = new Pair<>(x + dx[id], y + dy[id]);
//            }
        }
        return nextStep;
    }

    public static Pair<Integer, Integer> getNextStepByBFS(int x, int y) {
        Pair<Integer, Integer> nextStep = new Pair<>(x, y);
        try {
            _table = BombermanGame.getTable();
            for (int i = 0; i < BombermanGame.HEIGHT; i++) {
                Arrays.fill(pathTo[i], new Pair<>(x, y));
                Arrays.fill(visited[i], false);
            }
            bfs(x, y);
            int py = BombermanGame.player.getXUnit();
            int px = BombermanGame.player.getYUnit();
            while (px != x && py != y) {
                nextStep = new Pair<>(px, py);
                int tmp = px;
                px = pathTo[tmp][py].getKey();
                py = pathTo[tmp][py].getValue();
            }
        } catch (Exception e) {
            int id = new Random().nextInt(4);
            //System.out.println("Exception " + id + " " + x + " " + y + " " + BombermanGame.getTable()[x][y].getClass().getName() + " " + visited[x][y]);
            if (!(BombermanGame.getTable()[x + dx[id]][y + dy[id]] instanceof Brick || BombermanGame.getTable()[x + dx[id]][y + dy[id]] instanceof Wall)) {
                nextStep = new Pair<>(x + dx[id], y + dy[id]);
            }
        }
        return nextStep;
    }
}
