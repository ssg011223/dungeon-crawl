package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Axe extends Item{
    public Axe(Cell cell) {
        super(cell);
        this.setHasAttackModifier(true);
    }

    @Override
    public String getTileName() {
        return "axe";
    }

    @Override
    public int modifyAttack(int damage) {
        return 5;
    }
}
