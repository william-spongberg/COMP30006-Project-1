package ore.entity;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import ore.OreSim;

import java.awt.*;
import java.util.ArrayList;


/**
 *  Ore class for ore objects which are pushed by pusher.
 *  instances can switch to the sprite of an ore cart when they are on a target
 *  the main function of this class, checkAndMove, checks if we can move to a point based on various conditions
 */
public class Ore extends Actor
{
    public Ore() {
        super("sprites/ore.png", 2);
    }

    // checks if we can move, if we can, then we check if we are going to / leaving a target, then move
    public boolean checkAndMove(Location location) {
        // see whats at the location we're moving to.
        ArrayList<Actor> onLocation = gameGrid.getActorsAt(location);

        // check if we're gonna hit a wall.
        Color c = gameGrid.getBg().getColor(location);
        if (c.equals(OreSim.BORDER_COLOUR)) {
            return false;
        }

        // check if theres too much at a spot (target and ore) or theres something else
        // blocking us
        if (onLocation.size() > 1 || (onLocation.size() == 1 && !(onLocation.get(0) instanceof Target))) {
            return false;
        }

        // if we have reached this point, there is either a target or empty space.
        if (onLocation.size() == 1 && onLocation.get(0) instanceof Target) {
            // get the target we're going to
            Target targetTo = (Target) gameGrid.getOneActorAt(location, Target.class);

            // update us (set us to an oreCart)
            this.setLocation(location);
            this.show(1);

            // if we were already on the target, show the target we were at
            ArrayList<Actor> actorsOnLocation = gameGrid.getActorsAt(this.getLocation());
            for (Actor actor : actorsOnLocation) {
                if (actor instanceof Target) {
                    Target targetAt = (Target) actor;
                    targetAt.show();
                }
            }
            // hide the target we're going to
            targetTo.hide();
            return true;

        }
        // must be an empty space, so move.
        this.setLocation(location);
        this.show(0);
        return true;

    }
}
