package world;

import world.worldObject.build.Building;
import world.worldObject.build.Net;
import world.worldObject.build.Platform;
import world.worldObject.supply.Supply;

/**
 * contains the tiles
 */
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
/**
 * creates the base map
 */
    public Map(int width, int height) {
        this.width = width;
        this.height = height;

        tiles = new Tile[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = new Ocean();
            }
        }

        placeRaftOnTheMiddle();
    }

    /**
     * places a platform on the specified tile
     * @param x
     * @param y
     */
    public void placePlatform(int x, int y) {
        tiles[y][x] = new Platform();
    }
    /** places a building on the specified tile
     *
     */
    public void placeBuilding(Building building, int x, int y) {
        getTile(x, y).setBuilding(building);
    }

    /**
     * places some platform on the middle
     */
    private void placeRaftOnTheMiddle() {
        tiles[height / 2][width / 2] = new Platform();
        tiles[height / 2][width / 2 + 1] = new Platform();
        tiles[height / 2 + 1][width / 2] = new Platform();
        tiles[height / 2 + 1][width / 2 + 1] = new Platform();
    }

    /**
     * supplies apear on the top
     */
    public void spawnSupplies() {
        int generatedSupplyAmount = (int) (Math.random() * (maxSupplyGeneratedPerTurn + 1));
        int supplyXPosition;
        for (int i = 0; i < generatedSupplyAmount; i++) {
            supplyXPosition = (int) (Math.random() * width);
            spawnSupply(supplyXPosition, getRandomSupplyType());
        }
    }

    /**
     * gets a wood, leaf, debris, or barrel
     * @return
     */
    private Supply getRandomSupplyType() {
        double randomNumber = Math.random();
        if (randomNumber < 0.32) return Supply.WOOD;
        if (randomNumber < 0.64) return Supply.LEAF;
        if (randomNumber < 0.96) return Supply.DEBRIS;
        return Supply.BARREL;
    }

    /**
     * spwans a single supply on location
     * @param xPos
     * @param supply
     */
    private void spawnSupply(int xPos, Supply supply) {
        Tile tile = getTile(xPos, 0);
        if (!(tile instanceof Ocean ocean)) {
            return;
        }
        ocean.setSupply(supply);
    }

    /**
     * all supplies start to go down 1 line
     */
    public void beginFlowSupplies() {
        destroyBottomRowSupplies();

        for (int y = height - 2; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                if (!(getTile(x, y) instanceof Ocean)) continue;
                if (((Ocean) getTile(x, y)).getSupply() == null) continue;
                flowSupplyDown(x, y);
            }
        }
    }

    /**
     * make supplies on the bottom dissapear
     */
    private void destroyBottomRowSupplies() {
        for (int x = 0; x < width; x++) {
            try {
                ((Ocean) getTile(x, height - 1)).setSupply(null);
            } catch (Exception e) {

            }
        }
    }

    /**
     * flows a single supply down and decides if it will be caught by a net or destroyed, or transmited
     * @param x
     * @param y
     */
    private void flowSupplyDown(int x, int y) {
        Ocean upstreamOcean = ((Ocean) getTile(x, y));
        Tile downstreamTile = getTile(x, y + 1);

        if (downstreamTile.getBuilding() instanceof Net) {
            ((Net) downstreamTile.getBuilding()).catchSupply(upstreamOcean.getSupply());
        } else if ((downstreamTile instanceof Ocean)) {
            ((Ocean) downstreamTile).setSupply(upstreamOcean.getSupply());
        }
        upstreamOcean.setSupply(null);
    }

    /**
     * gets every tile's illustration as a matrix
     * @return
     */
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
