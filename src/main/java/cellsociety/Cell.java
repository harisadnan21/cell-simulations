package cellsociety;

import cellsociety.CellState.GameOfLifeState;
import cellsociety.CellState.PercolationState;
import cellsociety.CellState.SchellingSegregationState;
import cellsociety.CellState.SpreadingOfFireState;
import cellsociety.CellState.WaTorState;
import java.util.Collection;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Cell {
  private final Rectangle rect;
  private final Simulation simtype;
  private CellState currentState;
  private CellState nextState;
  private Collection<Cell> neighbors;

  public Cell(double x, double y, double width, double height, CellState initialState) {
    rect = new Rectangle();
    rect.setX(x);
    rect.setY(y);
    rect.setWidth(width);
    rect.setHeight(height);
    rect.setFill(getColor(initialState));
    currentState = initialState;
    simtype = getSimulationType(initialState);
  }

  public Cell(double x, double y, double size, CellState initialState) {
    this(x,y,size,size,initialState);
  }

  private Simulation getSimulationType(CellState initialState) {
    if (initialState instanceof GameOfLifeState)
      return new GameOfLife();
    if (initialState instanceof SpreadingOfFireState)
      return new SpreadingOfFire();
    if (initialState instanceof SchellingSegregationState)
      return new SchellingSegregation();
    if (initialState instanceof WaTorState)
      return new WaTor();
    if (initialState instanceof PercolationState)
      return new Percolation();

    // TODO: Find better default behavior
    return new GameOfLife();

  }

  private Paint getColor(CellState initialState) {

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

  public void addNeighbors(Collection<Cell> neighbors) {
    this.neighbors = neighbors;
  }

  public void calculate() {
    CellState next = simtype.getNext(this,neighbors);
    nextState = next;
  }

  public void update() {
    currentState = nextState;
    nextState = null;
  }

  public CellState getState() {
    return currentState;
  }


}