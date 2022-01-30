package cellsociety;


import cellsociety.CellState.GameOfLifeState;

public class SchellingSegregation extends CellularAutomataAlgorithm {



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
  public CellState runAlgorithm(Grid g, Cell c) {
    return GameOfLifeState.Dead;
  }
}