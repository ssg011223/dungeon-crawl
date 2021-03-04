package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.Axe;
import com.codecool.dungeoncrawl.logic.items.Health;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    GameMap gameMap = new GameMap(7, 7, CellType.FLOOR);
    Axe axe;
    Health health;
    Key key;
    Sword sword;

    @BeforeEach
    void createItemsOnMap() {
        axe = new Axe(gameMap.getCell(1, 2));
        health = new Health(gameMap.getCell(1, 3));
        key = new Key(gameMap.getCell(1, 4));
        sword = new Sword(gameMap.getCell(1, 5));
    }

    @Test
    void pickUp_PlayerAddedOnCell_ItemPickedUpCorrectly() {
        Player player = new Player(gameMap.getCell(1, 2));

        axe.pickUp(player);

        assertEquals(axe, player.getInventory().get(0));
    }

    @Test
    void getCell_CellCoordinatesAdded_ItemCorrectlyReturned() {
        assertEquals(1, health.getCell().getX());
        assertEquals(3, health.getCell().getY());
        assertEquals(health, gameMap.getCell(1, 3).getItem());
    }

    @Test
    void getCell_OffMapCoordinates_ThrowsError() {
        assertThrows(IndexOutOfBoundsException.class, () -> gameMap.getCell(-1, 0).getItem());
    }


    @Test
    void modifyAttack_DamageAdded_ModifiesAttack() {
        Player player = new Player(gameMap.getCell(1, 2));

        int damageBeforeSword = player.getAttack();

        player.addToInventory(sword);

        int damageWithSword = player.getAttack();

        assertTrue(damageBeforeSword < damageWithSword);
    }

    @Test
    void hasAttackModifier_ShowsModifier_ModifiesCorrectly() {
        

    }

    @Test
    void isConsumable_ItemChecked_ReturnsCorrect() {}


}