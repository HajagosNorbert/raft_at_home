package world;

import helper.Illustrations;

public class Ocean implements Tile {
    @Override
    public String getIllustration() {
        return Illustrations.getOceanIllustration();
    }
}
