package world.worldObject.living;

import world.Illustratable;

public class Shark extends Character implements Illustratable {

    public Shark(int x, int y) {
        super(x, y);
    }

    public String getIllustration() {
        // SHARK 🦈
        return "\uD83E\uDD88";
    }
}
