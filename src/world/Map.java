package world;

import world.worldObject.build.Building;
import world.worldObject.build.Net;
import world.worldObject.build.Platform;
import world.worldObject.supply.Supply;

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
            for (Tile tile : row) {
                tile = new Ocean();
            }
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = new Ocean();
            }
        }

        placeRaftOnTheMIddle();
    }

    public void placePlatform(int x, int y) {
        tiles[y][x] = new Platform();
    }

    public void placeBuilding(Building building, int x, int y) {
        getTile(x, y).setBuilding(building);
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

    public void beginFlowSupplies() {
        destroyBottomRowSupplies();

        for (int y = height - 2; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                if (!(getTile(x, y) instanceof Ocean)) continue;
                if (((Ocean) getTile(x, y)).getSupply() == null) continue;
                flowSuppyDown(x, y);
            }
        }
    }

    private void destroyBottomRowSupplies() {
        for (int x = 0; x < width; x++) {
            try {
                ((Ocean) getTile(x, height - 1)).setSupply(null);
            } catch (Exception e) {
                continue;
            }
        }
    }

    private void flowSuppyDown(int x, int y) {
        Ocean upstreamOcean = ((Ocean) getTile(x, y));
        Tile downstreamTile = getTile(x, y + 1);

        if (downstreamTile.getBuilding() instanceof Net) {
            ((Net) downstreamTile.getBuilding()).catchSupply(upstreamOcean.getSupply());
        } else if((downstreamTile instanceof Ocean)){
            ((Ocean) downstreamTile).setSupply(upstreamOcean.getSupply());
        }
        upstreamOcean.setSupply(null);
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
