package cellsociety;

import cellsociety.CellState.*;
import java.util.Collection;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Cell {
  private CellState currentState;
  private CellState nextState;
  private Collection<Cell> neighbors;
  private CellObject resident;

  public Cell(CellState initialState) {
    currentState = initialState;
    resident = setResident(initialState);
  }

  private CellObject setResident(CellState initialState) {
    if(initialState instanceof SchellingSegregationState) {
      SchellingSegregationState s = (SchellingSegregationState) initialState;
      switch(s) {
        case Empty -> { return null; }
        case AgentA -> { return new Agent(true); }
        case AgentB -> { return new Agent(false); }
      }
    }

    if(initialState instanceof  WaTorState) {
      WaTorState s = (WaTorState) initialState;
      switch(s) {
        case Empty -> { return null; }
        case Shark -> { return new Shark(5,5); }
        case Fish -> { return new Fish(5); }
      }
    }

    return null;
  }

  public CellObject getResident() { return resident; }

  public void setResident(CellObject o) { resident = o; }

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
}
