package cellsociety.Model;


import cellsociety.CellularAutomata;
import cellsociety.Model.CellState.GameOfLifeState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class represents an instance of a Game of Life simulation algorithm.
 *
 * @author Matt Knox
 */
public class GameOfLife extends CellularAutomataAlgorithm {

  public static final List<String> SPECIFIC_PARAMS = Collections.emptyList();
  public static final String BUNDLE_NAME = "GameOfLife";

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

  // Determines if cell should be alive or dead based on if it has enough live neighbors
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