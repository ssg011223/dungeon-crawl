package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell);
        this.setAttack(1);
    }

    @Override
    public String getTileName() {
        if (isAlive()) return "skeleton";
        return "bones";
        }
}

