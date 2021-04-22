package world.worldObject.living;

import helper.Illustrations;
import world.Illustratable;

public class Shark extends Character implements Illustratable {

    public Shark(int x, int y) {
        super(x, y);
    }

    public String getIllustration() {
        return Illustrations.getSharkIllustration();
    }
}
