package world.worldObject.living;

import helper.ImpossibleActionException;

/**
 * An interface just to use lambdas and have a name that makes sense
 */
public interface Action {
    void execute() throws ImpossibleActionException;
}
