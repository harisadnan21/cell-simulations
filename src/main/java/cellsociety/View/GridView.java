package cellsociety.View;


import cellsociety.CellularAutomata;
import cellsociety.Model.Cell;
import cellsociety.Model.CellState;
import cellsociety.Model.CellState.GameOfLifeState;
import cellsociety.Model.CellState.PercolationState;
import cellsociety.Model.CellState.RPSState;
import cellsociety.Model.CellState.SchellingSegregationState;
import cellsociety.Model.CellState.SpreadingOfFireState;
import cellsociety.Model.CellState.WaTorState;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GridView extends TilePane {
  private CellView[][] cellViews;
  private int simulationType;





  public GridView(double width, double height, int numRows, int numColumns, int typeOfGrid, int simulationType) {
    super();
    setWidth(width);
    setHeight(height);
    setPrefRows(numRows);
    setPrefColumns(numColumns);
    setTileAlignment(Pos.TOP_LEFT);
    this.simulationType = simulationType;
    cellViews = new CellView[numRows][numColumns];
    addCellsToGrid(width, height, numRows, numColumns, typeOfGrid);
  }

  private void addCellsToGrid(double width, double height, int numRows, int numColumns, int typeOfGrid) {
    for (int i = 0; i < cellViews.length; i++) {
      for (int j = 0; j < cellViews[0].length; j++) {
        switch(typeOfGrid) {
          case CellularAutomata.SQUARE -> cellViews[i][j] = new SquareCellView((width / 1.2) / numRows, (height / 1.2) / numColumns);
          case CellularAutomata.TRIANGLE -> cellViews[i][j] = new TriangleCellView((width / 1.2) / numRows, (height / 1.2) / numColumns,i+j);
          case CellularAutomata.HEXAGON -> cellViews[i][j] = new HexagonCellView((width / 1.2) / numRows, (height / 1.2) / numColumns,i);
        }
        //cellViews[i][j] = new CellView((width / 1.2) / numRows, (height / 1.2) / numColumns);
        this.getChildren().add(cellViews[i][j]);
      }
    }
  }

  public void updateCells(Cell[][] cells) {
    if(cells.length != getPrefRows() || cells[0].length != getPrefColumns()) {
      throw new RuntimeException("CellStates are not the correct size");
    }

    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells[0].length; j++) {
        cellViews[i][j].updateFillAndStroke(cells[i][j].getState(),simulationType);
      }
    }
  }


  public static Paint getFillColor(CellState initialState, int simulationType) {

    switch(simulationType) {
      case CellularAutomata.GAME_OF_LIFE -> {
        GameOfLifeState s = (GameOfLifeState)initialState;
        return switch (s) {
          case Live -> Color.BLACK;
          case Dead -> Color.WHITE;
        };
      }
      case CellularAutomata.PERCOLATION -> {
        PercolationState s = (PercolationState)initialState;
        return switch (s) {
          case Blocked -> Color.BLACK;
          case Open -> Color.WHITE;
          case Percolated -> Color.BLUE;
        };
      }
      case CellularAutomata.SCHELLING_SEGREGATION -> {
        SchellingSegregationState s = (SchellingSegregationState)initialState;
        return switch (s) {
          case Empty -> Color.WHITE;
          case AgentA -> Color.RED;
          case AgentB -> Color.BLUE;
        };
      }
      case CellularAutomata.SPREADING_OF_FIRE -> {
        SpreadingOfFireState s = (SpreadingOfFireState)initialState;
        return switch (s) {
          case Empty -> Color.YELLOW;
          case Tree -> Color.GREEN;
          case Burning -> Color.DARKRED;
        };
      }
      case CellularAutomata.WATOR -> {
        WaTorState s = (WaTorState)initialState;
        return switch (s) {
          case Empty -> Color.WHITE;
          case Fish -> Color.GREEN;
          case Shark -> Color.BLUE;
        };
      }
      case CellularAutomata.RPS -> {
        RPSState s = (RPSState) initialState;
        return switch (s) {
          case Rock -> Color.RED;
          case Paper -> Color.GREEN;
          case Scissors -> Color.BLUE;
        };
      }
    }

    return Color.GRAY;
  }

  public static Paint getStrokeColor(CellState initialState, int simulationType) {
    return switch (simulationType) {
      case CellularAutomata.GAME_OF_LIFE -> Color.BLUE;
      case CellularAutomata.PERCOLATION -> Color.BLACK;
      case CellularAutomata.SCHELLING_SEGREGATION -> Color.BLACK;
      case CellularAutomata.SPREADING_OF_FIRE -> Color.BLACK;
      case CellularAutomata.WATOR -> Color.BLACK;
      case CellularAutomata.RPS -> Color.BLACK;
      default -> Color.BLACK;
    };
  }
}
