package cellsociety.Model;

import cellsociety.CellularAutomata;
import cellsociety.Model.CellState.FallingSandState;
import cellsociety.Model.CellState.GameOfLifeState;
import cellsociety.Model.CellState.PercolationState;
import cellsociety.Model.CellState.SchellingSegregationState;
import cellsociety.Model.CellState.SpreadingOfFireState;
import cellsociety.Model.CellState.WaTorState;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * This class provides an abstraction of a simulation instance and defines what characteristics all
 * simulations should have. Subclasses of SimulationType can specify desired algorithms for cell
 * behavior and state.
 *
 * @author Edison Ooi
 * @author Matt Knox
 */
public abstract class CellularAutomataAlgorithm {

  // Represents the type of simulation algorithm that this instance represents
  private final int simulationType;

  // Information for setting up grid of cells
  private int numRows;
  private int numColumns;
  private CellState[][] initialCellConfig;

  // Attributions
  private String title;
  private String description;
  private String author;

  // Information about neighborhood configuration
  private int numNeighborRows;
  private int numNeighborColumns;
  private int[][] neighborhoodConfig;

  // Parameters specific to this simulation type, for example probCatch in Spreading of Fire simulations
  private Map<String, String> simulationParams;

  /**
   * Class constructor.
   *
   * @param data SimulationData record that contains the data obtained from parsing the
   *             configuration XML
   */
  public CellularAutomataAlgorithm(SimulationData data) {
    simulationType = data.simulationType();
    numRows = data.numRows();
    numColumns = data.numColumns();
    numNeighborRows = data.numNeighborRows();
    numNeighborColumns = data.numNeighborColumns();
    title = data.title();
    description = data.description();
    author = data.author();
    simulationParams = data.params();

    setUpSimulationParameters();

    initializeNeighborConfig(data.neighborConfig());

    initializeCellConfig(data.startingConfig());
  }

  /**
   * Initialize specific parameters for subclasses from values in simulationParams.
   */
  protected abstract void setUpSimulationParameters();

  /**
   * Check if simulationParams contains all the needed parameters for this algorithm. If not, throw
   * an Exception.
   * <p>
   * This should always be called on the first line of setUpSimulationParameters.
   *
   * @param specificParams List of keys representing parameters specific to this algorithm type
   */
  protected void checkSimulationParameters(List<String> specificParams) {
    Map<String, String> params = getSimulationParams();

    for (String param : specificParams) {
      if (!params.containsKey(param)) {
        throw new RuntimeException("Parameter " + param + " not found for simulation type "
            + getSimulationType());
      }
    }
  }

  /**
   * Initializes the CellObjects that live within certain cells in Grid g
   *
   * @param g Grid that contains some cells to be populated with CellObjects
   */
  protected abstract void initializeResidents(Grid g);

  /**
   * Sets the appropriate next state for every Cell in Grid g based on the Cell's neighbors and
   * the Grid's information as a whole.
   *
   * @param g Grid that contains the cells that must be updated
   */
  public abstract void runAlgorithm(Grid g);

  // Initializes an array representing the starting state of every cell in the simulation
  private void initializeCellConfig(String config) {
    String[] initialStates = getArrayFromConfigString(config);

    CellState[] possibleStates = new CellState[0];
    switch (simulationType) {
      case CellularAutomata.GAME_OF_LIFE -> possibleStates = GameOfLifeState.values();
      case CellularAutomata.PERCOLATION -> possibleStates = PercolationState.values();
      case CellularAutomata.SCHELLING_SEGREGATION -> possibleStates = SchellingSegregationState.values();
      case CellularAutomata.SPREADING_OF_FIRE -> possibleStates = SpreadingOfFireState.values();
      case CellularAutomata.WATOR -> possibleStates = WaTorState.values();
      case CellularAutomata.FALLING_SAND -> possibleStates = FallingSandState.values();
    }

    initialCellConfig = new CellState[numRows][numColumns];

    switch (initialStates[0]) {
      case "Random" -> initializeRandomCellConfig(possibleStates);
      case "Probability" -> initializeProbabilityCellConfig(initialStates, possibleStates);
      default -> initializeSetCellConfig(initialStates, possibleStates);
    }
  }

