package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Orc extends Actor{

    public Orc(Cell cell) {
        super(cell);
        this.setAttack(5);
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell;
        try {
            nextCell = getCell().getNeighbor(dx * 2, dy * 2);
        } catch (ArrayIndexOutOfBoundsException ex) {
            nextCell = getCell().getNeighbor(dx, dy);
        }
        if (nextCell.getType().equals(CellType.FLOOR)) {
            if (getHiddenOccupant() == null || getHiddenOccupant().isAlive()) getCell().setActor(null);
            else getCell().setActor(getHiddenOccupant());
            setHiddenOccupant(nextCell.getActor());
            nextCell.setActor(this);
            setCell(nextCell);
        }
    }

    @Override
    public String getTileName() {
        if (isAlive()) return "orc";
        return "bones";
    }
}
