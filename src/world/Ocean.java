package world;

import helper.TextColor;

public class Ocean implements Tile {
    @Override
    public String getIllustration() {
        return TextColor.CYAN+"\u2591\u2591"+TextColor.RESET;
    }
}
