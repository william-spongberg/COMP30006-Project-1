package ore;

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

    public String[] getMap() {
        return map;
    }

    private final String[] map;

    public int getNumHorzCells() {
        return numHorzCells;
    }

    public int getNumVertCells() {
        return numVertCells;
    }

    private final int numHorzCells;
    private final int numVertCells;

    /**
     * Mapping from the string to a HashMap to prepare drawing
     *
     * @param model An enum specifying the map to generate
     */
    public MapGrid(int model) {
        // set map
        if (model == 0) {
            // model == 0
            map = map_0;
        } else {
            // model == 1
            map = map_1;
        }
        // set vertical and horizontal number of cells
        numHorzCells = map[0].length();
        numVertCells = map.length;
    }
}
