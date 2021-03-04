package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Orc;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.Sword;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    GameMap gameMap = new GameMap(7, 7, CellType.FLOOR);

    @Test
    void moveUpdatesCells() {
        Player player = new Player(gameMap.getCell(1, 1));
        player.move(1, 0);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertEquals(null, gameMap.getCell(1, 1).getActor());
        assertEquals(player, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        Player player = new Player(gameMap.getCell(1, 1));
        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveOutOfMap() {
        Player player = new Player(gameMap.getCell(6, 1));
        player.move(1, 0);

        assertEquals(6, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveIntoAnotherActor() {
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertEquals(2, skeleton.getX());
        assertEquals(1, skeleton.getY());
        assertEquals(skeleton, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void actorAtZeroHealthIsNotAlive() {
        Player player = new Player(gameMap.getCell(1, 1));
        player.setHealth(0);
        player.update();
        assertFalse(player.isAlive());
    }

    @Test
    void actorTakesCorrectDamageWhenAttacked() {
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));

        skeleton.setAttack(1);

        int expectedHealth = 14;

        player.damage(skeleton.getAttack());
        assertTrue(player.isAlive());
        assertEquals(expectedHealth, player.getHealth());
    }

    @Test
    void actorDiesAfterMultipleAttacks() {
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));

        skeleton.setAttack(5);

        for (int i = 1; i < 5; i++) {
            player.damage(skeleton.getAttack());
            player.update();
        }
        assertFalse(player.isAlive());
    }

    @Test
    void swordDamageGreaterThanBaseDamage() {
        Player player = new Player(gameMap.getCell(1, 1));
        int damageBeforeSword = player.getAttack();
        player.addToInventory(new Sword(gameMap.getCell(1, 1)));
        int damageWithSword = player.getAttack();

        assertTrue(damageBeforeSword < damageWithSword);
    }

    @Test
    void orcMovesUniquely() {
        Orc orc = new Orc(gameMap.getCell(4,4));

        orc.move(1, 0);

        int expectedX = 6;
        int expectedY = 4;

        assertEquals(expectedX, orc.getX());
        assertEquals(expectedY, orc.getY());
    }
}