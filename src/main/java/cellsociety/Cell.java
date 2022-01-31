package cellsociety;

import cellsociety.CellState.*;
import java.util.Collection;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Cell {
  private final Rectangle rect;
  private CellState currentState;
  private CellState nextState;
  private Collection<Cell> neighbors;
  private CellObject resident;

  public Cell(CellState initialState) {
    rect = new Rectangle();
    currentState = initialState;
//HEAD
    resident = setResident(initialState);
  }

  public Cell(double x, double y, double size, CellState initialState, double strokeWidth) {
    this(x,y,size,size,initialState,strokeWidth);
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
=======
    nextState = getNextState();
>>>>>>> master


  }






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

  public Rectangle getRect() { return rect; }

  public void moveResidentTo(Cell c) {
    c.setResident(resident);
    resident = null;
  }
}
