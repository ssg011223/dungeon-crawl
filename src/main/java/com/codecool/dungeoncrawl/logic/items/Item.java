package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.actors.Player;

public abstract class Item implements Drawable {
    private Cell cell;
    private boolean consumable;
    private boolean hasAttackModifier;

    public Item(Cell cell) {
        this.cell = cell;
        this.cell.setItem(this);
    }

    public void pickUp(Player player) {
        this.cell.setItem(null);
        player.addToInventory(this);
    }

    public int modifyAttack(int damage) {
        return 0;
    }

    public boolean isHasAttackModifier() {
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
