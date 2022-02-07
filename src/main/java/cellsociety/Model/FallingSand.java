package cellsociety.Model;

import cellsociety.Model.CellState.FallingSandState;
import java.util.Collections;
import java.util.List;

public class FallingSand extends CellularAutomataAlgorithm {

  public static final List<String> SPECIFIC_PARAMS = Collections.emptyList();
  public static final String BUNDLE_NAME = "FallingSand";

  /**
   * Class constructor.
   *
   * @param data SimulationData record that contains the data obtained from parsing the configuration
   *             XML
   */
  public FallingSand(SimulationData data) {
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
    Cell[][] cells = g.getCells();
    for (int i = 0; i < cells.length - 1; i++) {
      for (int j = 0; j < cells[0].length; j++) {
        // Sand just falls to next block down
        if (cells[i][j].getState() == FallingSandState.Sand && cells[i + 1][j].getState() == FallingSandState.Empty) {
          cells[i][j].assignNextState(FallingSandState.Empty);
          cells[i + 1][j].assignNextState(FallingSandState.Sand);
        }
        // Sand is at bottom and needs to flow over onto its neighbors
        else if (cells[i][j].getState() == FallingSandState.Sand && cells[i + 1][j].getState() == FallingSandState.Sand) {
          if (j > 0) cells[i + 1][j - 1].assignNextState(FallingSandState.Sand);
          if (j < cells[0].length - 1) cells[i + 1][j + 1].assignNextState(FallingSandState.Sand);
        }
      }
    }

    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells[0].length; j++) {
        if(cells[i][j].getNextState() == null) {
          cells[i][j].assignNextState(cells[i][j].getState());
        }
      }
    }



  }



}
