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

  public Cell(CellState initialState) {
    currentState = initialState;
  }


  /**
   *
   * @return the resident in this Cell
   */
  public CellObject getResident() { return resident; }

  /**
   * Sets the resident in this cell
   * @param o: the resident to set
   */
  public void setResident(CellObject o) { resident = o; }

  /**
   * Removes the resident from this cell
   */
  public void clearResident() { resident = null; }

  /**
   * Adds neighbors to this cell
   * @param neighbors: the neighbors to add
   */
  public void addNeighbors(Collection<Cell> neighbors) {
    this.neighbors = neighbors;
  }

  /**
   * Assigns the next state of this cell
   * @param next: the next state
   */
  public void assignNextState(CellState next) {
    nextState = next;
  }

  /**
   * Updates the state of this cell
   */
  public void update() {
    currentState = nextState;
    nextState = null;
  }

  /**
   * Returns the current neighbors
   * @return the neighbors
   */
  public Collection<Cell> getNeighbors() { return neighbors;}

  /**
   * Gets the current state of the cell
   *
   * @return the state of the cell
   */
  public CellState getState() {
    return currentState;
  }

  /**
   * Gets the next state of the cell
   * @return the next state
   */
  public CellState getNextState() { return nextState; }

}
