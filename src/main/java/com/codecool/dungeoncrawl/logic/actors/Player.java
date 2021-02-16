package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.HashMap;

public class Player extends Actor {
    private HashMap<Item, Integer> inventory;

    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "player";
    }

    public HashMap<Item, Integer> getInventory() { return inventory; }

    public void setInventory(HashMap<Item, Integer> inventory) { this.inventory = inventory; }

    public void addToInventory(Item item, Integer amount) { this.inventory.put(item, amount); }
}
