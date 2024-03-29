package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.List;

public class Pusher extends Vehicle
{
    private List<String> controls = null;
    private int autoMovementIndex = 0;
    public Pusher()
    {
        super(true, "sprites/pusher.png");  // Rotatable
    }
    public void setupPusher(boolean isAutoMode, List<String> controls) {
        this.controls = controls;
    }

    /**
     * Method to move pusher automatically based on the instructions input from properties file
     */
    public void autoMoveNext() {
        if (controls != null && autoMovementIndex < controls.size()) {
            String[] currentMove = controls.get(autoMovementIndex).split("-");
            String machine = currentMove[0];
            String move = currentMove[1];
            autoMovementIndex++;
            if (machine.equals("P")) {
                if (isFinished)
                    return;

                Location next = null;
                switch (move)
                {
                    case "L":
                        next = getLocation().getNeighbourLocation(Location.WEST);
                        setDirection(Location.WEST);
                        break;
                    case "U":
                        next = getLocation().getNeighbourLocation(Location.NORTH);
                        setDirection(Location.NORTH);
                        break;
                    case "R":
                        next = getLocation().getNeighbourLocation(Location.EAST);
                        setDirection(Location.EAST);
                        break;
                    case "D":
                        next = getLocation().getNeighbourLocation(Location.SOUTH);
                        setDirection(Location.SOUTH);
                        break;
                }

                Target curTarget = (Target) getOneActorAt(getLocation(), Target.class);
                if (curTarget != null){
                    curTarget.show();
                }
                if (next != null && canMove(next))
                {
                    setLocation(next);
                }
                refresh();
            }
        }
    }
}
