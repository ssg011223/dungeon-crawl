package com.codecool.dungeoncrawl.logic.obstacles;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.actors.Player;

public abstract class Obstacle implements Drawable {
    private Cell cell;

    public Obstacle(Cell cell) {
        this.cell = cell;
    }

    public abstract void remove(Player player);
}
