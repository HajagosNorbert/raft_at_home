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
