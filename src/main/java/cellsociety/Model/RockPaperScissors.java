package cellsociety.Model;

import cellsociety.Model.CellState.RPSState;
import java.util.List;
import java.util.Map;

/**
 * This class represents an instance of a Percolation simulation algorithm.
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

  @Override
  public void runAlgorithm(Grid g) {
    for(Cell[] cellArray: g.getCells()) {
      for(Cell c: cellArray) {
        updateCell(c);
      }
    }
  }

  // Changes current cell into its defeator if it loses to enough neighbors
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

  // Gets the RPSState that beats this state
  private RPSState getDefeatorState(RPSState currentState) {
    return switch (currentState) {
      case Rock -> RPSState.Paper;
      case Paper -> RPSState.Scissors;
      case Scissors -> RPSState.Rock;
    };
  }
}
