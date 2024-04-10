package ore;

import java.util.List;
import java.util.Map;

import ch.aplu.jgamegrid.*;

public class Pusher extends Vehicle {
    // only vehicle that can be manually controlled

    public Pusher() {
        super(true, "sprites/pusher.png");
    }

    public boolean updateObject(MapObject object) {
        if (object instanceof Ore) {
            Ore ore = (Ore) object;
            // Try to move the ore
            ore.setDirection(this.getDirection());
            return moveOre(ore);
        }
        return false;
    }

    /**
     * When the pusher pushes the ore in 1 direction, this method will be called to
     * check if the ore can move in that direction
     * and if it can move, then it changes the location
     * 
     * @param ore
     * @return
     */
    private boolean moveOre(Ore ore) {

        Location next = ore.getNextMoveLocation();

        MapGrid map;
        // Test if try to move into border
        Color c = getBg().getColor(next);
        Rock rock = (Rock) map.getOneActorAt(next, Rock.class);
        Clay clay = (Clay) getOneActorAt(next, Clay.class);
        if (c.equals(borderColor) || rock != null || clay != null)
            return false;

        // Test if there is another ore
        Ore neighbourOre = (Ore) getOneActorAt(next, Ore.class);
        if (neighbourOre != null)
            return false;

        // Reset the target if the ore is moved out of target
        Location currentLocation = ore.getLocation();
        List<Actor> actors = getActorsAt(currentLocation);
        if (actors != null) {
            for (Actor actor : actors) {
                if (actor instanceof Target) {
                    Target currentTarget = (Target) actor;
                    currentTarget.show();
                    ore.show(0);
                }
            }
        }

        // Move the ore
        ore.setLocation(next);

        // Check if we are at a target
        Target nextTarget = (Target) getOneActorAt(next, Target.class);
        if (nextTarget != null) {
            ore.show(1);
            nextTarget.hide();
        } else {
            ore.show(0);
        }

        return true;
    }
}
