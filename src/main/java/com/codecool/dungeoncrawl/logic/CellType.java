package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    STAIRS("stairs"),
    PIPESTART("pipe-start"),
    PIPE("pipe"),
    BOTTOMFLOOR("bottom-floor"),
    TILEDFLOOR("tiled-floor"),
    FILL("fill"),
    ROCK("rock"),
    WALLRUIN("wall-ruin");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
