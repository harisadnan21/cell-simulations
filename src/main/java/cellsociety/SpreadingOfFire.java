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
  public void runAlgorithm(Grid g) {
    for(Cell c: g.getCells()) {
      updateCell(c);
    }
  }

  private void updateCell(Cell c) {
    CellState currentState = c.getState();

    switch ((SpreadingOfFireState) currentState) {
      case Empty:
        handleEmptyState(c);
        return;
      case Tree:
        handleTreeState(c);
        return;
      case Burning:
        handleBurningState(c);
        return;
    }
    c.assignNextState(c.getState());
  }

  // If cell is empty, it has a chance of becoming a tree
  private void handleEmptyState(Cell c) {
    if (Math.random() < probGeneration) {
      c.assignNextState(SpreadingOfFireState.Tree);
    } else {
      c.assignNextState(SpreadingOfFireState.Empty);
    }
  }

  // If cell is a tree, it has a chance of burning if any of its neighbors are burning
  private void handleTreeState(Cell c) {
    for (Cell neighbor : c.getNeighbors()) {
      if (neighbor.getState() == SpreadingOfFireState.Burning) {
        System.out.println("It's burning!");
        if (Math.random() < probCatch) {
          //return SpreadingOfFireState.Burning;
          c.assignNextState(SpreadingOfFireState.Burning);
          return;
          //return c.getNextState();
        }
      }
    }
    //return SpreadingOfFireState.Tree;
    c.assignNextState(SpreadingOfFireState.Tree);
    //return c.getNextState();
  }

  // If cell is burning, it remains burning
  private void handleBurningState(Cell c) {
    c.assignNextState(SpreadingOfFireState.Burning);
    //return SpreadingOfFireState.Burning;
  }
}