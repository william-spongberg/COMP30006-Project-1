package ore;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.GGKeyListener;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Collections;

public class OreSim extends GameGrid implements GGKeyListener {
    public static final Color BORDER_COLOUR = new Color(100, 100, 100);
    public static final Color FLOOR_COLOUR = Color.lightGray;
    public static final Color OUTSIDE_COLOUR = Color.darkGray;

    private final MapGrid grid;
    private final int numHorzCells;
    private final int numVertCells;
    private final Properties properties;

    // TODO: set to private, create getters and setters
    public final boolean isAutoMode;
    public List<String> autoMovements = new ArrayList<String>();

    private double gameDuration;
    private int movementIndex;
    private static final double ONE_SECOND = 1000.0;
    private final StringBuilder logResult = new StringBuilder();

    public OreSim(Properties properties, MapGrid grid) {
        super(grid.getNumHorzCells(), grid.getNumVertCells(), 30, false);
        this.grid = grid;
        numHorzCells = grid.getNumHorzCells();
        numVertCells = grid.getNumVertCells();
        this.properties = properties;
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
     * @param isDisplayingUI
     * @return
     */
    public String runApp(boolean isDisplayingUI) {
        GGBackground bg = getBg();
        drawBoard(bg);
        drawActors();

        if (isDisplayingUI) {
            show();
        }
        ArrayList<Actor> vehicles;
        while (!completed() && gameDuration >= 0) {
            try {
                // update actors
                vehicles = getActors(Vehicle.class);
                for (Actor vehicle : vehicles) {
                    ((Vehicle) vehicle).moveVehicle();
                    addKeyListener(((Vehicle) vehicle).getController());
                }
                refresh();
                updateLogResult();

                // handle duration
                Thread.sleep(simulationPeriod);
                double minusDuration = (simulationPeriod / ONE_SECOND);
                gameDuration -= minusDuration;

                // Set title
                String title = String.format("# Ores at Target: %d. Time left: %.2f seconds", getOresDone(),
                        gameDuration);
                setTitle(title);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        doPause();

        if (completed()) {
            setTitle("Mission Complete. Well done!");
        } else if (gameDuration < 0) {
            setTitle("Mission Failed. You ran out of time");
        }

        updateStatistics();
        return logResult.toString();
    }

    /**
     * Transform the list of actors to a string of location for a specific kind of
     * actor.
     *
     * @param actors
     * @return
     */
    private String actorLocations(List<Actor> actors) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean hasAddedColon = false;
        boolean hasAddedLastComma = false;
        for (int i = 0; i < actors.size(); i++) {
            Actor actor = actors.get(i);
            if (actor.isVisible()) {
                if (!hasAddedColon) {
                    stringBuilder.append(":");
                    hasAddedColon = true;
                }
                stringBuilder.append(actor.getX() + "-" + actor.getY());
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
     * Students need to modify this method so it can write an actual statistics into
     * the statistics file. It currently
     * only writes the sample data.
     */
    private void updateStatistics() {
        File statisticsFile = new File("statistics.txt");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(statisticsFile);
            List<Actor> pushers = getActors(Pusher.class);
            List<Actor> excavators = getActors(Excavator.class);
            List<Actor> bulldozers = getActors(Bulldozer.class);

            for (Actor pusher : pushers) {
                String[] statistics = ((Pusher) pusher).getStatistics();
                for (String statistic : statistics) {
                    fileWriter.write(statistic + "\n");
                }
            }

            for (Actor excavator : excavators) {
                String[] statistics = ((Excavator) excavator).getStatistics();
                for (String statistic : statistics) {
                    fileWriter.write(statistic + "\n");
                }
            }

            for (Actor bulldozer : bulldozers) {
                String[] statistics = ((Bulldozer) bulldozer).getStatistics();
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
     * Draw all different actors on the board: pusher, ore, target, rock, clay,
     * bulldozer, excavator
     */
    private void drawActors() {
        ArrayList<ArrayList<ElementType>> map = grid.getMap();
        for (int y = 0; y < grid.getNumVertCells(); y++) {
            for (int x = 0; x < grid.getNumHorzCells(); x++) {
                switch (map.get(y).get(x)) {
                    case PUSHER:
                        addActor(new Pusher(isAutoMode, autoMovements, 0), new Location(x, y));
                        break;
                    case BULLDOZER:
                        addActor(new Bulldozer(isAutoMode, autoMovements, 0), new Location(x, y));
                        break;
                    case EXCAVATOR:
                        addActor(new Excavator(isAutoMode, autoMovements, 0), new Location(x, y));
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
        setPaintOrder(Target.class);
    }

    /**
     * Draw the basic board with outside color and border color
     *
     * @param bg
     */

    private void drawBoard(GGBackground bg) {
        // TODO: work out what tf going on here
        // the following for loop should paint cells the correct colour, so unsure what
        // the next 2 lines do :P
        bg.clear(new Color(230, 230, 230));
        bg.setPaintColor(Color.darkGray);

        ArrayList<ArrayList<ElementType>> map = grid.getMap();
        for (int y = 0; y < grid.getNumVertCells(); y++) {
            for (int x = 0; x < grid.getNumHorzCells(); x++) {
                switch (map.get(y).get(x)) {
                    case OUTSIDE:
                        bg.fillCell(new Location(x, y), OUTSIDE_COLOUR);
                        break;
                    case BORDER:
                        bg.fillCell(new Location(x, y), BORDER_COLOUR);
                        break;
                    default:
                        bg.fillCell(new Location(x, y), FLOOR_COLOUR);
                }
            }
        }
    }

    private int getOresDone() {
        int counter = 0;
        for (Actor ore : getActors(Ore.class)) {
            if (getActorsAt(ore.getLocation(), Target.class).size() > 0) {
                counter++;
            }
        }
        return counter;
    }

    private boolean completed() {
        // try to find an ore not at a target
        for (Actor ore : getActors(Ore.class)) {
            if (getActorsAt(ore.getLocation(), Target.class).size() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * The method will generate a log result for all the movements of all actors
     * The log result will be tested against our expected output.
     * Your code will need to pass all the 3 test suites with 9 test cases.
     */
    private void updateLogResult() {
        movementIndex++;
        List<Actor> pushers = getActors(Pusher.class);
        List<Actor> ores = getActors(Ore.class);
        List<Actor> targets = getActors(Target.class);
        List<Actor> rocks = getActors(Clay.class);
        List<Actor> clays = getActors(Clay.class);
        List<Actor> bulldozers = getActors(Bulldozer.class);
        List<Actor> excavators = getActors(Excavator.class);

        logResult.append(movementIndex + "#");
        logResult.append(ElementType.PUSHER.getShortType()).append(actorLocations(pushers)).append("#");
        logResult.append(ElementType.ORE.getShortType()).append(actorLocations(ores)).append("#");
        logResult.append(ElementType.TARGET.getShortType()).append(actorLocations(targets)).append("#");
        logResult.append(ElementType.ROCK.getShortType()).append(actorLocations(rocks)).append("#");
        logResult.append(ElementType.CLAY.getShortType()).append(actorLocations(clays)).append("#");
        logResult.append(ElementType.BULLDOZER.getShortType()).append(actorLocations(bulldozers)).append("#");
        logResult.append(ElementType.EXCAVATOR.getShortType()).append(actorLocations(excavators));

        logResult.append("\n");
    }

    public boolean keyPressed(KeyEvent evt) {
        return false;
    }

    public boolean keyReleased(KeyEvent evt) {
        return false;
    }

    // ------------- Inner classes -------------
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
}
