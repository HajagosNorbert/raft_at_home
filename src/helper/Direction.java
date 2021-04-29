package helper;

/**
 * Has all 8 + 1 directions for a 2D plane
 */
public enum Direction {
    DOWN_LEFT(-1, 1), DOWN(0, 1), DOWN_RIGHT(1, 1), LEFT(-1, 0), CURRENT(0, 0), RIGHT(1, 0), UP_LEFT(-1, -1), UP(0, -1), UP_RIGHT(1, -1);

    public final int x;
    public final int y;

    /**
     * Initialize with coordinates
     * @param x
     * @param y
     */
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * turns a keypad number to a direction
     * @param code
     * @return
     */
    public static Direction directionCodeToDirection(int code) {
        return switch (code) {
            case 1 -> DOWN_LEFT;
            case 2 -> DOWN;
            case 3 -> DOWN_RIGHT;
            case 4 -> LEFT;
            case 5 -> CURRENT;
            case 6 -> RIGHT;
            case 7 -> UP_LEFT;
            case 8 -> UP;
            default -> UP_RIGHT;
        };
    }

}
