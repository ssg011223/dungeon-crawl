package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.io.*;
import java.sql.Date;

public class SaveFileManager {
    public void export(GameMap currentMap, GameMap[] maps) throws IOException {
        FileOutputStream fileOut = new FileOutputStream("testfile.json");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(writeGameStateObject(currentMap, maps));
        objectOut.close();
        fileOut.close();
    }

    public GameState importState() throws IOException, ClassNotFoundException {
        FileInputStream fIn = new FileInputStream("testfile.json");
        ObjectInputStream oIn = new ObjectInputStream(fIn);
        GameState state = (GameState) oIn.readObject();
        oIn.close();
        fIn.close();
        System.out.println(state);
        return state;
    }

    private GameState writeGameStateObject(GameMap currentMap, GameMap[] maps) {
        Date date = new java.sql.Date(System.currentTimeMillis());
        PlayerModel player = new PlayerModel(currentMap.getPlayer());
        GameState state = new GameState(currentMap, date, player);
        state.addDiscoveredMap(maps[0]);
        state.addDiscoveredMap(maps[1]);
        return state;
    }
}
