package ore;

import ch.aplu.jgamegrid.Actor;

public abstract class MapObject extends Actor implements Updateable {
    public OreSim.ElementType getType() {
        return type;
    }

    private final OreSim.ElementType type;
    public MapObject (boolean rotatable, String image, OreSim.ElementType type)
    {
        super(image);
        this.type = type;
    }
}
