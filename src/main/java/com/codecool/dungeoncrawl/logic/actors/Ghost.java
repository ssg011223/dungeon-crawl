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

    @Override
    public void move(int dx, int dy) {
        Cell nextCell;
        try {
            nextCell = getCell().getNeighbor(dx, dy);
        } catch (ArrayIndexOutOfBoundsException ex) {
            nextCell = getCell();
        }
        if (nextCell == null) return;
        if (nextCell.getActor() != null && nextCell.getActor().isAlive()) return;
        if (nextCell.getObstacle() != null) return;
        if (getHiddenOccupant() == null) getCell().setActor(null);
        else if (getHiddenOccupant().isAlive()) getCell().setActor(null);
        else getCell().setActor(getHiddenOccupant());
        setHiddenOccupant(nextCell.getActor());
        nextCell.setActor(this);
        setCell(nextCell);
    }
}


