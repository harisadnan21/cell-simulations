package cellsociety;


import cellsociety.CellState.GameOfLifeState;
import java.util.ArrayList;
import java.util.List;

public class SchellingSegregation extends CellularAutomataAlgorithm {

  public static final List<String> SPECIFIC_PARAMS = new ArrayList<>();

  /**
   * Class constructor.
   *
   * @param data SimulationData record that contains the data obtained from parsing the configuration
   *             XML
   */
  public SchellingSegregation(SimulationData data) {
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
    return GameOfLifeState.Dead;
  }
}