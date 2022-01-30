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





  private Paint getFillColor(CellState initialState) {

    if (initialState instanceof GameOfLifeState) {
      GameOfLifeState s = (GameOfLifeState)initialState;
      switch(s) {
        case Live: return Color.BLACK;
        case Dead: return Color.WHITE;
      }
    }

    if (initialState instanceof SpreadingOfFireState) {
      SpreadingOfFireState s = (SpreadingOfFireState)initialState;
      switch(s) {
        case Empty: return Color.YELLOW;
        case Tree: return Color.GREEN;
        case Burning: return Color.DARKRED;
      }
    }

    if (initialState instanceof SchellingSegregationState) {
      SchellingSegregationState s = (SchellingSegregationState)initialState;
      switch(s) {
        case Empty: return Color.WHITE;
        case AgentA: return Color.RED;
        case AgentB: return Color.BLUE;
      }
    }


    if (initialState instanceof WaTorState) {
      WaTorState s = (WaTorState)initialState;
      switch(s) {
        case Empty: return Color.WHITE;
        case Fish: return Color.GREEN;
        case Shark: return Color.BLUE;
      }
    }


    if (initialState instanceof PercolationState) {
      PercolationState s = (PercolationState)initialState;
      switch(s) {
        case Blocked: return Color.BLACK;
        case Open: return Color.WHITE;
        case Percolated: return Color.BLUE;
      }
    }

    return Color.GRAY;
  }

  private Paint getStrokeColor(CellState initialState) {
    if (initialState instanceof GameOfLifeState)
      return Color.BLUE;
    else if(initialState instanceof SpreadingOfFireState)
      return Color.BLACK;
    else if(initialState instanceof SchellingSegregationState)
      return Color.BLACK;
    else if(initialState instanceof WaTorState)
      return Color.BLACK;
    else if(initialState instanceof PercolationState)
      return Color.BLACK;

    return Color.TRANSPARENT;
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
