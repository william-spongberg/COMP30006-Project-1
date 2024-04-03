package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class Clay extends MapObject implements Destroyable
{
    private DestroyListener listener;

    public void setDestroyListener(DestroyListener listener) {
        this.listener = listener;
    }
    private Location location;
    public Clay(Location location) {
        super(false, "sprites/clay.png", location);
        this.location = location;
    }

    @Override
    public void destroy() {
        // Notify the listener about the destruction
        if (listener != null) {
            listener.onDestroy(this);
        }

    }
}
