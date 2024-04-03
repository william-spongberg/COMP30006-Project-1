package ore;

import ch.aplu.jgamegrid.*;

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
    private int nbHorzCells = -1;
    private int nbVertCells = -1;
    private final OreSim.ElementType[][] mapElements; // = new OreSim.ElementType[nbHorzCells][nbVertCells];
    private int nbStones = 0;

    private final static String[] map_0 =
            {"    xxxxx          " + // 0 (19)
                    "    x...x          ", // 1
                    "    x*..x          ", // 2
                    "  xxx...xx         ",  // 3
                    "  x......x         ",  // 4
                    "xxx...RD.x   xxxxxx", // 5
                    "x.....RD.xxxxx....x", // 6
                    "x...*............ox",  // 7
                    "xxxxx.DDD.xPxx...ox",  // 8
                    "    x.....xxxxxxxxx",  // 9
                    "    xxxxxxx        "};  //10

    private final static String[] map_1 = {
            "xxxxxxxxxxxx", // 0  (14)
            "x..........x", // 0  (14)
            "x....RB....x", // 1
            "xo...R.*...x", // 2
            "xo...RDDDDDx", // 3
            "xP....ERRRRx", // 4
            "x....RRR*.xx", // 5
            "x..........x", // 6
            "xxxxxxxxxxxx"};  // 7

    private final static String[][] mapModel =
            {
                    map_0, map_1
            };


    public enum Model {MAP0, MAP1}

    private Model model;

    /**
     * Mapping from the string to a HashMap to prepare drawing
     *
     * @param model
     */
    public MapGrid(Model model) throws Exception {

        String[] map;
        switch (model) {
            case MAP0 -> {
                nbHorzCells = map_0[0].length();
                nbVertCells = map_0.length;
                map = map_0;
            }
            case MAP1 -> {
                nbHorzCells = map_1[0].length();
                nbVertCells = map_1.length;
                map = map_1;
            }
            default -> {
                throw new Exception("Invalid model");
            }
        }

        mapElements = new OreSim.ElementType[nbHorzCells][nbVertCells];

        // Copy structure into integer array
        for (int k = 0; k < nbVertCells; k++) {
            for (int i = 0; i < nbHorzCells; i++) {
                switch (map[k].charAt(i)) {
                    case ' ':
                        mapElements[i][k] = OreSim.ElementType.OUTSIDE;  // Empty outside
                        break;
                    case '.':
                        mapElements[i][k] = OreSim.ElementType.EMPTY;  // Empty inside
                        break;
                    case 'x':
                        mapElements[i][k] = OreSim.ElementType.BORDER;  // Border
                        break;
                    case '*':
                        mapElements[i][k] = OreSim.ElementType.ORE;  // Stones
                        nbStones++;
                        break;
                    case 'o':
                        mapElements[i][k] = OreSim.ElementType.TARGET;  // Target positions
                        break;
                    case 'P':
                        mapElements[i][k] = OreSim.ElementType.PUSHER;
                        break;
                    case 'B':
                        mapElements[i][k] = OreSim.ElementType.BULLDOZER;
                        break;
                    case 'E':
                        mapElements[i][k] = OreSim.ElementType.EXCAVATOR;
                        break;
                    case 'R':
                        mapElements[i][k] = OreSim.ElementType.ROCK; // Rocks
                        break;
                    case 'D':
                        mapElements[i][k] = OreSim.ElementType.CLAY; // Clay
                        break;
                }
            }
        }
    }

    public int getNbHorzCells() {
        return nbHorzCells;
    }

    public int getNbVertCells() {
        return nbVertCells;
    }

    public int getNbOres() {
        return nbStones;
    }

    public OreSim.ElementType getCell(Location location) {
        return mapElements[location.x][location.y];
    }
}
