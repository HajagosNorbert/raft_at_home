package world.worldObject.living;

import helper.Direction;
import helper.ImpossibleActionException;

/**
 * any moving character on the map
 */
public class Character {
    private int x;
    private int y;

    public Character(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * moves to the specified direction
     * @param dir
     */
    public void move(Direction dir) {
        int newX = getX() + dir.x;
        int newY = getY() + dir.y;



        setX(newX);
        setY(newY);
    }

    /**
     * Calculates the new position of the character based on the given direction
     * @param direction
     * @return
     */
    protected int getNewX(Direction direction) {
        return getX() + direction.x;
    }

    /**
     *
     * @param x
     * @param y
     * @param height
     * @param width
     * @throws ImpossibleActionException if outOfWorld returns true
     */
    protected void throwExceptionIfOutOfWorld(int x, int y, int height, int width) throws ImpossibleActionException {
        if (outOfWorld(x, y, height, width)) throw new ImpossibleActionException("Out of world");
    }

    /**
     *
     * @param x
     * @param y
     * @param height
     * @param width
     * @return true if out of world
     */
    protected boolean outOfWorld(int x, int y, int height, int width) {
        return y < 0 || y >= height || x < 0 || x >= width;
    }
    /**
     * Calculates the new position of the character based on the given direction
     * @param direction
     * @return
     */
    protected int getNewY(Direction direction) {
        return getY() + direction.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
