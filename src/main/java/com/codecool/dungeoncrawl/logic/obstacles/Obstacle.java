package com.codecool.dungeoncrawl.logic.obstacles;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.actors.Player;

import java.io.Serializable;

public abstract class Obstacle implements Drawable, Serializable {
    private Cell cell;

    public Obstacle(Cell cell) {
        this.cell = cell;
        this.cell.setObstacle(this);
    }

    public Cell getCell() {
        return this.cell;
    }

    public abstract void remove(Player player);
}
