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

  public Cell(CellState initialState) {
    rect = new Rectangle();
    currentState = initialState;
    nextState = getNextState();


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
}
