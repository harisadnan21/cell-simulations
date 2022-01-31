package cellsociety;


import cellsociety.CellState.GameOfLifeState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameOfLife extends CellularAutomataAlgorithm {

  public static final List<String> SPECIFIC_PARAMS = new ArrayList<>();

  /**
   * Class constructor.
   *
   * @param data SimulationData record that contains the data obtained from parsing the configuration
   *             XML
   */
  public GameOfLife(SimulationData data) {
    super(data);
  }

  @Override
  protected void setUpSimulationParameters() {

  }

  @Override
  protected void checkSimulationParameters() {

  }

  @Override
  public CellState runAlgorithm(Grid g, Cell c) {
    GameOfLifeState currentState = (GameOfLifeState) c.getState();
    Collection<Cell> neighbors = c.getNeighbors();

    //get the number of live neighbors
    int liveCount = 0;
    for(Cell neighbor: neighbors) {
      GameOfLifeState neighborState = (GameOfLifeState) neighbor.getState();
      switch(neighborState) {
        case Live -> liveCount++;
      }
    }

    switch(currentState) {
      case Dead -> {
        if(liveCount == 3) return GameOfLifeState.Live;
        return GameOfLifeState.Dead;
      }
      case Live -> {
        if(liveCount == 2 || liveCount == 3) return GameOfLifeState.Live;
        return GameOfLifeState.Dead;
      }
    }
    return null;
  }

}