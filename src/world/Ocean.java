package world;

import helper.TextColor;

public class Ocean extends Tile {
    @Override
    public String toString() {
        return TextColor.CYAN+"\u2591\u2591"+TextColor.RESET;
    }
}
