package world.worldObject.living;

import helper.Direction;
import helper.ImpossibleActionException;
import world.Map;

public class MoveAction extends Action {

    public MoveAction(Map map, Character character , Direction direction) throws ImpossibleActionException {
        super(character, direction);

        int newX = character.getX() + direction.x;
        int newY = character.getY() + direction.y;

        if(newY < 0 || newY >= map.height || newX < 0 || newX >= map.width){
            throw new ImpossibleActionException("Out of the world");
        }
    }

    public void execute(){
        character.move(direction);
    }
}
