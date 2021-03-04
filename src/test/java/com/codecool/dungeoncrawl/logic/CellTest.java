package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Axe;
import com.codecool.dungeoncrawl.logic.obstacles.Tree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void getNeighbor_NegativeNumbers_WorksCorrectly() {
        Cell cell = map.getCell(1, 1);
        Cell neighbor = cell.getNeighbor(-1, 0);
        assertEquals(0, neighbor.getX());
        assertEquals(1, neighbor.getY());
    }

    @Test
    void getNeighbor_OffMappCoordinates_ReturnsNull() {
        Cell cell = map.getCell(1, 0);
        assertEquals(null, cell.getNeighbor(0, -1));

        cell = map.getCell(1, 2);
        assertEquals(null, cell.getNeighbor(0, 1));
    }

    @Test
    void setItem_ItemAddedToPlayer_ItemShowsUpInInventory() {
        Axe axe = new Axe(map.getCell(1, 1));
        Cell cell = map.getCell(1, 0);
        cell.setItem(axe);
        assertEquals(axe, cell.getItem());
        assertNull(cell.getObstacle());
    }

    @Test
    void ItemConstructor_PlacedOutOfBound_ThrowsError() {
        assertThrows(IndexOutOfBoundsException.class, () -> new Axe(map.getCell(1, -1)));
    }

    @Test
    void ObstacleConstructor_PlacedOutOfBound_ThrowsError() {
        assertThrows(IndexOutOfBoundsException.class, () -> new Tree(map.getCell(1, -1)));
    }

    @Test
    void getCell_OffMapCoordinates_ThrowsError() {
        assertThrows(IndexOutOfBoundsException.class, () -> map.getCell(1, -1));
    }

    @Test
    void setObstacle_ObstacleAddedToMap_MapShowsObstacle() {
        Tree tree = new Tree(map.getCell(1, 1));
        Cell cell = map.getCell(1, 0);
        cell.setObstacle(tree);
        assertEquals(tree, cell.getObstacle());
        assertNull(cell.getItem());
    }

    @Test
    void getTileName_MapCoordinateSelected_TileNameOfCoordinateShownCorrectly() {
        assertEquals("floor", map.getCell(1,1).getTileName());
    }

    @Test
    void getType_CellTypeCreated_CorrectCellTypeShown() {
        assertEquals(CellType.FLOOR, map.getCell(1,1).getType());
    }

    @Test
    void getX_CellCreated_CorrectCoordinateReturned() {
        Cell cell = map.getCell(1,0);
        int expectedX = 1;
        assertEquals(expectedX, cell.getX());
    }

    @Test
    void getY_CellCreated_CorrectCoordinateReturned() {
        Cell cell = map.getCell(0,1);
        int expectedY = 1;
        assertEquals(expectedY, cell.getY());
    }

}