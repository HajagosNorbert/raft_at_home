package world.worldObject.living;

import helper.Direction;
import helper.Illustrations;
import world.Illustratable;
import world.Map;
import world.Ocean;

import java.util.Random;

/**
 * the shark class
 */
public class Shark extends Character implements Illustratable {
    private static final Random r;

    static {
        r = new Random();
    }

    public Shark(int x, int y) {
        super(x, y);
    }

    /**
     * moves in a random direction
     * @param map
     */
    public void moveRandomly(Map map) {
        Direction[] directions = Direction.values();
        Direction randomDir;
        int randomPosX;
        int randomPosY;
        int randomTryCounter = 0;
        final int maxRandomTries = 40;
        while (true) {
            if (randomTryCounter++ > maxRandomTries) {
                randomDir = Direction.CURRENT;
                break;
            }
            randomDir = directions[r.nextInt(directions.length)];
            randomPosX = getNewX(randomDir);
            randomPosY = getNewY(randomDir);
            if (outOfWorld(randomPosX, randomPosY, map.height, map.width)) continue;
            if (!(map.getTile(randomPosX, randomPosY) instanceof Ocean)) continue;
            break;
        }

        move(randomDir);
    }

    public String getIllustration() {
        return Illustrations.getSharkIllustration();
    }
}
