package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.Axe;
import com.codecool.dungeoncrawl.logic.items.Sword;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    GameMap gameMap = new GameMap(7, 7, CellType.FLOOR);

    @Test
    void move_PlayerPlacedAndMoved_CellsUpdateToActorMove() {
        Player player = new Player(gameMap.getCell(1, 1));
        player.move(1, 0);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertEquals(null, gameMap.getCell(1, 1).getActor());
        assertEquals(player, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void move_WallPlaced_CannotMoveThroughWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        Player player = new Player(gameMap.getCell(1, 1));
        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void move_CommandedToMoveOffMap_CannotMove() {
        Player player = new Player(gameMap.getCell(6, 1));
        player.move(1, 0);

        assertEquals(6, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void move_PathBlockedByEnemy_ActorCannotMoveThrough() {
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
    void isAlive_ActorHealthReduced_ActorIsNotAlive() {
        Player player = new Player(gameMap.getCell(1, 1));
        player.setHealth(0);
        player.update();
        assertFalse(player.isAlive());
    }

    @Test
    void damage_ActorAttacked_ActorTakesCorrectDamage() {
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));

        skeleton.setAttack(1);

        int expectedHealth = 14;

        player.damage(skeleton.getAttack());
        assertTrue(player.isAlive());
        assertEquals(expectedHealth, player.getHealth());
    }

    @Test
    void isAlive_MultipleAttacksByEnemy_ActorIsNotAlive() {
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
    void damage_WeaponAddedToPlayerInventory_DamageGreaterThanBefore() {
        Player player = new Player(gameMap.getCell(1, 1));
        int damageBeforeSword = player.getAttack();
        player.addToInventory(new Sword(gameMap.getCell(1, 1)));
        int damageWithSword = player.getAttack();

        assertTrue(damageBeforeSword < damageWithSword);
    }

    @Test
    void move_MovesPassedToOrc_OrcMovesDouble() {
        Orc orc = new Orc(gameMap.getCell(4,4));

        orc.move(1, 0);

        assertEquals(6, orc.getX());
        assertEquals(4, orc.getY());
    }

    @Test
    void move_MovesPassedToBoss_BossDoesNotMove() {
        Boss boss = new Boss(gameMap.getCell(4, 4));
        boss.move(1,0);

        assertEquals(4, boss.getX());
        assertEquals(4, boss.getY());
    }

    @Test
    void move_WallsPlacedInPath_GhostMovesThroughWalls() {
        gameMap.getCell(2, 1).setType(CellType.WALL);

        Ghost ghost = new Ghost(gameMap.getCell(1, 1));

        ghost.move(1, 0);

        assertEquals(2, ghost.getX());
        assertEquals(1, ghost.getY());
    }

    @Test
    void move_OnlyWaterTiles_KrakenMovesCorrectly() {
        gameMap.getCell(2, 1).setType(CellType.WATER);
        gameMap.getCell(3, 1).setType(CellType.WATER);

        Kraken kraken = new Kraken(gameMap.getCell(2, 1));

        kraken.move(-1, 0);                 // Floor below - Fail to move
        assertEquals(2, kraken.getX());
        assertEquals(1, kraken.getY());

        kraken.move(0, 1);                  // Floor right - Fail to move
        assertEquals(2, kraken.getX());
        assertEquals(1, kraken.getY());

        kraken.move(0, -1);                 // Floor left - Fail to move
        assertEquals(2, kraken.getX());
        assertEquals(1, kraken.getY());

        kraken.move(1, 0);                  // Water above - Move success
        assertEquals(3, kraken.getX());
        assertEquals(1, kraken.getY());
    }

    @Test
    void getTileName_ActorAlive_ReturnsCorrect() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(1, 1));

        String livingSkeleton = skeleton.getTileName();
        boolean skeletonAlive = skeleton.isAlive();

        skeleton.setHealth(0);
        skeleton.update();

        String deadSkeleton = skeleton.getTileName();
        boolean skeletonDead = skeleton.isAlive();

        assertTrue(skeletonAlive);
        assertEquals("skeleton", livingSkeleton);

        assertFalse(skeletonDead);
        assertEquals("bones", deadSkeleton);
    }

    @Test
    void move_PlayerMovesOverDeadSkeleton_SkeletonAddedAsHiddenOccupant() {
        Player player = new Player(gameMap.getCell(2,3));
        Skeleton skeleton = new Skeleton(gameMap.getCell(3, 3));

        skeleton.setHealth(0);
        skeleton.update();

        player.move(1, 0);

        assertEquals(3, player.getX());
        assertEquals(3, player.getY());
        assertEquals(skeleton, player.getHiddenOccupant());
    }

    @Test
    void getInventory_PickedUpObject_AppearsInInventory() {
        Player player = new Player(gameMap.getCell(2,3));
        Sword sword = new Sword(gameMap.getCell(3, 3));

        player.move(1,0);
        player.getCell().getItem().pickUp(player);

        assertEquals(sword, player.getInventory().get(0));
    }

    @Test
    void removeFromInventory_ItemToBeRemovedFromInventory_ItemRemoved() {
        Player player = new Player(gameMap.getCell(2,3));
        Sword sword = new Sword(gameMap.getCell(3, 3));
        Axe axe = new Axe(gameMap.getCell(1,1));

        player.addToInventory(sword);
        player.addToInventory(axe);

        assertEquals(2, player.getInventory().size());

        player.removeFromInventory(sword);

        assertEquals(axe, player.getInventory().get(0));
    }

    @Test
    void setName_PlayerNameChange_NameChanged() {
        Player player = new Player(gameMap.getCell(2,3));

        player.setName("Player1");

        assertEquals("Player1", player.getName());
    }

    @Test
    void Teleport_PlayerCanTeleport_WithoutError() {
        GameMap gameMapSecond = new GameMap(5, 5, CellType.FLOOR);
        Player player = new Player(gameMap.getCell(2,3));

        player.teleport(gameMapSecond, 2, 2);

        assertEquals(player.getCell(), gameMapSecond.getPlayer().getCell());
    }

    @Test
    void getTileName_HealthReduced_LeavesBonesTiles() {
        Orc orc = new Orc(gameMap.getCell(1,2));

        orc.setHealth(0);
        orc.update();

        assertFalse(orc.isAlive());
        assertEquals("bones", orc.getTileName());
    }

    @Test
    void getTileName_GhostDies_TilesNameReturned() {
        Ghost ghost = new Ghost(gameMap.getCell(1, 2));

        ghost.setHealth(0);
        ghost.update();

        assertEquals(ghost.getCell().getTileName(), ghost.getTileName());
    }

}