package cellsociety.View;

import cellsociety.CellularAutomata;
import cellsociety.Model.Cell;
import cellsociety.Model.CellState;
import cellsociety.Model.CellState.GameOfLifeState;
import cellsociety.Model.CellState.PercolationState;
import cellsociety.Model.CellState.SchellingSegregationState;
import cellsociety.Model.CellState.SpreadingOfFireState;
import cellsociety.Model.CellState.WaTorState;
import cellsociety.Model.GameOfLife;
import cellsociety.Model.Percolation;
import cellsociety.Model.SchellingSegregation;
import cellsociety.Model.SpreadingOfFire;
import cellsociety.Model.WaTor;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GridView extends TilePane {
  private CellView[][] cellViews;
  private int simulationType;

  private ResourceBundle myResources;

  public static final int SQUARE = 0;
  public static final int TRIANGLE = 1;
  public static final int HEXAGON = 2;



  public GridView(double width, double height, int numRows, int numColumns, int typeOfGrid, int simulationType) {
    super();
    setWidth(width);
    setHeight(height);
    setPrefRows(numRows);
    setPrefColumns(numColumns);
    setTileAlignment(Pos.TOP_LEFT);
    this.simulationType = simulationType;
    initializeResourceBundle();
    cellViews = new CellView[numRows][numColumns];
    addCellsToGrid(width, height, numRows, numColumns, typeOfGrid);
  }

  private void initializeResourceBundle() {
    String bundleName = "";
    switch (simulationType) {
      case CellularAutomata.GAME_OF_LIFE -> bundleName = GameOfLife.BUNDLE_NAME;
      case CellularAutomata.PERCOLATION -> bundleName = Percolation.BUNDLE_NAME;
      case CellularAutomata.SCHELLING_SEGREGATION -> bundleName = SchellingSegregation.BUNDLE_NAME;
      case CellularAutomata.SPREADING_OF_FIRE -> bundleName = SpreadingOfFire.BUNDLE_NAME;
      case CellularAutomata.WATOR -> bundleName = WaTor.BUNDLE_NAME;
      default -> throw new IllegalArgumentException("Invalid simulation type number: " + simulationType);
    }

    myResources = ResourceBundle.getBundle(CellularAutomata.VIEW_RESOURCE_PACKAGE + bundleName, Locale.getDefault());
  }

  private void addCellsToGrid(double width, double height, int numRows, int numColumns, int typeOfGrid) {
    for (int i = 0; i < cellViews.length; i++) {
      for (int j = 0; j < cellViews[0].length; j++) {
        switch(typeOfGrid) {
          case SQUARE -> cellViews[i][j] = new SquareCellView((width / 1.2) / numRows, (height / 1.2) / numColumns);
          case TRIANGLE -> cellViews[i][j] = new TriangleCellView((width / 1.2) / numRows, (height / 1.2) / numColumns,i+j);
          case HEXAGON -> cellViews[i][j] = new HexagonCellView((width / 1.2) / numRows, (height / 1.2) / numColumns,i);
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
        updateFillAndStroke(cellViews[i][j], cells[i][j].getState());
        //cellViews[i][j].updateFillAndStroke(cells[i][j].getState(),simulationType);
      }
    }
  }

  // Updates fill and stroke of CellView based on Cell's state
  private void updateFillAndStroke(CellView cellView, CellState state) {
    cellView.setFill(getFillColor(state));
    cellView.setStroke(getStrokeColor());
  }

  // Returns appropriate fill color from resource bundle for given state
  private Paint getFillColor(CellState initialState) {

    switch(simulationType) {
      case CellularAutomata.GAME_OF_LIFE -> {
        GameOfLifeState s = (GameOfLifeState)initialState;
        return switch (s) {
          case Live -> Color.valueOf(myResources.getString("FillColor_Live"));
          case Dead -> Color.valueOf(myResources.getString("FillColor_Dead"));
        };
      }
      case CellularAutomata.PERCOLATION -> {
        PercolationState s = (PercolationState)initialState;
        return switch (s) {
          case Blocked -> Color.valueOf(myResources.getString("FillColor_Blocked"));
          case Open -> Color.valueOf(myResources.getString("FillColor_Open"));
          case Percolated -> Color.valueOf(myResources.getString("FillColor_Percolated"));
        };
      }
      case CellularAutomata.SCHELLING_SEGREGATION -> {
        SchellingSegregationState s = (SchellingSegregationState)initialState;
        return switch (s) {
          case Empty -> Color.valueOf(myResources.getString("FillColor_Empty"));
          case AgentA -> Color.valueOf(myResources.getString("FillColor_AgentA"));
          case AgentB -> Color.valueOf(myResources.getString("FillColor_AgentB"));
        };
      }
      case CellularAutomata.SPREADING_OF_FIRE -> {
        SpreadingOfFireState s = (SpreadingOfFireState)initialState;
        return switch (s) {
          case Empty -> Color.valueOf(myResources.getString("FillColor_Empty"));
          case Tree -> Color.valueOf(myResources.getString("FillColor_Tree"));
          case Burning -> Color.valueOf(myResources.getString("FillColor_Burning"));
        };
      }
      case CellularAutomata.WATOR -> {
        WaTorState s = (WaTorState)initialState;
        return switch (s) {
          case Empty -> Color.valueOf(myResources.getString("FillColor_Empty"));
          case Fish -> Color.valueOf(myResources.getString("FillColor_Fish"));
          case Shark -> Color.valueOf(myResources.getString("FillColor_Shark"));
        };
      }
    }

    return Color.GRAY;
  }

  // Returns appropriate stroke color from resource bundle for given state
  private Paint getStrokeColor() {
    return Color.valueOf(myResources.getString("StrokeColor"));
  }
}
