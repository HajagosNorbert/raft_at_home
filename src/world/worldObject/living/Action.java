package world.worldObject.living;

import helper.Direction;

import java.util.concurrent.Callable;
import java.util.function.Function;

public abstract class Action {
    protected Direction direction;
    protected Character character;

    public Action(Character character, Direction direction) {
        this.direction = direction;
        this.character = character;
    }

    public abstract void execute();
}
