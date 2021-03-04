package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private GameStateDaoJdbc gameState;
//    private GameMap currentMap;

    public void overWriteSave(GameMap currentMap, GameMap[] allMaps, Player player, String saveName) {
        PlayerModel model = new PlayerModel(player);
        Date savedAt = new Date(Calendar.getInstance().getTime().getTime());
        GameState currentGameState = new GameState(currentMap, savedAt, model, saveName);
        currentGameState.addDiscoveredMap(allMaps[0]);
        currentGameState.addDiscoveredMap(allMaps[1]);
        playerDao.add(model);
        gameState.overWriteExistingState(currentGameState, saveName);

    }

    public boolean checkName(String name) {
        return gameState.findName(name);
    }

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameState = new GameStateDaoJdbc(dataSource);
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
    }

    public void saveGameState(GameMap currentMap, GameMap[] allMaps, Player player, String saveName) {
        PlayerModel model = new PlayerModel(player);
        Date savedAt = new Date(Calendar.getInstance().getTime().getTime());
        GameState currentGameState = new GameState(currentMap, savedAt, model, saveName);
        currentGameState.addDiscoveredMap(allMaps[0]);
        currentGameState.addDiscoveredMap(allMaps[1]);
        playerDao.add(model);
        gameState.add(currentGameState);
    }

    public GameState getGameState(int i) {
        return gameState.get(i);
    }

    public List<GameState> getAllSaves() {
        return gameState.getAll();
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = "dungeon-crawl";
        String user = "gergo";
        String password = "ger448";

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}
