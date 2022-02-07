package cellsociety.Model;

import java.util.Collection;

/**
 * This class represents the data held by one cell in a cell automata simulation.
 *
 * @author Matt Knox
 */
public class Cell {

  private CellState currentState;
  private CellState nextState;

  private Collection<Cell> neighbors;
  private CellObject resident;

  /**
   * Class constructor. Initializes the cell with a CellState.
   * @param initialState the initial CellState
   */
  public Cell(CellState initialState) {
    currentState = initialState;
  }

  /**
   * @return the object living inside that cell
   */
  public CellObject getResident() { return resident; }

  /**
   * @param o The CellObject that should live in this cell
   */
  public void setResident(CellObject o) { resident = o; }

  /**
   * Remove the object living in this cell
   */
  public void clearResident() { resident = null; }

  /**
   * @param neighbors the cells that belong in this cell's neighborhood
   */
  public void addNeighbors(Collection<Cell> neighbors) {
    this.neighbors = neighbors;
  }

  /**
   * @param next the state that the cell should have after updating
   */
  public void assignNextState(CellState next) {
    nextState = next;
  }

  /**
   * Update the cell's state to its assigned next state
   */
  public void update() {
    currentState = nextState;
    nextState = null;
  }

  /**
   * @return the Cells in this Cell's neighborhood
   */
  public Collection<Cell> getNeighbors() { return neighbors;}

  /**
   * @return the current state of the cell
   */
  public CellState getState() {
    return currentState;
  }

  /**
   * @return the state that the cell should have after updating
   */
  public CellState getNextState() { return nextState; }

  public void moveResidentTo(Cell c) {
    c.setResident(resident);
    resident = null;
  }
}
