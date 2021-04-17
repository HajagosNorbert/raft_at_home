package world;

import helper.TextColor;

public class Platform implements Tile{
    @Override
    public String getIllustration() {
        return TextColor.BLACK +"\u2588\u2588" +TextColor.RESET;
    }
}
