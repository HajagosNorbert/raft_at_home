package world.worldObject.living;

import world.Illustratable;

public class Player extends Character implements Illustratable {

    public Player(int x, int y){
        super(x, y);
    }

    public String getIllustration() {
        //😀
        return "\uD83D\uDE00";
    }
}
