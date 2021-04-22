package world.worldObject.living;
import helper.ImpossibleActionException;

public interface Action {
    void execute() throws ImpossibleActionException;
}
