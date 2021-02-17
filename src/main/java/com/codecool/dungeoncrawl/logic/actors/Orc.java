package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Orc extends Actor{

    public Orc(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        if (isAlive()) return "orc";
        return "bones";
    }
}
