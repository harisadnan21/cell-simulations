package cellsociety.Model;


import cellsociety.Model.CellState.PercolationState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class implements the Percolation simulation.
 *
 * @author Matt Knox
 */
public class Percolation extends CellularAutomataAlgorithm {

  public static final List<String> SPECIFIC_PARAMS = Collections.emptyList();
  public static final String BUNDLE_NAME = "Percolation";

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
  protected void initializeResidents(Grid g) {

  }

  /**
   * Runs the percolation update algorithm
   * @param g Grid that contains the cells that must be updated
   */
  @Override
  public void runAlgorithm(Grid g) {
    for(Cell[] cellarray: g.getCells()) {
      for(Cell c: cellarray) {
        updateCell(c);
      }
    }
  }

  private void updateCell(Cell c) {
    PercolationState currentState = (PercolationState) c.getState();

    switch(currentState) {
      case Open -> {
        if(hasPercolatedNeighbor(c)) {
          c.assignNextState(PercolationState.Percolated);
          //return PercolationState.Percolated;
        } else {
          c.assignNextState(PercolationState.Open);
          //return PercolationState.Open;
        }
      } case Percolated -> {
        c.assignNextState(PercolationState.Percolated);
        //return PercolationState.Percolated;
      } case Blocked -> {
        c.assignNextState(PercolationState.Blocked);
        //return PercolationState.Blocked;
      }
    }
    //return null;
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