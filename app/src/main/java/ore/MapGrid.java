package ore;

import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.Actor;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The MapGrid class represents a grid-based map for an ore simulation game.
 * It stores information about the elements present in each cell of the grid,
 * such as borders, empty spaces, stones, targets, pushers, bulldozers, excavators,
 * rocks, and clay. The class provides methods to retrieve information about the
 * dimensions of the grid, the number of stones, and the type of element in a specific cell.
 * <p>
 * The map grid is initialized based on a predefined model, which consists of a string
 * representation of the map and the dimensions of the grid for each model. The map
 * elements are mapped from the string representation to the corresponding element types.
 * <p>
 * //@param model The index of the model to use for initializing the map grid.
 */
public class MapGrid {
    private final static String[] map_0 = {
            "    xxxxx          ", // 0 (19)
            "    x...x          ", // 1
            "    x*..x          ", // 2
            "  xxx...xx         ", // 3
            "  x......x         ", // 4
            "xxx...RD.x   xxxxxx", // 5
            "x.....RD.xxxxx....x", // 6
            "x...*............ox", // 7
            "xxxxx.DDD.xPxx...ox", // 8
            "    x.....xxxxxxxxx", // 9
            "    xxxxxxx        "};//10
    private final static String[] map_1 = {
            "xxxxxxxxxxxx", // 0  (14)
            "x..........x", // 1
            "x....RB....x", // 2
            "xo...R.*...x", // 3
            "xo...RDDDDDx", // 4
            "xP....ERRRRx", // 5
            "x....RRR*.xx", // 6
            "x..........x", // 7
            "xxxxxxxxxxxx"};// 8

    public int getNumHorzCells() {
        return numHorzCells;
    }

    public int getNumVertCells() {
        return numVertCells;
    }
    public ArrayList<Actor> get(Location location) {
        return map.get(location);
    }

    private final int numHorzCells;
    private final int numVertCells;
    private final HashMap<Location, ArrayList<Actor>> map = new HashMap<>();

    /**
     * Mapping from the string to a HashMap to prepare drawing
     *
     * @param model An enum specifying the map to generate
     */
    public MapGrid(int model) {
        String[] map_str;
        if (model == 0) {
            // model == 0
            numHorzCells = map_0[0].length();
            numVertCells = map_0.length;
            map_str = map_0;
        } else {
            // model == 1
            numHorzCells = map_1[0].length();
            numVertCells = map_1.length;
            map_str = map_1;
        }

        // Copy structure into HashMap
        Location location;
        for (int y = 0; y < numVertCells; y++) {
            for (int x = 0; x < numHorzCells; x++) {
                location = new Location(x, y);
                map.put(location, new ArrayList<Actor>());
                switch (map_str[y].charAt(x)) {
                    case '.': // Empty
                        map.get(location).add(new Floor());
                        break;
                    case 'x': // Border
                        map.get(location).add(new Border());
                        break;
                    case '*': // Ore
                        map.get(location).add(new Ore());
                        break;
                    case 'o': // Target
                        map.get(location).add(new Target());
                        break;
                    case 'P': // Pusher
                        map.get(location).add(new Pusher());
                        break;
                    case 'B': // Bulldozer
                        map.get(location).add(new Bulldozer());
                        break;
                    case 'E': // Excavator
                        map.get(location).add(new Excavator());
                        break;
                    case 'R': // Rock
                        map.get(location).add(new Rock());
                        break;
                    case 'D': // Clay
                        map.get(location).add(new Clay());
                        break;
                }
            }
        }
    }

    /**
     * gets the number of ores that have been moved to targets by checking for an ore and a target in each location
     * @return the number of ores in a target location
     */
    public int getOresDone()
    {
        int oresDone = 0;
        for (Location location: map.keySet()) {
            if (contains(location, OreSim.ElementType.ORE) && contains(location, OreSim.ElementType.TARGET))
            {
                oresDone++;
            }
        }
        return oresDone;
    }

    public boolean completed()
    {
        // count number of ores
        int numOres = 0;
        for (Location location: map.keySet()) {
            for (MapObject mapObject: map.get(location))
            {
                if (mapObject.getType() == OreSim.ElementType.ORE)
                {
                    numOres++;
                }
            }
        }
        return numOres == getOresDone();
    }

    public boolean contains(Location location, OreSim.ElementType type)
    {
        for (MapObject mapObject: map.get(location))
        {
            if (mapObject.getType() == type)
            {
                return true;
            }
        }
        return false;
    }
}
