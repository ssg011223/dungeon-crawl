package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label tileLabel = new Label();
    Random RANDOM = new Random();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.add(new Label("Tile: "),0,1);
        ui.add(tileLabel, 1,1);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private int randomNumber(int upperBound) {
        return RANDOM.nextInt(upperBound);
    };

    private void moveEnemies() {
        for (Actor enemy : map.getActors()) {
            enemy.move(randomNumber(3) - 1, randomNumber(3) - 1);
        }
    }

    private Actor checkEnemy(Cell targetCell) {
        for (Actor enemy : map.getActors()) {
            if (enemy.getCell() == targetCell) {
                return enemy;
            }
        }
        return null;
    }

    private void attackOrMove(int dx, int dy) {
        Cell targetCell = map.getPlayer().getCell().getNeighbor(dx, dy);
        Actor enemyAtTarget = checkEnemy(targetCell);
        if (enemyAtTarget != null) {
            attack(enemyAtTarget);
        } else { map.getPlayer().move(dx, dy); }
    }

    private void attack(Actor enemyAtTarget) {
        enemyAtTarget.damage(5);
        enemyAtTarget.update();
        if (enemyAtTarget.isAlive()) map.getPlayer().damage(2);
        map.getPlayer().update();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
            switch (keyEvent.getCode()) {
                case UP:
                    attackOrMove(0, -1);
                    moveEnemies();
                    refresh();
                    break;
                case DOWN:
                    attackOrMove(0, 1);
                    moveEnemies();
                    refresh();
                    break;
                case LEFT:
                    attackOrMove(-1, 0);
                    moveEnemies();
                    refresh();
                    break;
                case RIGHT:
                    attackOrMove(1, 0);
                    moveEnemies();
                    refresh();
                    break;
            }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
    }
}
