package world;

import helper.TextColor;

public class Platform extends Tile{
    @Override
    public String toString() {
        return TextColor.BLACK +"\u2588\u2588" +TextColor.RESET;
    }
}
