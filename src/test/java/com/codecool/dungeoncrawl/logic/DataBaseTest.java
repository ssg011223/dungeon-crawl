package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.actors.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataBaseTest {
    GameDatabaseManager gameDbManager = new GameDatabaseManager();
    GameMap gameMap = new GameMap(3,3,CellType.FLOOR);
    GameMap gameMap2 = new GameMap(2,2,CellType.WATER);
    GameMap[] allMaps = {gameMap, gameMap2};

    @BeforeEach
    void createDbConnection() throws SQLException {
        gameDbManager.setup();
    }

    @Test
    void overWriteSave_SameNameFileAdded_OverWritesSave() {
        Player player = new Player(gameMap.getCell(1,1));
        gameDbManager.saveGameState(gameMap, allMaps, player, "TestSave");

        assertTrue(gameDbManager.checkName("TestSave"));

        gameDbManager.overWriteSave(gameMap, allMaps, player, "TestSave");

        assertTrue(gameDbManager.checkName("TestSave"));
    }

}
