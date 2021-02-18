package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.obstacles.*;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(String mapFile) {
        InputStream is = MapLoader.class.getResourceAsStream("/" + mapFile);
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
                            map.addActors(new Skeleton(cell));
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
                            map.addActors(new Orc(cell));
                            break;
                        case 'd':
                            cell.setType(CellType.STAIRS);
                            new Door(cell);
                            break;
                        case 'S':
                            cell.setType(CellType.STAIRS);
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
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case '~':
                            cell.setType(CellType.WATER);
                            break;
                        case 'g':
                            map.addActors(new Ghost(cell));
                            cell.setType(CellType.FLOOR);
                            break;
                        case 'K':
                            map.addActors(new Kraken(cell));
                            cell.setType(CellType.WATER);
                            break;
                        case 'h':
                            cell.setType(CellType.FLOOR);
                            new Health(cell);
                            break;
                        case 't':
                            cell.setType(CellType.FLOOR);
                            new Tree(cell);
                            break;
                        case 'a':
                            cell.setType(CellType.FLOOR);
                            new Axe(cell);
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            Boss boss = new Boss(cell);
                            map.addActors(boss);
                            map.setBoss(boss);
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
