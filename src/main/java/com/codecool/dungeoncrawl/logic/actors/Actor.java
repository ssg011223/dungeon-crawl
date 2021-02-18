package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;
    private boolean isAlive;
    private Actor hiddenOccupant;
    private int attack = 10;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
        this.isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getActor() instanceof Door) return;
        CellType nextCellType = nextCell.getType();
        if (nextCell.getActor() instanceof Door) return;
        if (nextCellType.equals(CellType.FLOOR) || nextCellType.equals(CellType.STAIRS) || nextCellType.equals(CellType.TILEDFLOOR)) {
            if (hiddenOccupant == null) cell.setActor(null);
            else if (hiddenOccupant.isAlive) cell.setActor(null);
            else cell.setActor(hiddenOccupant);
            hiddenOccupant = nextCell.getActor();
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    public void damage(int amount) { this.health -= amount; }

    public int getHealth() {
        return health;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Actor getHiddenOccupant() {
        return hiddenOccupant;
    }

    public void setHiddenOccupant(Actor hiddenOccupant) {
        this.hiddenOccupant = hiddenOccupant;
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
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
