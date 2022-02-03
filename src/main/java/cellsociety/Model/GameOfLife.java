package cellsociety.Model;


import cellsociety.Model.CellState.GameOfLifeState;
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
  public void runAlgorithm(Grid g) {
    for(Cell[] cellArray: g.getCells()) {
      for(Cell c: cellArray) {
        updateCell(c);
      }
    }
  }

  private void updateCell(Cell c) {
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
        if(liveCount == 3) {
          c.assignNextState(GameOfLifeState.Live);
        } else {
          c.assignNextState(GameOfLifeState.Dead);
        }
      }
      case Live -> {
        if(liveCount == 2 || liveCount == 3) {
          c.assignNextState(GameOfLifeState.Live);
        } else {
          c.assignNextState(GameOfLifeState.Dead);
        }
      }
    }
  }

}