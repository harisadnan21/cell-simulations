package cellsociety.Model;

import cellsociety.Model.CellState.RPSState;
import java.util.List;
import java.util.Map;

/**
 * This class implements the Rock Paper Scissors simulation.
 *
 * @author Matt Knox
 */
public class RockPaperScissors extends CellularAutomataAlgorithm{

  public static final List<String> SPECIFIC_PARAMS = List.of("threshold");
  private int threshold;

  /**
   * Class constructor.
   *
   * @param data SimulationData record that contains the data obtained from parsing the configuration
   *             XML
   */
  public RockPaperScissors(SimulationData data) {
    super(data);
  }

  @Override
  protected void setUpSimulationParameters() {
    checkSimulationParameters(SPECIFIC_PARAMS);
    Map<String, String> params = getSimulationParams();
    threshold = Integer.parseInt(params.get("threshold"));
  }

  @Override
  protected void initializeResidents(Grid g) {

  }

  /**
   * Runs the Rock Paper Scissors algorithm
   * @param g Grid that contains the cells that must be updated
   */
  @Override
  public void runAlgorithm(Grid g) {
    for(Cell[] cellArray: g.getCells()) {
      for(Cell c: cellArray) {
        updateCell(c);
      }
    }
  }

  private void updateCell(Cell c) {
    RPSState currentState = (RPSState) c.getState();
    RPSState defeatorState = getDefeatorState(currentState);
    int numLosses = 0;
    for(Cell neighbor: c.getNeighbors()) {
      if(neighbor.getState() == defeatorState)
        numLosses++;
    }
    if(numLosses > threshold)
      c.assignNextState(defeatorState);
    else
      c.assignNextState(c.getState());
  }

  private RPSState getDefeatorState(RPSState currentState) {
    return switch (currentState) {
      case Rock -> RPSState.Paper;
      case Paper -> RPSState.Scissors;
      case Scissors -> RPSState.Rock;
    };
  }
}
