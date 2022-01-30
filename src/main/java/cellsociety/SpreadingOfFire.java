package cellsociety;


import cellsociety.CellState.GameOfLifeState;
import cellsociety.CellState.SpreadingOfFireState;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SpreadingOfFire extends CellularAutomataAlgorithm {

  public static final List<String> SPECIFIC_PARAMS = List.of("probCatch", "probGeneration");
  // Probability of an empty cell becoming a new tree
  private double probGeneration;
  // Probability of a tree in a cell catching fire if a neighboring cell is on fire
  private double probCatch;

  /**
   * Class constructor.
   *
   * @param data SimulationData record that contains the data obtained from parsing the
   *             configuration XML
   */
  public SpreadingOfFire(SimulationData data) {
    super(data);
  }

  @Override
  protected void setUpSimulationParameters() {
    checkSimulationParameters();
    Map<String, String> params = getSimulationParams();
    probCatch = Double.parseDouble(params.get("probCatch"));
    probGeneration = Double.parseDouble(params.get("probGeneration"));
  }

  @Override
  protected void checkSimulationParameters() {
    Map<String, String> params = getSimulationParams();

    for (String param : SPECIFIC_PARAMS) {
      if (!params.containsKey(param)) {
        throw new RuntimeException("Parameter " + param + " not found for simulation type "
            + "Spreading of Fire");
      }
    }
  }

  @Override
  public CellState runAlgorithm(Grid g, Cell c) {
    CellState currentState = c.getState();

    switch ((SpreadingOfFireState) currentState) {
      case Empty:
        return handleEmptyState(c);
      case Tree:
        return handleTreeState(c);
      case Burning:
        return handleBurningState(c);
    }
    c.assignNextState(c.getState());
    return c.getState();
  }

  // If cell is empty, it has a chance of becoming a tree
  private CellState handleEmptyState(Cell c) {
    if (Math.random() < probGeneration) {
      c.assignNextState(SpreadingOfFireState.Tree);
    } else {
      c.assignNextState(SpreadingOfFireState.Empty);
    }
    return c.getNextState();
  }

  // If cell is a tree, it has a chance of burning if any of its neighbors are burning
  private CellState handleTreeState(Cell c) {
    for (Cell neighbor : c.getNeighbors()) {
      if (neighbor.getState() == SpreadingOfFireState.Burning) {
        if (Math.random() < probCatch) {
          c.assignNextState(SpreadingOfFireState.Burning);
          return c.getNextState();
        }
      }
    }

    c.assignNextState(SpreadingOfFireState.Tree);
    return c.getNextState();
  }

  // If cell is burning, it remains burning
  private CellState handleBurningState(Cell c) {
    c.assignNextState(SpreadingOfFireState.Burning);
    return SpreadingOfFireState.Burning;
  }
}