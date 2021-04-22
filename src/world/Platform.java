package world;

import helper.Illustrations;

public class Platform implements Tile{
    @Override
    public String getIllustration() {
        return Illustrations.getPlatformIllustration();
    }
}
