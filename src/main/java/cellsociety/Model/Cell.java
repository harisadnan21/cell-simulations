package cellsociety.Model;

import cellsociety.Model.CellState.*;
import java.util.Collection;

public class Cell {
  private CellState currentState;
  private CellState nextState;
  private Collection<Cell> neighbors;
  private CellObject resident;

  public Cell(CellState initialState) {
    currentState = initialState;
  }


  public CellObject getResident() { return resident; }

  public void setResident(CellObject o) { resident = o; }

  public void clearResident() { resident = null; }

  public void addNeighbors(Collection<Cell> neighbors) {
    this.neighbors = neighbors;
  }

  public void assignNextState(CellState next) {
    nextState = next;
  }

  public void update() {
    currentState = nextState;
    nextState = null;
  }

  public Collection<Cell> getNeighbors() { return neighbors;}

  public CellState getState() {
    return currentState;
  }

  public CellState getNextState() { return nextState; }

  public void moveResidentTo(Cell c) {
    c.setResident(resident);
    resident = null;
  }
}
