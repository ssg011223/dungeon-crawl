package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;

public class Door extends Actor{
    public Door(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "door";
    }

    public void open(Player player) {
        for (Item item: player.getInventory()) {
            if (item instanceof Key) {
                this.getCell().setActor(null);
                player.removeFromInventory(item);
                return;
            }
        }
    }
}
