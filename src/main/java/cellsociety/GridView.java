package cellsociety;

import cellsociety.CellState.GameOfLifeState;
import cellsociety.CellState.PercolationState;
import cellsociety.CellState.SchellingSegregationState;
import cellsociety.CellState.SpreadingOfFireState;
import cellsociety.CellState.WaTorState;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GridView extends TilePane {
  Rectangle[][] cellViews;

  public GridView(double width, double height, int numRows, int numColumns) {
    super();
    setWidth(width);
    setHeight(height);
    setPrefRows(numRows);
    setPrefColumns(numColumns);
    cellViews = new Rectangle[numRows][numColumns];
    addCellsToGrid();
  }

  private void addCellsToGrid() {
    for (int i = 0; i < cellViews.length; i++) {
      for (int j = 0; j < cellViews[0].length; j++) {
        this.getChildren().add(cellViews[i][j]);
      }
    }
  }

  public void updateCells(CellState[][] states) {
    if(states.length != getPrefRows() || states[0].length != getPrefColumns()) {
      throw new RuntimeException("CellStates are not the correct size");
    }

    for (int i = 0; i < states.length; i++) {
      for (int j = 0; j < states[0].length; j++) {
        cellViews[i][j].setFill(getFillColor(states[i][j]));
        cellViews[i][j].setStroke(getStrokeColor(states[i][j]));
      }
    }
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
}
