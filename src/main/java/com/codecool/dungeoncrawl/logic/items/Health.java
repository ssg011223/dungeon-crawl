package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class Health extends Item{
    public Health(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "health";
    }

    @Override
    public void pickUp(Player player) {
        super.getCell().setItem(null);
        player.setHealth(player.getHealth() + 5);
    }
}
