package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Sword extends Item{
    public Sword(Cell cell) {
        super(cell);
        this.setHasAttackModifier(true);
    }

    @Override
    public String getTileName() {
        return "sword";
    }

    @Override
    public int modifyAttack(int damage) {
        return damage * 2;
    }
}
