package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Orc extends Actor{

    enum Direction {
        SOUTH,
        WEST,
        NORTH,
        EAST
    }

    private Direction facing = Direction.SOUTH;
    int steps = 0;
    int turns = 0;

    public Orc(Cell cell) {
        super(cell);
        this.setAttack(5);
    }

    private void patrolling() {
        if (facing == Direction.SOUTH && getCell().getNeighbor(0,1).equals(CellType.FLOOR)) {
            orcStep(getCell().getNeighbor(0,1));
            steps++;
        }
        if (facing == Direction.WEST && getCell().getNeighbor(-1,0).equals(CellType.FLOOR)) {
            orcStep(getCell().getNeighbor(-1,0));
            steps++;
        }
        if (facing == Direction.NORTH && getCell().getNeighbor(0,-1).equals(CellType.FLOOR)) {
            orcStep(getCell().getNeighbor(0,-1));
            steps++;
        }
        if (facing == Direction.EAST && getCell().getNeighbor(-1,0).equals(CellType.FLOOR)) {
            orcStep(getCell().getNeighbor(-1,0));
            steps++;
        }
        if (steps == 2) changeFacing();
    }

    private void changeFacing() {
            if (facing == Direction.SOUTH) facing = Direction.WEST;
            else if (facing == Direction.WEST) facing = Direction.NORTH;
            else if (facing == Direction.NORTH) facing = Direction.EAST;
            else if (facing == Direction.EAST) facing = Direction.SOUTH;
            steps = 0;
    }

    private void orcStep(Cell nextCell) {
        if (nextCell.getType().equals(CellType.FLOOR)) {
            if (getHiddenOccupant() == null || getHiddenOccupant().isAlive()) getCell().setActor(null);
            else getCell().setActor(getHiddenOccupant());
            setHiddenOccupant(nextCell.getActor());
            nextCell.setActor(this);
            setCell(nextCell);
        }
    }

    @Override
    public void move(int dx, int dy) {
        if (turns == 0) {
            Cell nextCell;
            try {
                nextCell = getCell().getNeighbor(dx * 2, dy * 2);
            } catch (ArrayIndexOutOfBoundsException ex) {
                nextCell = getCell().getNeighbor(dx, dy);
            }
            orcStep(nextCell);
            turns ++;
        } else if (turns > 10 && turns < 15) {
            patrolling();
            turns++;
        } else {
            turns = 0;
        }
    }

    @Override
    public String getTileName() {
        if (isAlive()) return "orc";
        return "bones";
    }
}
