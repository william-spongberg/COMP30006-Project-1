package ore;

import java.util.Properties;

import ore.map.MapGrid;

public class Driver {
    // properties/settings for game
    public static final String DEFAULT_PROPERTIES_PATH = "properties/game1.properties";


    // main process runs the game
    public static void main(String[] args) throws Exception {
        String propertiesPath = DEFAULT_PROPERTIES_PATH;
        // if more than one property, set to first property
        if (args.length > 0) {
            propertiesPath = args[0];
        }
        // load properties
        final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);
        // feed into MapGrid
        int model = Integer.parseInt(properties.getProperty("map"));
        MapGrid grid = new MapGrid(model);
        // feed MapGrid into Oresim
        String logResult = new OreSim(properties, grid).runApp(true);
        // print result
        System.out.println("logResult = " + logResult);
    }
}