package cellsociety;


import cellsociety.CellState.GameOfLifeState;

public class SpreadingOfFire extends CellularAutomataAlgorithm {



  /**
   * Class constructor.
   *
   * @param data SimulationData record that contains the data obtained from parsing the configuration
   *             XML
   */
  public SpreadingOfFire(SimulationData data) {
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