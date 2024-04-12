package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import java.util.ArrayList;

public class Ore extends Actor
{
    public Ore() {
        super("sprites/ore.png", 2);
    }

    // this function checks if the provided location that we are about to move to is a target,
    // and whether an ore is at that spot.
    public boolean checkOnTarget(Location location) {
        // check if there is anything at the location except a Target
        ArrayList<Actor> onLocation = gameGrid.getActorsAt(location);
        for (Actor actor : onLocation) {
            if (!(actor instanceof Target)) {
                return false;
            }
        }
        // switch sprite to OreCart
        int spriteChange = 1;
        switchSprite(1);
        return true;
    }

    // check if the target we're moving to is not a target, and if the one we're currently on is.
    // this assumes we've already checked a move is indeed possible.
    public boolean movingFromTarget(Location locationTo) {
        // check if we're on a target in the first place
        Target targetAt = (Target) gameGrid.getOneActorAt(this.getLocation(), Target.class);
        Target targetTo = (Target) gameGrid.getOneActorAt(locationTo, Target.class);
        // we're moving to another target, so return false
        if (targetTo != null) {
            return false;
        }
        // we are on a target
        if (targetAt != null) {
            // we are on a target, and next place isn't a target, so we can switch to Ore
            switchSprite(0);
            return true;
        }
        return true;

    }

    public void switchSprite(int spriteChange) {
        this.show(spriteChange);
    }
}
