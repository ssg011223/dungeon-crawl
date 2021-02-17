package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Orc;
import com.codecool.dungeoncrawl.logic.actors.Door;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.Key;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            Skeleton skeleton = new Skeleton(cell);
                            map.addActors(skeleton);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'o':
                            cell.setType(CellType.FLOOR);
                            Orc orc = new Orc(cell);
                            map.addActors(orc);
                            break;
                        case 'd':
                            cell.setType(CellType.STAIRS);
                            new Door(cell);
                            break;
                        case '/':
                            cell.setType(CellType.PIPESTART);
                            break;
                        case '-':
                            cell.setType(CellType.PIPE);
                            break;
                        case 'W':
                            cell.setType(CellType.BOTTOMFLOOR);
                            break;
                        case '_':
                            cell.setType(CellType.TILEDFLOOR);
                            break;
                        case '*':
                            cell.setType(CellType.FILL);
                            break;
                        case ':':
                            cell.setType(CellType.ROCK);
                            break;
                        case 'X':
                            cell.setType(CellType.WALLRUIN);
                            break;
                        case ',':
                            cell.setType(CellType.DEBRIS);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
