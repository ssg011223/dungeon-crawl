package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;

public class Player extends Actor {
    private ArrayList<Item> inventory = new ArrayList<>(8);
    private String name;

    public Player(Cell cell) {
        super(cell);
        this.setAttack(5);
        this.setHealth(15);
        this.name = "Player";
    }

    public String getTileName() {
        return "player";
    }

    public ArrayList<Item> getInventory() { return inventory; }

    public void addToInventory(Item item) { this.inventory.add(item); }

    public void removeFromInventory(Item item) { this.inventory.remove(item); }

    public void teleport (GameMap map, int x, int y) {
        this.getCell().setActor(null);
        map.setPlayer(this);
        map.getCell(x, y).setActor(this);
        this.setCell(map.getCell(x, y));
    }

    @Override
    public int getAttack() {
        int resAttack = super.getAttack();
        for (Item item: this.getInventory()) {
            if (item.hasAttackModifier()) {
                resAttack += item.modifyAttack(super.getAttack());
            }
        }
        return resAttack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
