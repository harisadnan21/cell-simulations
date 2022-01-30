package cellsociety;


import cellsociety.CellState.GameOfLifeState;
import cellsociety.CellState.PercolationState;

public class Percolation extends CellularAutomataAlgorithm {



  /**
   * Class constructor.
   *
   * @param data SimulationData record that contains the data obtained from parsing the configuration
   *             XML
   */
  public Percolation(SimulationData data) {
    super(data);
  }

  @Override
  protected void setUpSimulationParameters() {

  }

  @Override
  public CellState runAlgorithm(Grid g, Cell c) {
    PercolationState currentState = (PercolationState) c.getState();

    switch(currentState) {
      case Open -> {
        if(hasPercolatedNeighbor(c)) {
          return PercolationState.Percolated;
        } else {
          return PercolationState.Open;
        }
      } case Percolated -> {
        return PercolationState.Percolated;
      } case Blocked -> {
        return PercolationState.Blocked;
      }
    }
    return null;
  }

  private boolean hasPercolatedNeighbor(Cell c) {
    for(Cell neighbor: c.getNeighbors()) {
      PercolationState neighborState = (PercolationState) neighbor.getState();
      if(neighborState == PercolationState.Percolated) {
        return true;
      }
    }
    return false;
  }
}