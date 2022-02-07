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
import javafx.scene.Group;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GridView  {
  private CellView[][] cellViews;
  private int simulationType;

  private double startingX;
  private double startingY;
  private int numRows;
  private int numColumns;
  private double width;
  private double height;
  private Group g;



  public GridView(double x, double y, double width, double height, int numRows, int numColumns, int typeOfGrid, int simulationType, Group g) {
    super();
    startingX = x;
    startingY = y;
    this.width = width;//setWidth(width);
    this.height = height;//setHeight(height);
    this.numRows = numRows;//setPrefRows(numRows);
    this.numColumns = numColumns;//setPrefColumns(numColumns);
    //setTileAlignment(Pos.TOP_LEFT);
    this.simulationType = simulationType;
    this.g = g;
    cellViews = new CellView[numRows][numColumns];
    addCellsToGrid(width, height, numRows, numColumns, typeOfGrid);
  }

  private void addCellsToGrid(double width, double height, int numRows, int numColumns, int typeOfGrid) {
    for (int i = 0; i < cellViews.length; i++) {
      for (int j = 0; j < cellViews[0].length; j++) {
        double xLocation = startingX + (j * width/numColumns);
        double yLocation = startingY + (i * height/numRows);
        switch(typeOfGrid) {
          case CellularAutomata.SQUARE -> cellViews[i][j] = new SquareCellView(width / numColumns, height / numRows, xLocation, yLocation);
          case CellularAutomata.TRIANGLE -> cellViews[i][j] = new TriangleCellView(width / (1 + numColumns/2.0), height / numRows,i+j, startingX + (j * width/(1 + numColumns/2.0)/2.0), yLocation);
          case CellularAutomata.HEXAGON -> cellViews[i][j] = new HexagonCellView(width  / numColumns, height / numRows,i, xLocation, yLocation);
        }
        //cellViews[i][j] = new CellView((width / 1.2) / numRows, (height / 1.2) / numColumns);
        g.getChildren().add(cellViews[i][j]);
      }
    }
  }

  public void updateCells(Cell[][] cells) {
    if(cells.length != numRows || cells[0].length != numColumns) {
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
