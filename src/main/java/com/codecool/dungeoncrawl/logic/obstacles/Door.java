package com.codecool.dungeoncrawl.logic.obstacles;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;

public class Door extends Obstacle{
    public Door(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "door";
    }

    @Override
    public void remove(Player player) {
        for (Item item: player.getInventory()) {
            if (item instanceof Key) {
                this.getCell().setObstacle(null);
                player.removeFromInventory(item);
                return;
            }
        }
    }
}
