package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Axe;
import com.codecool.dungeoncrawl.logic.obstacles.Tree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void getNeighbor() {
        Cell cell = map.getCell(1, 1);
        Cell neighbor = cell.getNeighbor(-1, 0);
        assertEquals(0, neighbor.getX());
        assertEquals(1, neighbor.getY());
    }

    @Test
    void cellOnEdgeHasNoNeighbor() {
        Cell cell = map.getCell(1, 0);
        assertEquals(null, cell.getNeighbor(0, -1));

        cell = map.getCell(1, 2);
        assertEquals(null, cell.getNeighbor(0, 1));
    }

    @Test
    void setItemPlacesItemsCorrectly() {
        Axe axe = new Axe(map.getCell(1, 1));
        Cell cell = map.getCell(1, 0);
        cell.setItem(axe);
        assertEquals(axe, cell.getItem());
        assertNull(cell.getObstacle());
    }

    @Test
    void createItemOffMapThrowsError() {
        assertThrows(IndexOutOfBoundsException.class, () -> new Axe(map.getCell(1, -1)));
    }

    @Test
    void createObstacleOffMapThrowsError() {
        assertThrows(IndexOutOfBoundsException.class, () -> new Tree(map.getCell(1, -1)));
    }

    @Test
    void cellOffMapThrowsError() {
        assertThrows(IndexOutOfBoundsException.class, () -> map.getCell(1, -1));
    }

    @Test
    void obstacleSetCorrectlyReturnsObstacle() {
        Tree tree = new Tree(map.getCell(1, 1));
        Cell cell = map.getCell(1, 0);
        cell.setObstacle(tree);
        assertEquals(tree, cell.getObstacle());
        assertNull(cell.getItem());
    }

    @Test
    void correctTileNameReturned() {
        assertEquals("floor", map.getCell(1,1).getTileName());
    }

    @Test
    void correctTileTypeReturned() {
        assertEquals(CellType.FLOOR, map.getCell(1,1).getType());
    }

    @Test
    void correctXCoordinateReturned() {
        Cell cell = map.getCell(1,0);
        int expectedX = 1;
        assertEquals(expectedX, cell.getX());
    }

    @Test
    void correctYCoordinateReturned() {
        Cell cell = map.getCell(0,1);
        int expectedY = 1;
        assertEquals(expectedY, cell.getY());
    }

}