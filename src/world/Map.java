package world;

import world.worldObject.supply.Supply;

import java.util.Arrays;

public class Map {

    private final Tile[][] tiles;
    public final int height;
    public final int width;
    public final int maxSupplyGeneratedPerTurn;

    {
        maxSupplyGeneratedPerTurn = 3;
    }

    public Tile getTile(int x, int y) {
        return tiles[y][x];
    }

    public Map(int width, int height) {
        this.width = width;
        this.height = height;

        tiles = new Tile[height][width];
        for (Tile[] row : tiles) {
            for(Tile tile: row){
                tile = new Ocean();
            }
        }
        for (int y = 0; y<height;y++) {
            for(int x = 0; x < width; x++){
                tiles[y][x] = new Ocean();
            }
        }

        placeRaftOnTheMIddle();

    }

    private void placeRaftOnTheMIddle() {
        tiles[height / 2][width / 2] = new Platform();
        tiles[height / 2][width / 2 + 1] = new Platform();
        tiles[height / 2 + 1][width / 2] = new Platform();
        tiles[height / 2 + 1][width / 2 + 1] = new Platform();
    }

    public void spawnSupplies() {
        int generatedSupplyAmmount = (int) (Math.random() * (maxSupplyGeneratedPerTurn + 1));
        int supplyXPosition;
        for (int i = 0; i < generatedSupplyAmmount; i++) {
            supplyXPosition = (int) (Math.random() * width);
            spawnSupply(supplyXPosition, getRandomSupplyType());
        }
    }

    private Supply getRandomSupplyType() {
        double randomNumber = Math.random();
        if (randomNumber < 0.32) return Supply.WOOD;
        if (randomNumber < 0.64) return Supply.LEAF;
        if (randomNumber < 0.96) return Supply.DEBRIS;
        return Supply.BARREL;
    }

    private void spawnSupply(int xPos, Supply supply) {
        Tile tile = getTile(xPos, 0);
        if (!(tile instanceof Ocean)) {
            return;
        }
        Ocean ocean = (Ocean) tile;
        ocean.setSupply(supply);
    }

    public void flowSupplies(){

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
