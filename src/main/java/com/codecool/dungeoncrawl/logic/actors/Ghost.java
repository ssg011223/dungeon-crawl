package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.lang.reflect.Array;

public class Ghost extends Actor{
    public Ghost(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        if (isAlive()) return "ghost";
        return getCell().getTileName();
    }

}


