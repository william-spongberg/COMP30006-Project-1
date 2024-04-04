package ore;

import ch.aplu.jgamegrid.Location;

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
    private final static String[] map_0 =
            {"    xxxxx          ", // 0 (19)
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
    private HashMap<Location, MapObject> map = new HashMap<>();
    private final int numHorzCells;
    private final int numVertCells;

    /**
     * Mapping from the string to a HashMap to prepare drawing
     *
     * @param model An enum specifying the map to generate
     */
    public MapGrid(Model model) throws Exception {
        String[] map_str;
        switch (model) {
            case MAP0 -> {
                numHorzCells = map_0[0].length();
                numVertCells = map_0.length;
                map_str = map_0;
            }
            case MAP1 -> {
                numHorzCells = map_1[0].length();
                numVertCells = map_1.length;
                map_str = map_1;
            }
            default -> {
                throw new Exception("Invalid model");
            }
        }

        // Copy structure into HashMap
        for (int y = 0; y < numVertCells; y++) {
            for (int x = 0; x < numHorzCells; x++) {
                switch (map_str[y].charAt(x)) {
                    case ' ': // outside
                        // TODO: necessary?
                        break;
                    case '.': // Empty
                        // TODO: handle empties
                        break;
                    case 'x': // Border
                        // TODO: handle border
                        break;
                    case '*': // Stone (did they mean ore???)
                        map.put(new Location(x, y), new Ore());
                        break;
                    case 'o': // Target
                        map.put(new Location(x, y), new Target());
                        break;
                    case 'P': // Pusher
                        map.put(new Location(x, y), new Pusher());
                        break;
                    case 'B': // Bulldozer
                        map.put(new Location(x, y), new Bulldozer());
                        break;
                    case 'E': // Excavator
                        map.put(new Location(x, y), new Excavator());
                        break;
                    case 'R': // Rock
                        map.put(new Location(x, y), new Rock());
                        break;
                    case 'D': // Clay
                        map.put(new Location(x, y), new Clay());
                        break;
                }
            }
        }
    }

    public int getNbHorzCells() {
        return numHorzCells;
    }

    public int getNbVertCells() {
        return numVertCells;
    }

    public enum Model {MAP0, MAP1}
}
