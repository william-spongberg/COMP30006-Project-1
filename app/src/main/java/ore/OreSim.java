package ore;

import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class OreSim extends GameGrid implements GGKeyListener {

    // colour of walls in the simulation that Pushers and Ores cannot move past
    public static final Color BORDER_COLOUR = new Color(100, 100, 100);

    // the ground colour which we can move on in the simulation
    public static final Color FLOOR_COLOUR = Color.lightGray;

    // the colour outside the defined borders
    public static final Color OUTSIDE_COLOUR = Color.darkGray;

    // the simulation period in milliseconds
    public static final double ONE_SECOND = 1000.0;

    private final boolean isAutoMode;
    private final List<String> autoMovements = new ArrayList<>();
    private final MapGrid grid;
    private final StringBuilder logResult = new StringBuilder();
    private double gameDuration;
    private int movementIndex;

    public enum ElementType {
        OUTSIDE("OS"), EMPTY("ET"), BORDER("BD"),
        PUSHER("P"), BULLDOZER("B"), EXCAVATOR("E"), ORE("O"),
        ROCK("R"), CLAY("C"), TARGET("T");

        private final String shortType;

        ElementType(String shortType) {
            this.shortType = shortType;
        }

        public static ElementType getElementByShortType(String shortType) {
            ElementType[] types = ElementType.values();
            for (ElementType type : types) {
                if (type.getShortType().equals(shortType)) {
                    return type;
                }
            }

            return ElementType.EMPTY;
        }

        public String getShortType() {
            return shortType;
        }
    }

    public OreSim(Properties properties, MapGrid grid) {
        super(grid.getNumHorzCells(), grid.getNumVertCells(), 30, false);
        this.grid = grid;
        this.isAutoMode = properties.getProperty("movement.mode").equals("auto");
        if (isAutoMode) {
            Collections.addAll(autoMovements, properties.getProperty("machines.movements").split(","));
        }
        gameDuration = Integer.parseInt(properties.getProperty("duration"));
        setSimulationPeriod(Integer.parseInt(properties.getProperty("simulationPeriod")));

        System.out.println("\nisAutoMode = " + isAutoMode);
        System.out.println("autoMovements = " + autoMovements);
        System.out.println("gameDuration = " + gameDuration);

        addKeyListener(this);
    }

    /**
     * The main method to run the game
     *
     * @param isDisplayingUI displays the simulation UI if true, hides simulation UI
     *                       if false
     * @return A string of the logged result
     */
    public String runApp(boolean isDisplayingUI) {
        // draw the board + UI
        GGBackground bg = getBg();
        drawBoard(bg);
        drawActors();
        if (isDisplayingUI) {
            show();
        }

        // run game
        while (!winCondition(getActors(Ore.class), getActors(Target.class)) && gameDuration >= 0) {
            try {
                // update actors
                for (Actor vehicle : getActors(Vehicle.class)) {
                    ((Vehicle) vehicle).moveVehicle();
                }

                // update screen
                refresh();

                // update results
                updateLogResult();

                // handle duration
                Thread.sleep(simulationPeriod);
                double minusDuration = (simulationPeriod / ONE_SECOND);
                gameDuration -= minusDuration;

                // display title
                String title = String.format("# Ores at Target: %d. Time left: %.2f seconds",
                        getNumOresDone(getActors(Ore.class), getActors(Target.class)),
                        gameDuration);
                setTitle(title);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // pause the game
        doPause();

        // display win/lose message
        if (winCondition(getActors(Ore.class), getActors(Target.class))) {
            setTitle("Mission Complete. Well done!");
        } else if (gameDuration < 0) {
            setTitle("Mission Failed. You ran out of time");
        }

        // write statistics to file and return the result
        updateStatistics();
        return logResult.toString();
    }

    /**
     * Draws the actors on the grid based on the elements in the map.
     * Each element in the map corresponds to a specific actor type.
     * The method iterates through the map and adds the corresponding actor to the
     * grid.
     */
    private void drawActors() {
        ArrayList<ArrayList<ElementType>> map = grid.getMap();
        int pusherId, bulldozerId, excavatorId;
        pusherId = bulldozerId = excavatorId = 0;

        for (int y = 0; y < grid.getNumVertCells(); y++) {
            for (int x = 0; x < grid.getNumHorzCells(); x++) {
                switch (map.get(y).get(x)) {
                    case PUSHER:
                        addVehicle(new Pusher(++pusherId, isAutoMode, autoMovements, 0), new Location(x, y));
                        break;
                    case BULLDOZER:
                        addVehicle(new Bulldozer(++bulldozerId, isAutoMode, autoMovements, 0), new Location(x, y));
                        break;
                    case EXCAVATOR:
                        addVehicle(new Excavator(++excavatorId, isAutoMode, autoMovements, 0), new Location(x, y));
                        break;
                    case ORE:
                        addActor(new Ore(), new Location(x, y));
                        break;
                    case ROCK:
                        addActor(new Rock(), new Location(x, y));
                        break;
                    case CLAY:
                        addActor(new Clay(), new Location(x, y));
                        break;
                    case TARGET:
                        addActor(new Target(), new Location(x, y));
                    default:
                        break;
                }
            }
        }
    }

    /**
     * Draws the game board on the given GGBackground.
     *
     * @param bg The GGBackground on which to draw the game board.
     */
    private void drawBoard(GGBackground bg) {
        ArrayList<ArrayList<ElementType>> map = grid.getMap();
        for (int y = 0; y < grid.getNumVertCells(); y++) {
            for (int x = 0; x < grid.getNumHorzCells(); x++) {
                switch (map.get(y).get(x)) {
                    case OUTSIDE -> bg.fillCell(new Location(x, y), OUTSIDE_COLOUR);
                    case BORDER -> bg.fillCell(new Location(x, y), BORDER_COLOUR);
                    default -> bg.fillCell(new Location(x, y), FLOOR_COLOUR);
                }
            }
        }
    }

    /**
     * Adds a vehicle to the simulation at the specified location.
     * Also adds a key listener to the vehicle if it is not automatic.
     *
     * @param vehicle  the vehicle to be added
     * @param location the location where the vehicle should be added
     */
    private void addVehicle(Vehicle vehicle, Location location) {
        addActor(vehicle, location);
        if (!vehicle.getIsAuto()) {
            addKeyListener(vehicle.getController());
        }
    }

    /**
     * Returns the number of ores that have been pushed to a target.
     *
     * @param ores    the list of ores to check
     * @param targets the list of targets to compare with
     * @return the number of ores that have been pushed to a target.
     */
    private int getNumOresDone(ArrayList<Actor> ores, ArrayList<Actor> targets) {
        int counter = 0;
        for (Actor ore : ores) {
            for (Actor target : targets) {
                if (ore.getLocation().equals(target.getLocation())) {
                    counter++;
                    break;
                }
            }
        }
        return counter;
    }

    /**
     * Checks if the win condition is met.
     * The win condition is met when the number of completed ores is equal to the
     * total number of ores.
     *
     * @param ores    the list of ores
     * @param targets the list of targets
     * @return true if the win condition is met, false otherwise
     */
    private boolean winCondition(ArrayList<Actor> ores, ArrayList<Actor> targets) {
        return getNumOresDone(ores, targets) == ores.size();
    }

    /**
     * Updates the log result by appending the current state of the simulation to
     * the log result.
     */
    private void updateLogResult() {
        List<Actor> pushers = getActors(Pusher.class);
        List<Actor> bulldozers = getActors(Bulldozer.class);
        List<Actor> excavators = getActors(Excavator.class);
        List<Actor> ores = getActors(Ore.class);
        List<Actor> targets = getActors(Target.class);
        List<Actor> rocks = getActors(Rock.class);
        List<Actor> clays = getActors(Clay.class);

        movementIndex++;
        logResult.append(movementIndex).append("#");
        logResult.append(ElementType.PUSHER.getShortType()).append(actorLocations(pushers)).append("#");
        logResult.append(ElementType.BULLDOZER.getShortType()).append(actorLocations(bulldozers)).append("#");
        logResult.append(ElementType.EXCAVATOR.getShortType()).append(actorLocations(excavators)).append("#");
        logResult.append(ElementType.ORE.getShortType()).append(actorLocations(ores)).append("#");
        logResult.append(ElementType.TARGET.getShortType()).append(actorLocations(targets)).append("#");
        logResult.append(ElementType.ROCK.getShortType()).append(actorLocations(rocks)).append("#");
        logResult.append(ElementType.CLAY.getShortType()).append(actorLocations(clays)).append("#");

        logResult.append("\n");
    }

    /**
     * Transform the list of actors to a string of location for a specific kind of
     * actor.
     *
     * @param actors A list of Actors
     * @return A string representing the location of each given actor
     */
    private String actorLocations(List<Actor> actors) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean hasAddedColon = false;
        boolean hasAddedLastComma = false;
        for (Actor actor : actors) {
            if (actor.isVisible()) {
                if (!hasAddedColon) {
                    stringBuilder.append(":");
                    hasAddedColon = true;
                }
                stringBuilder.append(actor.getX()).append("-").append(actor.getY());
                stringBuilder.append(",");
                hasAddedLastComma = true;
            }
        }

        if (hasAddedLastComma) {
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
        }

        return stringBuilder.toString();
    }

    /**
     * Updates the statistics by writing them to a file named "statistics.txt".
     * The statistics include information from all the pushers, excavators, and
     * bulldozers in the simulation.
     * If an error occurs while writing to the file or closing it, an error message
     * is printed to the console.
     */
    private void updateStatistics() {
        File statisticsFile = new File("statistics.txt");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(statisticsFile);
            List<Actor> vehicles = new ArrayList<>();
            vehicles.addAll(getActors(Pusher.class));
            vehicles.addAll(getActors(Excavator.class));
            vehicles.addAll(getActors(Bulldozer.class));

            for (Actor vehicle : vehicles) {
                String[] statistics = ((Vehicle) vehicle).getStatistics();
                for (String statistic : statistics) {
                    fileWriter.write(statistic + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Cannot write to file - e: " + e.getLocalizedMessage());
        } finally {
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                System.out.println("Cannot close file - e: " + e.getLocalizedMessage());
            }

        }
    }

    /**
     * Handles the key pressed event for GameGrid.
     *
     * @param evt the KeyEvent object representing the key press event
     * @return false due to no key press handling in this class
     */
    public boolean keyPressed(KeyEvent evt) {
        return false;
    }

    /**
     * Handles the key released event for GameGrid.
     *
     * @param evt the KeyEvent object representing the key press event
     * @return false due to no key release handling in this class
     */
    public boolean keyReleased(KeyEvent evt) {
        return false;
    }
}