  // Initializes cell config from the given configuration from the XML file
  private void initializeSetCellConfig(String[] initialStates, CellState[] possibleStates) {
    if (numRows * numColumns != initialStates.length) {
      throw new IllegalArgumentException(
          "Cell config size does not match number of rows and columns!");
    }

    for (int i = 0; i < initialCellConfig.length; i++) {
      for (int j = 0; j < initialCellConfig[0].length; j++) {
        initialCellConfig[i][j] = possibleStates[Integer.parseInt(
            initialStates[i * initialCellConfig[0].length + j])];
      }
    }
  }

  // Initializes cell config by choosing a random state for each cell
  private void initializeRandomCellConfig(CellState[] possibleStates) {
    for (int i = 0; i < initialCellConfig.length; i++) {
      for (int j = 0; j < initialCellConfig[0].length; j++) {
        initialCellConfig[i][j] = possibleStates[(int) (Math.random() * possibleStates.length)];
      }
    }
  }

  // Initializes cell config given the probability that each cell should have a certain state
  private void initializeProbabilityCellConfig(String[] initialStates, CellState[] possibleStates) {
    if (initialStates.length - 1 != possibleStates.length) {
      throw new IllegalArgumentException(
          "Probabilities given in cell config do not match number of possible states!");
    }

    double[] probabilities = new double[initialStates.length - 1];
    probabilities[0] = Double.parseDouble(initialStates[1]);
    double cumulativeSum = probabilities[0];

    for (int i = 2; i < initialStates.length; i++) {
      double currentProb = Double.parseDouble(initialStates[i]);

      probabilities[i - 1] = Double.parseDouble(initialStates[i]) + probabilities[i - 2];
      cumulativeSum += currentProb;
    }

    if (cumulativeSum != 1.0) {
      throw new IllegalArgumentException("Probabilities given in cell config do not add up to 1.");
    }

    for (int i = 0; i < initialCellConfig.length; i++) {
      for (int j = 0; j < initialCellConfig[0].length; j++) {
        double rand = Math.random();

        for (int k = 0; k < probabilities.length; k++) {
          if (rand < probabilities[k]) {
            initialCellConfig[i][j] = possibleStates[k];
            break;
          }
        }
      }
    }
  }

  // Initialize an array that represents the desired neighborhood for a given reference cell
  private void initializeNeighborConfig(String config) {
    String[] neighbors = getArrayFromConfigString(config);

    if (numNeighborRows * numNeighborColumns != neighbors.length) {
      throw new IllegalArgumentException(
          "Neighbor config size does not match number of rows and columns!");
    }

    neighborhoodConfig = new int[numNeighborRows][numNeighborColumns];

    boolean foundReferenceCell = false;

    for (int i = 0; i < numNeighborRows; i++) {
      for (int j = 0; j < numNeighborColumns; j++) {
        String currentNeighbor = neighbors[i * numNeighborColumns + j];

        switch (currentNeighbor) {
          case Neighborhood.ignoreCellString -> neighborhoodConfig[i][j] = Neighborhood.ignoreCell;
          case Neighborhood.neighborCellString -> neighborhoodConfig[i][j] = Neighborhood.neighborCell;
          case Neighborhood.refCellString -> {
            // Only add central reference cell if it has not been seen before, else we have bad input
            if (!foundReferenceCell) {
              neighborhoodConfig[i][j] = Neighborhood.refCell;
              foundReferenceCell = true;
            } else {
              throw new IllegalArgumentException("Multiple reference cells found in neighborhood config.");
            }
          }
        }
      }
    }

    if (!foundReferenceCell) {
      throw new IllegalArgumentException("Reference cell in neighborhood config not found");
    }
  }

  // Helper method to convert a configuration string read from XML to a desirable String array
  private String[] getArrayFromConfigString(String config) {
    String trimmedConfig = config.trim().replaceAll("\\s+", " ");
    return trimmedConfig.split(" ");
  }

  /**
   * @return int representing type of simulation algorithm
   */
  protected int getSimulationType() {
    return simulationType;
  }

  protected int getNumRows() {
    return numRows;
  }

  protected int getNumColumns() {
    return numColumns;
  }

  /**
   * @return 2D array of CellState representing the initial states of all Cells defined in config file
   */
  public CellState[][] getInitialCellConfig() {
    return initialCellConfig;
  }

  /**
   * @return 2D int array representing the desired neighborhood of a singular reference cell
   */
  public int[][] getNeighborhoodConfig() {
    return neighborhoodConfig;
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

  /**
   * @return Map of key-value pairs representing parameters specific to this simulation algorithm
   */
  protected Map<String, String> getSimulationParams() {
    return simulationParams;
  }
}