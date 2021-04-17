package world;

import java.util.Arrays;

public class Map {

    private final Tile[][] tiles;
    public final int height;
    public final int width;

    public Map(int width, int height){
        this.width = width;
        this.height = height;

        tiles = new Tile[height][width];
        for (Tile[] row:tiles) {
            Arrays.fill(row, new Ocean());
        }

        //raft in the middle
        tiles[height /2][width /2] = new Platform();
        tiles[height /2][width /2 + 1] = new Platform();
        tiles[height /2 + 1][width /2] = new Platform();
        tiles[height /2 + 1][width /2 + 1] = new Platform();


    }

    public String[][] getTileIllustrations() {
        String[][] out = new String[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                out[y][x] = tiles[y][x].getIllustration();
            }
        }
        return out;
    }
}
