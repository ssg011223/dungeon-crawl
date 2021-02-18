package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Boss extends Actor{
    public Boss(Cell cell) {
        super(cell);
        this.setHealth(60);
        this.setAttack(10);
    }

    @Override
    public String getTileName() {
        return "boss";
    }

    @Override
    public void move(int dx, int dy) { }
}
