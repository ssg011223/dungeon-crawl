package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Kraken extends Actor{

    public Kraken(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "kraken";
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = getCell().getNeighbor(dx, dy);
        CellType nextCellType = nextCell.getType();
        if (nextCellType.equals(CellType.WATER)) {
            getCell().setActor(null);
            nextCell.setActor(this);
            setCell(nextCell);
        }
    }
}
