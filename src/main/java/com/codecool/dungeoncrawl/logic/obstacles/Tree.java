package com.codecool.dungeoncrawl.logic.obstacles;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Axe;
import com.codecool.dungeoncrawl.logic.items.Item;

public class Tree extends Obstacle{
    public Tree(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "tree";
    }

    @Override
    public void remove(Player player) {
        for (Item item: player.getInventory()) {
            if (item instanceof Axe) {
                this.getCell().setObstacle(null);
                return;
            }
        }
    }
}
