package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class Player extends Actor {
    private ArrayList<Item> inventory = new ArrayList<>(8);

    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "player";
    }

    public ArrayList<Item> getInventory() { return inventory; }

    public void addToInventory(Item item) { this.inventory.add(item); }
}
