package world.worldObject.living;

import helper.Direction;
import helper.ImpossibleActionException;

public class Character {
    private int x;
    private int y;

    public Character(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(Direction dir){
        int newX = getX() + dir.x;
        int newY = getY() + dir.y;

        setX(newX);
        setY(newY);
    }

    protected int getNewX(Direction direction) {
        return getX() + direction.x;
    }

    protected void throwExceptionIfOutOfWorld(int x, int y, int height, int width) throws ImpossibleActionException {
        if (outOfWorld(x, y, height, width))throw new ImpossibleActionException("Out of world");
    }

    protected boolean outOfWorld(int x, int y, int height, int width){
        return y < 0 || y >= height || x < 0 || x >= width;
    }

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
