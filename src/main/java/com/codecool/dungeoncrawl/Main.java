package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Random;

public class Main extends Application {
    GameMap[] maps = new GameMap[] {MapLoader.loadMap("map.txt"), MapLoader.loadMap("map2.txt")};
    GameMap map = maps[0];
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label inventoryLabel = new Label();
    Label attackLabel = new Label();
    Random RANDOM = new Random();
    Stage gameStage;
    String GameOver = "GAME OVER";
    String buttonStyle = "-fx-background-color: \n" +
            "        #090a0c,\n" +
            "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
            "        linear-gradient(#20262b, #191d22),\n" +
            "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
            "    -fx-background-radius: 5,4,3,5;\n" +
            "    -fx-background-insets: 0,1,2,0;\n" +
            "    -fx-text-fill: white;\n" +
            "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
            "    -fx-font-family: \"Arial\";\n" +
            "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
            "    -fx-font-size: 18px;\n" +
            "    -fx-padding: 10 20 10 20;";
    String modalStyle = "" +
            "    -fx-background-color:\n" +
            "        linear-gradient(#6a040f, #370617),\n" +
            "        radial-gradient(center 50% -40%, radius 200%, #d00000 45%, #9d0208 50%);\n";
    int xCoord = 0;
    int yCoord = 1;

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
        ui.add(new Label("Attack: "), 0, 1);
        ui.add(attackLabel, 1, 1);
        ui.add(new Label("Inventory: "), 0, 2);
        ui.add(inventoryLabel, 1, 2);
        ui.add(saveButton(primaryStage), 0, 5);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressEvent -> onKeyPressed(keyPressEvent));

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        gameStage = primaryStage;
    }

    private Button saveButton(Stage primaryStage) {
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> modalSaveWindow(primaryStage));
        return saveButton;
    }

    private void modalExitWindow(Stage primaryStage, String modalTitle, String modalText) {
        final Stage dialog = new Stage();
        dialog.setTitle(modalTitle);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        Button exitButton = new Button("EXIT");
        exitButton.setOnAction(event -> System.exit(0));
        exitButton.setStyle(buttonStyle);
        Button replayButton = new Button("PLAY AGAIN");
        replayButton.setOnAction(event -> {
            this.restart();
            dialog.close();
        });
        dialog.setOnCloseRequest(event -> System.exit(0));
        replayButton.setStyle(buttonStyle);
        VBox dialogVbox = new VBox();
        dialogVbox.getChildren().add(new Text(modalText));
        dialogVbox.getChildren().add(exitButton);
        dialogVbox.getChildren().add(replayButton);
        dialogVbox.setAlignment(Pos.TOP_CENTER);
        dialogVbox.setStyle(modalStyle);
        Scene dialogScene = new Scene(dialogVbox, 150, 100);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void modalSaveWindow(Stage primaryStage) {
        final Stage dialog = new Stage();
        dialog.setTitle("Save Game");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setOnCloseRequest(event -> dialog.close());

        Button saveButton = new Button("SAVE");
        saveButton.setOnAction(event -> saveGame());
        Button cancelSaveButton = new Button("CANCEL");
        cancelSaveButton.setOnAction(event -> dialog.close());

        VBox dialogVbox = new VBox();
        dialogVbox.getChildren().addAll(saveButton, cancelSaveButton);
        dialogVbox.setAlignment(Pos.TOP_CENTER);

        Scene dialogScene = new Scene(dialogVbox, 150, 75);

        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void saveGame() {
        GameDatabaseManager gameDbManager = new GameDatabaseManager();
        try {
            gameDbManager.setup();
        } catch (SQLException SQLex) {
            System.out.println("SQL ERROR");
        }
        gameDbManager.savePlayer(map.getPlayer());

    }

    private void restart() {
        this.maps = new GameMap[] {MapLoader.loadMap("map.txt"), MapLoader.loadMap("map2.txt")};
        this.map = maps[0];
        this.refresh();
    }

    private int randomNumber(int upperBound) {
        return RANDOM.nextInt(upperBound);
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
        if (enemyAtTarget != null && enemyAtTarget.isAlive()) {
            attack(enemyAtTarget);
        } else {
            Player player = map.getPlayer();
            Cell neighbouringCell = player.getCell().getNeighbor(dx,dy);
            player.move(dx, dy);
            if (neighbouringCell.getObstacle() != null) neighbouringCell.getObstacle().remove(player);
            if (player.getCell().getItem() != null) player.getCell().getItem().pickUp(player);
            if (player.getCell().getType().equals(CellType.STAIRS)) {
                if (this.map == maps[0]) {
                    this.map = maps[1];
                    player.teleport(this.map, 1, 2); //Coordinates should be next to the stairs
                } else {
                    this.map = maps[0];
                    player.teleport(this.map, 23, 17); //Coordinates should be next to the stairs
                }
            }
        }
    }

    private void attack(Actor enemyAtTarget) {
        enemyAtTarget.damage(map.getPlayer().getAttack());                               // this is how much Player damages target
        enemyAtTarget.update();
        if (enemyAtTarget.isAlive()) map.getPlayer().damage(2); // this is how much the target damages back if still alive
        map.getPlayer().update();
    }

    private boolean isPlayerNear(Actor enemy) {
        for (int rows = -1; rows < 2; rows++) {
            for (int columns = -1; columns < 2; columns++) {
                if (enemy.getCell().getNeighbor(rows, columns) == null) continue;
                if (enemy.getCell().getNeighbor(rows, columns).getActor() != null) {
                    if (enemy.getCell().getNeighbor(rows, columns).getActor().equals(map.getPlayer())) {
                        return true;
                        }
                    }
                }
            }
        return false;
    }

    private void enemyAttackOrMove() {
        if (map.getActors() == null) return;
        for (Actor enemy : map.getActors()) {
            if (isPlayerNear(enemy) && enemy.isAlive()) {
                map.getPlayer().damage(enemy.getAttack());                      // this is how much the enemy damage the player when they attack
            } else if (enemy.isAlive())
                enemy.move(randomNumber(3) - 1, randomNumber(3) - 1);
        }
        map.getPlayer().update();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
            switch (keyEvent.getCode()) {
                case UP:
                    attackOrMove(0, -1);
                    enemyAttackOrMove();
                    refresh();
                    break;
                case DOWN:
                    attackOrMove(0, 1);
                    enemyAttackOrMove();
                    refresh();
                    break;
                case LEFT:
                    attackOrMove(-1, 0);
                    enemyAttackOrMove();
                    refresh();
                    break;
                case RIGHT:
                    attackOrMove(1, 0);
                    enemyAttackOrMove();
                    refresh();
                    break;
            }
    }

    private int[] mapMover() {
        int offsetX = 0;
        int offsetY = 0;
        int displayWidth = 25;
        int dislpayHeight = 20;
        int mapX = map.getWidth();
        int mapY = map.getHeight();
        int playerX = map.getPlayer().getX();
        int playerY = map.getPlayer().getY();
        if (playerX + (displayWidth / 2) >= mapX) {
            offsetX = mapX - displayWidth;
        }
        else if (playerX > (displayWidth / 2)) {
            offsetX = playerX - (displayWidth / 2);
        }
        if (playerY + (dislpayHeight / 2) >= mapY) {
            offsetY = mapY - dislpayHeight;
        }
        else if (playerY > (dislpayHeight / 2)) {
            offsetY = playerY - (dislpayHeight / 2);
        }
        int[] coords = {offsetX, offsetY};
        return coords;
    }

    private void refreshMap() {
        int[] mapOffset = mapMover();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x - mapOffset[xCoord], y - mapOffset[yCoord]);
                } else if (cell.getObstacle() != null) {
                    Tiles.drawTile(context, cell.getObstacle(), x - mapOffset[xCoord], y - mapOffset[yCoord]);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x - mapOffset[xCoord], y - mapOffset[yCoord]);
                } else {
                    Tiles.drawTile(context, cell, x - mapOffset[xCoord], y - mapOffset[yCoord]);
                }
            }
        }
    }

    private void refreshInventory() {
        healthLabel.setText("" + map.getPlayer().getHealth());
        attackLabel.setText("" + map.getPlayer().getAttack());
        StringBuilder inventoryPrint = new StringBuilder();
        for (int i = 0; i < map.getPlayer().getInventory().size(); i++) {
            if (i > 0) {
                inventoryPrint.append(", ").append(map.getPlayer().getInventory().get(i).getClass().getSimpleName());
            } else inventoryPrint.append(map.getPlayer().getInventory().get(i).getClass().getSimpleName());
        }
        inventoryLabel.setText(inventoryPrint.toString());
    }

    private void refresh() {
        refreshMap();
        healthLabel.setText("" + map.getPlayer().getHealth());
        refreshInventory();
        if (!map.getPlayer().isAlive()) {
            modalExitWindow(gameStage, GameOver, "");
        }
        if (!maps[1].getBoss().isAlive()) {
            modalExitWindow(gameStage, "YOU WON", "");
        }
    }
}
