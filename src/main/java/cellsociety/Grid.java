package cellsociety;

import cellsociety.CellState.GameOfLifeState;
import cellsociety.CellState.PercolationState;
import cellsociety.CellState.SchellingSegregationState;
import cellsociety.CellState.SpreadingOfFireState;
import cellsociety.CellState.WaTorState;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import javafx.scene.shape.Rectangle;

public class Grid {

  private Cell[][] cells;
  private final CellularAutomataAlgorithm simtype;
  private SimulationData data;


  public Grid(int numRows, int numColumns, CellState[][] initialStates, SimulationData data) {


    System.out.println(initialStates[0].length);
    System.out.println(initialStates.length);
    //make sure arguments are of correct length
    if(initialStates[0].length != numColumns) {
      throw new IllegalArgumentException("initialStates has an incorrect number of columns");
    }
    if(initialStates.length != numRows)
      throw new IllegalArgumentException("initialStates has an incorrect number of rows");

    //ensure all values of initialStates are of the same type
    if(hasBadStates(initialStates))
      throw new IllegalArgumentException("initialStates is composed of multiple different kinds of states");

    this.data = data;
    initializeCells(numRows, numColumns, initialStates);
    addNeighbors(numRows, numColumns);
    simtype = getSimulationType(cells[0][0].getState());

  }

  private void initializeCells(int numRows, int numColumns, CellState[][] initialStates) {
    cells = new Cell[numRows][numColumns];
    for(int i = 0; i < numRows; i++) {
      for(int j = 0; j < numColumns; j++) {
        cells[i][j] = new Cell(initialStates[i][j]);
      }
    }
  }

  private boolean hasBadStates(CellState[][] initialStates) {

    if(initialStates[0][0] == null) return true;
    Class firstClass = initialStates[0][0].getClass();
    for(int i = 0; i < initialStates.length; i++) {
      for(int j = 0; j < initialStates[0].length; j++) {
        if(i == 0 && j == 0) continue;
        if(initialStates[i][j] == null) return true;
        Class currentClass = initialStates[i][j].getClass();
        if(firstClass != currentClass)
          return true;
      }
    }
    return false;
  }

  private void addNeighbors(int numRows, int numColumns) {
    //add corner cell neighbors
    //top left corner
    cells[0][0].addNeighbors(new HashSet<>(Arrays.asList(
        cells[0][1],
        cells[1][0],
        cells[1][1])));
    //top right corner
    cells[0][numColumns -1].addNeighbors(new HashSet<>(Arrays.asList(
        cells[0][numColumns -2],
        cells[1][numColumns -1],
        cells[1][numColumns -2])));
    //bottom left corner
    cells[numRows -1][0].addNeighbors(new HashSet<>(Arrays.asList(
        cells[numRows -2][0],
        cells[numRows -1][1],
        cells[numRows -2][1])));
    //bottom right corner
    cells[numRows -1][numColumns -1].addNeighbors(new HashSet<>(Arrays.asList(
        cells[numRows -2][numColumns -2],
        cells[numRows -2][numColumns -1],
        cells[numRows -1][numColumns -2])));

    //add edge cell neighbors
    //left column
    for(int i = 1; i < numRows -1; i++) {
      int j = 0;
      cells[i][j].addNeighbors(new HashSet<>(Arrays.asList(
          cells[i-1][j],
          cells[i+1][j],
          cells[i-1][j+1],
          cells[i][j+1],
          cells[i+1][j+1])));
    }

    //right column
    for(int i = 1; i < numRows -1; i++) {
      int j = numColumns - 1;
      cells[i][j].addNeighbors(new HashSet<>(Arrays.asList(
          cells[i-1][j],
          cells[i+1][j],
          cells[i-1][j-1],
          cells[i][j-1],
          cells[i+1][j-1])));
    }

    //top row
    for(int j = 1; j < numColumns -1; j++) {
      int i = 0;
      cells[i][j].addNeighbors(new HashSet<>(Arrays.asList(
          cells[i][j-1],
          cells[i][j+1],
          cells[i+1][j-1],
          cells[i+1][j],
          cells[i+1][j+1])));
    }

    // bottom row
    for(int j = 1; j < numColumns -1; j++) {
      int i = numRows - 1;
      cells[i][j].addNeighbors(new HashSet<>(Arrays.asList(
          cells[i][j-1],
          cells[i][j+1],
          cells[i-1][j-1],
          cells[i-1][j],
          cells[i-1][j+1])));
    }

    //add inner cell neighbors
    for(int i = 1; i < numRows - 1; i++) {
      for(int j = 1; j < numColumns - 1; j++) {
        cells[i][j].addNeighbors(new HashSet<>(Arrays.asList(
            cells[i][j-1],
            cells[i+1][j-1],
            cells[i+1][j],
            cells[i+1][j+1],
            cells[i][j+1],
            cells[i-1][j+1],
            cells[i-1][j],
            cells[i-1][j-1])));
      }
    }
  }

  private CellularAutomataAlgorithm getSimulationType(CellState initialState) {
    if (initialState instanceof GameOfLifeState)
      return new GameOfLife(data);
    if (initialState instanceof SpreadingOfFireState)
      return new SpreadingOfFire(data);
    if (initialState instanceof SchellingSegregationState)
      return new SchellingSegregation(data);
    if (initialState instanceof WaTorState)
      return new WaTor(data);
    if (initialState instanceof PercolationState)
      return new Percolation(data);

    // TODO: Find better default behavior
    return new GameOfLife(data);

  }

  private boolean isGameOfLife() { return cells[0][0].getState() instanceof GameOfLifeState; }
  private boolean isSpreadingOfFire() { return cells[0][0].getState() instanceof SpreadingOfFireState; }
  private boolean isSchellingSegregationState() { return cells[0][0].getState() instanceof SchellingSegregationState; }
  private boolean isWaTorState() { return cells[0][0].getState() instanceof WaTorState; }
  private boolean isPercolationState() { return cells[0][0].getState() instanceof PercolationState; }


  public void update() {
    for(Cell[] cellArray: cells) {
      for(Cell cell: cellArray) {
        calculateNextStates();
        cell.update();
      }
    }
  }

  public void calculateNextStates() {
    simtype.runAlgorithm(this);
    /*
    for(Cell[] cellArray: cells) {
      for(Cell cell: cellArray) {
        CellState next = simtype.runAlgorithm(this,cell);
        cell.assignNextState(next);
      }
    }
     */
  }

  public Cell[][] getCells() {
    return this.cells;
  }

}
