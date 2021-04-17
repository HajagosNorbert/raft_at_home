package helper;

public enum Direction {
    DOWN(0, -1),
    UP(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    DOWN_LEFT(-1, -1),
    UP_LEFT(-1, 1),
    DOWN_RIGHT(1, -1),
    UP_RIGHT(1, 1);

    public final int x;
    public final int y;

    Direction(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static Direction directionCodeToDirection(int code){
        switch (code){
            case 1:
                return DOWN_LEFT;
            case 2:
                return DOWN;
            case 3:
                return DOWN_RIGHT;
        }


        return DOWN;

    }

}
