package cellsociety;

import cellsociety.CellState.GameOfLifeState;
import cellsociety.CellState.PercolationState;
import cellsociety.CellState.SchellingSegregationState;
import cellsociety.CellState.SpreadingOfFireState;
import cellsociety.CellState.WaTorState;
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
  private CellState[][] initialCellConfig; //TODO: Change this to array of CellState

  // Attributions
  private String title;
  private String description;
  private String author;

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
    title = data.title();
    description = data.description();
    author = data.author();
    simulationParams = data.params();

    setUpSimulationParameters();

    initializeCellConfig(data.startingConfig());
  }

  /**
   * Initialize specific parameters for subclasses from values in simulationParams.
   */
  protected abstract void setUpSimulationParameters();

  /**
   * Check if simulationParams contains all the needed parameters for this algorithm. If not, throw
   * an Exception.
   *
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

  public abstract void runAlgorithm(Grid g);

  // Initializes an array representing the starting state of every cell in the simulation
  private void initializeCellConfig(String config) {
    String trimmedConfig = config.trim().replaceAll("\\s+", " ");
    initialCellConfig = new CellState[numRows][numColumns];
    String[] initialStates = trimmedConfig.split(" ");
    CellState[] possibleStates = new CellState[0];

    switch (simulationType) {
      case CellularAutomataAlgorithm.GAME_OF_LIFE -> possibleStates = GameOfLifeState.values();
      case CellularAutomataAlgorithm.PERCOLATION -> possibleStates = PercolationState.values();
      case CellularAutomataAlgorithm.SCHELLING_SEGREGATION -> possibleStates = SchellingSegregationState.values();
      case CellularAutomataAlgorithm.SPREADING_OF_FIRE -> possibleStates = SpreadingOfFireState.values();
      case CellularAutomataAlgorithm.WATOR -> possibleStates = WaTorState.values();
    }

    for (int i = 0; i < initialCellConfig.length; i++) {
      for (int j = 0; j < initialCellConfig[0].length; j++) {
        initialCellConfig[i][j] = possibleStates[Integer.parseInt(initialStates[i * initialCellConfig[0].length + j])];
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

  protected CellState[][] getInitialCellConfig() {
    return initialCellConfig;
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