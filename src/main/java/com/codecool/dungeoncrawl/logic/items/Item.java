package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.actors.Player;

import java.io.Serializable;

public abstract class Item implements Drawable, Serializable {
    private Cell cell;
    private boolean consumable = false;
    private boolean hasAttackModifier = false;

    public Item(Cell cell) {
        this.cell = cell;
        this.cell.setItem(this);
    }

    public void pickUp(Player player) {
        this.cell.setItem(null);
        player.addToInventory(this);
    }

    public Cell getCell() {
        return cell;
    }

    public int modifyAttack(int damage) {
        return 0;
    }

    public boolean hasAttackModifier() {
        return hasAttackModifier;
    }

    public void setHasAttackModifier(boolean hasAttackModifier) {
        this.hasAttackModifier = hasAttackModifier;
    }

    public boolean isConsumable() {
        return consumable;
    }

    public void setConsumable(boolean consumable) {
        this.consumable = consumable;
    }
}
