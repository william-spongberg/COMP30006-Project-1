package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.awt.*;
import java.util.ArrayList;
import ch.aplu.jgamegrid.GGBackground;



public class Ore extends Actor
{
    private static final Color BORDER_COLOUR = new Color(100, 100, 100);
    public Ore() {
        super("sprites/ore.png", 2);
    }

    // checks if we can move, if we can, then we check if we are going to / leaving a target
    public boolean checkAndMove(Location location) {
        // see whats at the location we're moving to.
        ArrayList<Actor> onLocation = gameGrid.getActorsAt(location);

        // check if we're gonna hit a wall.
        Color c = gameGrid.getBg().getColor(location);
        if (c.equals(BORDER_COLOUR)) {
            return false;
        }

        // check if theres too much at a spot (target and ore) or theres something else blocking us
        if (onLocation.size() > 1 || (onLocation.size() == 1 && !(onLocation.get(0) instanceof Target))) {
            return false;
        }

        // if we have reached this point, there is either a target or empty space.
        Target targetAt = (Target) gameGrid.getOneActorAt(this.getLocation(), Target.class);

        if (onLocation.size() == 1 && onLocation.get(0) instanceof Target) {
            // get the target we're going to
            Target targetTo = (Target) gameGrid.getOneActorAt(location, Target.class);

            // update us (set us to an oreCart)
            this.setLocation(location);
            this.show(1);

            // hide the target we're going to
            targetTo.hide();

            // if we were already on the target, show the target we were at
            if (targetAt != null) {
                targetAt.show();
            }
            return true;

        }
        // must be an empty space, so move.
        this.setLocation(location);
        this.show(0);
        return true;

    }
}
