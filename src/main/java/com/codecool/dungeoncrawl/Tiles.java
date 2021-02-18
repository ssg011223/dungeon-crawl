package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(5, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(27, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("key", new Tile(16, 23));
        tileMap.put("bones", new Tile(0,15));
        tileMap.put("orc", new Tile(26,2));
        tileMap.put("door", new Tile(10, 9));
        tileMap.put("stairs", new Tile(1, 11));
        tileMap.put("pipe-start", new Tile(8, 12));
        tileMap.put("pipe", new Tile(9, 12));
        tileMap.put("bottom-floor", new Tile(17, 13));
        tileMap.put("tiled-floor", new Tile(16,0));
        tileMap.put("fill", new Tile(6,0));
        tileMap.put("rock", new Tile(5, 2));
        tileMap.put("wall-ruin", new Tile(2, 11));
        tileMap.put("debris", new Tile(1, 0));
        tileMap.put("sword", new Tile(1, 29));
        tileMap.put("water", new Tile(8,5));
        tileMap.put("ghost", new Tile(26,6));
        tileMap.put("kraken", new Tile(25,8));
        tileMap.put("health", new Tile(23, 22));
        tileMap.put("tree", new Tile(4, 2));
        tileMap.put("axe", new Tile(8, 30));
        tileMap.put("boss", new Tile(31, 0));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
