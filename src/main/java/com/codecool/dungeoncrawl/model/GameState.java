package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class GameState extends BaseModel {
    private Date savedAt;
    private GameMap currentMap;
    private List<GameMap> discoveredMaps = new ArrayList<>();
    private PlayerModel player;
    private String saveName;

    public GameState(GameMap currentMap, Date savedAt, PlayerModel player, String saveName) {
        this.currentMap = currentMap;
        this.savedAt = savedAt;
        this.player = player;
        this.saveName = saveName;
    }

    public Date getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Date savedAt) {
        this.savedAt = savedAt;
    }

    public GameMap getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(GameMap currentMap) {
        this.currentMap = currentMap;
    }

    public List<GameMap> getDiscoveredMaps() {
        return discoveredMaps;
    }

    public void addDiscoveredMap(GameMap map) {
        this.discoveredMaps.add(map);
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }
}
