package cellsociety.Model;

import cellsociety.Model.CellState.GameOfLifeState;
import cellsociety.Model.CellState.PercolationState;
import cellsociety.Model.CellState.SchellingSegregationState;
import cellsociety.Model.CellState.SpreadingOfFireState;
import cellsociety.Model.CellState.WaTorState;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * This class provides an abstraction of a simulation instance and defines what characteristics all
 * simulations should have. Subclasses of SimulationType can specify desired algorithms for cell
 * behavior and state.
 *
 * @author Edison Ooi
 */
public abstract class CellularAutomataAlgorithm {

  public static final int GAME_OF_LIFE = 0;
  public static final int PERCOLATION = 1;
  public static final int SCHELLING_SEGREGATION = 2;
  public static final int SPREADING_OF_FIRE = 3;
  public static final int WATOR = 4;

  private final int simulationType; //TODO: Change this to an enum of some kind

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
   */
  protected void checkSimulationParameters(List<String> SPECIFIC_PARAMS) {
    Map<String, String> params = getSimulationParams();

    for (String param : SPECIFIC_PARAMS) {
      if (!params.containsKey(param)) {
        throw new RuntimeException("Parameter " + param + " not found for simulation type "
            + getSimulationType());
      }
    }
  }

  protected abstract void initializeResidents(Grid g);

  public abstract void runAlgorithm(Grid g);

  // Initializes an array representing the starting state of every cell in the simulation
  private void initializeCellConfig(String config) {
    String[] initialStates = getArrayFromConfigString(config);

    CellState[] possibleStates = new CellState[0];
    switch (simulationType) {
      case CellularAutomataAlgorithm.GAME_OF_LIFE -> possibleStates = GameOfLifeState.values();
      case CellularAutomataAlgorithm.PERCOLATION -> possibleStates = PercolationState.values();
      case CellularAutomataAlgorithm.SCHELLING_SEGREGATION -> possibleStates = SchellingSegregationState.values();
      case CellularAutomataAlgorithm.SPREADING_OF_FIRE -> possibleStates = SpreadingOfFireState.values();
      case CellularAutomataAlgorithm.WATOR -> possibleStates = WaTorState.values();
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
          case "0" -> neighborhoodConfig[i][j] = 0;
          case "1" -> neighborhoodConfig[i][j] = 1;
          case "X" -> {
            // Only add central reference cell if it has not been seen before, else we have bad input
            if (!foundReferenceCell) {
              neighborhoodConfig[i][j] = 2;
              foundReferenceCell = true;
            } else {
              throw new RuntimeException("Multiple reference cells found in neighborhood config.");
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

  protected int getSimulationType() {
    return simulationType;
  }

  protected int getNumRows() {
    return numRows;
  }

  protected int getNumColumns() {
    return numColumns;
  }

  public CellState[][] getInitialCellConfig() {
    return initialCellConfig;
  }

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

  protected Map<String, String> getSimulationParams() {
    return simulationParams;
  }
}