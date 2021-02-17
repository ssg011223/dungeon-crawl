package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;
    private boolean isAlive;
    private Actor hiddenOccupant;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
        this.isAlive = true;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().equals(CellType.FLOOR)) {
            if (hiddenOccupant == null) cell.setActor(null);
            else cell.setActor(hiddenOccupant);
            hiddenOccupant = nextCell.getActor();
            if (nextCell.getActor() instanceof Door) return;
            if ((nextCell.getType().equals(CellType.FLOOR) || nextCell.getType().equals(CellType.STAIRS)) && isAlive) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
    }

    public void damage(int amount) { this.health -= amount; }

    public int getHealth() {
        return health;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void update() {
        if (this.health < 1) {
            this.isAlive = false;
        }
    };
}
