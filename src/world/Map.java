package world;

import worldObject.Player;
import worldObject.Shark;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Map {

    private final Tile[][] tiles;
    public final int y;
    public final int x;

    {
        y = 25;
        x = 35;
    }


    public Map(){
        tiles = new Tile[y][x];
        for (Tile[] row:tiles) {
            Arrays.fill(row, new Ocean());
        }

        //raft in the middle
        tiles[y/2][x/2] = new Platform();
        tiles[y/2][x/2 + 1] = new Platform();
        tiles[y/2 + 1][x/2] = new Platform();
        tiles[y/2 + 1][x/2 + 1] = new Platform();


        tiles[y/4][x/3] = new Shark();
        tiles[y-10][x/2] = new Player();

    }

    @Override
    public String toString() {
        String out = "";
        for (Tile[] row:tiles) {
            out += Arrays.stream(row).map(tile -> tile.toString()).collect(Collectors.joining()) + System.lineSeparator();
        }
        return out;
    }
}
