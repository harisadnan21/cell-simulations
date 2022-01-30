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

  public Cell(double x, double y, double width, double height, CellState initialState, double strokeWidth) {
    rect = new Rectangle();
    rect.setX(x);
    rect.setY(y);
    rect.setWidth(width);
    rect.setHeight(height);
    rect.setFill(getFillColor(initialState));
    rect.setStroke(getStrokeColor(initialState));
    rect.setStrokeWidth(strokeWidth);
    currentState = initialState;
  }

  public Cell(double x, double y, double size, CellState initialState, double strokeWidth) {
    this(x,y,size,size,initialState,strokeWidth);
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
