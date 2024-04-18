package ore.map;

import java.util.ArrayList;

import ore.OreSim;


/**
 * Represents a grid-based map for an ore simulation.
 * The map is defined by a 2D array of ElementTypes.
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
            "    xxxxxxx        "};// 10
    private final static String[] map_1 = {
            "xxxxxxxxxxxx", // 0 (14)
            "x..........x", // 1
            "x....RB....x", // 2
            "xo...R.*...x", // 3
            "xo...RDDDDDx", // 4
            "xP....ERRRRx", // 5
            "x....RRR*.xx", // 6
            "x..........x", // 7
            "xxxxxxxxxxxx"};// 8

    public ArrayList<ArrayList<OreSim.ElementType>> getMap() {
        return map;
    }

    private final ArrayList<ArrayList<OreSim.ElementType>> map;

    public int getNumHorzCells() {
        return numHorzCells;
    }

    public int getNumVertCells() {
        return numVertCells;
    }

    private final int numHorzCells;
    private final int numVertCells;

    /**
     * Creates a MapGrid object based on a specified map.
     *
     * @param model An int specifying the map to generate
     */
    public MapGrid(int model) {
        // set map
        String[] map_str;
        if (model == 0) {
            // model == 0
            map_str = map_0;
        } else {
            // model == 1
            map_str = map_1;
        }
        // set vertical and horizontal number of cells
        numHorzCells = map_str[0].length();
        numVertCells = map_str.length;

        // generate 2d array of ElementTypes
        ArrayList<ArrayList<OreSim.ElementType>> _map = new ArrayList<>();
        for (int y = 0; y < numVertCells; y++) {
            _map.add(new ArrayList<>());
            for (int x = 0; x < numHorzCells; x++) {
                switch (map_str[y].charAt(x)) {
                    case 'P' -> _map.get(y).add(OreSim.ElementType.PUSHER);
                    case 'B' -> _map.get(y).add(OreSim.ElementType.BULLDOZER);
                    case 'E' -> _map.get(y).add(OreSim.ElementType.EXCAVATOR);
                    case 'R' -> _map.get(y).add(OreSim.ElementType.ROCK);
                    case 'D' -> _map.get(y).add(OreSim.ElementType.CLAY);
                    case '*' -> _map.get(y).add(OreSim.ElementType.ORE);
                    case 'o' -> _map.get(y).add(OreSim.ElementType.TARGET);
                    case ' ' -> _map.get(y).add(OreSim.ElementType.OUTSIDE);
                    case '.' -> _map.get(y).add(OreSim.ElementType.EMPTY);
                    case 'x' -> _map.get(y).add(OreSim.ElementType.BORDER);
                }
            }
        }
        map = _map;
    }
}
