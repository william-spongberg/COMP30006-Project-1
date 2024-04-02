package ore;

import ch.aplu.jgamegrid.Actor;

public abstract class MapObject extends Actor implements Updateable {
    public MapObject (boolean rotatable, String image)
    {
        super(image);
    }
}
