package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.List;

public class PlayerModel extends BaseModel {
    private String playerName;
    private int hp;
    private int x;
    private int y;
    private List<Item> inventory;

    public PlayerModel(String playerName, int x, int y) {
        this.playerName = playerName;
        this.x = x;
        this.y = y;
    }

    public PlayerModel(Player player) {
        this.playerName = player.getName();
        this.x = player.getX();
        this.y = player.getY();

        this.hp = player.getHealth();

        this.inventory = player.getInventory();
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getInventory() {
        StringBuilder sb = new StringBuilder("");
        int commaTracker = 0;
        for (Item item : inventory) {
            if (commaTracker == 0) {
                sb.append(item.getClass().getSimpleName());
                commaTracker++;
            } else { sb.append(", ").append(item.getClass().getSimpleName()); }
        }
        return sb.toString();
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
