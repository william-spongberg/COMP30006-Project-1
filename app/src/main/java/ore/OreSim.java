package ore;

import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Properties;

/**
 * The `OreSim` class represents a simulation of an ore mining game. It extends the `GameGrid` class and implements the `GGKeyListener` interface.
 * The class contains inner classes representing different types of game elements such as `Target`, `Ore`, `Pusher`, `Bulldozer`, `Excavator`, `Rock`, and `Clay`.
 * The `OreSim` class is responsible for setting up the game grid, initializing game elements, handling user input, and running the game simulation.
 * It also provides methods for checking the progress of the game, updating statistics, and drawing the game board.
 */
public class OreSim extends GameGrid
{
  // ------------- Inner classes -------------
  public enum ElementType{
    OUTSIDE("OS"), EMPTY("ET"), BORDER("BD"),
    PUSHER("P"), BULLDOZER("B"), EXCAVATOR("E"), ORE("O"),
    ROCK("R"), CLAY("C"), TARGET("T");
    private String shortType;

    ElementType(String shortType) {
      this.shortType = shortType;
    }

    public String getShortType() {
      return shortType;
    }

    public static ElementType getElementByShortType(String shortType) {
      ElementType[] types = ElementType.values();
      for (ElementType type: types) {
        if (type.getShortType().equals(shortType)) {
          return type;
        }
      }

      return ElementType.EMPTY;
    }
  }

  // ------------- End of inner classes ------
  //
  private MapGrid grid;
  private int nbHorzCells;
  private int nbVertCells;
  private final Color borderColor = new Color(100, 100, 100);
  private Ore[] ores;
  private Target[] targets;
  private Pusher pusher;
  private Bulldozer bulldozer;
  private Excavator excavator;
  private boolean isFinished = false;
  private Properties properties;
  private boolean isAutoMode;
  private double gameDuration;
  private List<String> controls;
  private int movementIndex;
  private StringBuilder logResult = new StringBuilder();
  public OreSim(Properties properties, MapGrid grid)
  {
    super(grid.getNumHorzCells(), grid.getNumVertCells(), 30, false);
    this.grid = grid;
    nbHorzCells = grid.getNbHorzCells();
    nbVertCells = grid.getNbVertCells();
    this.properties = properties;

    isAutoMode = properties.getProperty("movement.mode").equals("auto");
    gameDuration = Integer.parseInt(properties.getProperty("duration"));
    setSimulationPeriod(Integer.parseInt(properties.getProperty("simulationPeriod")));
    controls = Arrays.asList(properties.getProperty("machines.movements").split(","));
  }

  /**
   * The main method to run the game
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

    double ONE_SECOND = 1000.0;
    while(!grid.completed() && gameDuration >= 0) {
      try {
        Thread.sleep(simulationPeriod);
        double minusDuration = (simulationPeriod / ONE_SECOND);
        gameDuration -= minusDuration;
        String title = String.format("# Ores at Target: %d. Time left: %.2f seconds", grid.getOresDone(), gameDuration);
        setTitle(title);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    doPause();

    if (grid.completed()) {
      setTitle("Mission Complete. Well done!");
    } else if (gameDuration < 0) {
      setTitle("Mission Failed. You ran out of time");
    }

    updateStatistics();
    isFinished = true;
    return logResult.toString();
  }

  /**
   * Transform the list of actors to a string of location for a specific kind of actor.
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
   * Students need to modify this method so it can write an actual statistics into the statistics file. It currently
   *  only writes the sample data.
   */
  private void updateStatistics() {
    File statisticsFile = new File("statistics.txt");
    FileWriter fileWriter = null;
    try {
      fileWriter = new FileWriter(statisticsFile);
      fileWriter.write("Pusher-1 Moves: 10\n");
      fileWriter.write("Excavator-1 Moves: 5\n");
      fileWriter.write("Excavator-1 Rock removed: 3\n");
      fileWriter.write("Bulldozer-1 Moves: 2\n");
      fileWriter.write("Bulldozer-1 Clay removed: 1\n");
    } catch (IOException e) {
      System.out.println("Cannot write to file - e: " + e.getLocalizedMessage());
    } finally {
      try {
        fileWriter.close();
      } catch (IOException e) {
        System.out.println("Cannot close file - e: " + e.getLocalizedMessage());
      }
    }
  }

  /**
   * Draw all different actors on the board: pusher, ore, target, rock, clay, bulldozer, excavator
   */
  private void drawActors()
  {
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        Location location = new Location(x, y);
        MapObject mapObject = grid.get(location);
        if (mapObject instanceof Actor)
        {
          addActor(mapObject, location);
        }
      }
    }
    System.out.println("ores = " + Arrays.asList(ores));
    setPaintOrder(Target.class);
  }

  /**
   * Draw the basic board with outside color and border color
   * @param bg
   */

  private void drawBoard(GGBackground bg)
  {
    bg.clear(new Color(230, 230, 230));
    bg.setPaintColor(Color.darkGray);
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        Location location = new Location(x, y);
        MapObject mapObject = grid.get(location);
        if (mapObject != ElementType.OUTSIDE)
        {
          bg.fillCell(location, Color.lightGray);
        }
        if (mapObject == ElementType.BORDER)  // Border
          bg.fillCell(location, borderColor);
      }
    }
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

}
