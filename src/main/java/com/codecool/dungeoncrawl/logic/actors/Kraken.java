package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Kraken extends Actor{

    public Kraken(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "kraken";
    }
}
