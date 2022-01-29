package cellsociety;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides an abstraction of a simulation instance and defines what characteristics all simulations should
 * have. Subclasses of SimulationType can specify desired algorithms for cell behavior and state.
 *
 * @author Edison Ooi
 */
public abstract class SimulationType {
    public static final int GAME_OF_LIFE = 0;
    public static final int PERCOLATION = 1;
    public static final int SCHELLING_SEGREGATION = 2;
    public static final int SPREADING_OF_FIRE = 3;
    public static final int WATOR = 4;

    private final int simulationType; //TODO: Change this to an enum of some kind

    // Information for setting up grid of cells
    private int numRows;
    private int numColumns;
    private int[][] initialCellConfig; //TODO: Change this to array of CellState

    // Attributions
    private String title;
    private String description;
    private String author;

    // Parameters specific to this simulation type, for example probCatch in Spreading of Fire simulations
    private Map<String, String> simulationParams;

    /**
     * Class constructor.
     *
     * @param data SimulationData record that contains the data obtained from parsing the configuration XML
     */
    public SimulationType(SimulationData data) {
        simulationType = data.simulationType();
        numRows = data.numRows();
        numColumns = data.numColumns();
        title = data.title();
        description = data.description();
        author = data.author();
        simulationParams = data.params();

        initializeCellConfig(data.startingConfig());
    }

    /**
     * Initialize specific parameters for subclasses from values in simulationParams.
     */
    protected abstract void setUpSimulationParameters();

    public abstract void runAlgorithm();

    // Initializes an array representing the starting state of every cell in the simulation
    private void initializeCellConfig(String config) {
        String[] configElements = config.split(" ");

        if(configElements.length != numColumns * numRows) {
            throw new RuntimeException("StartingConfig is not the correct size!");
        }

        initialCellConfig = new int[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                initialCellConfig[i][j] = Integer.parseInt(configElements[i + j]);
            }
        }
    }

    protected int getSimulationType() {
        return simulationType;
    }

    protected int getNumRows() {
        return numRows;
    }

    protected int getNumColumns() {
        return numColumns;
    }

    protected String getTitle() {
        return title;
    }

    protected String getDescription() {
        return description;
    }

    protected String getAuthor() {
        return author;
    }

    protected Map<String, String> getSimulationParams() {
        return simulationParams;
    }
}