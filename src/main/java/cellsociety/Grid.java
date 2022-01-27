package cellsociety;

import cellsociety.CellState.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import javafx.scene.shape.Rectangle;

public class Grid {

  private Cell[][] cells;

  public Grid(double startingX, double startingY, int numRows, int numColumns, double width, double height, CellState[][] initialStates) {

    //make sure arguments are of correct length
    if(initialStates[0].length != numColumns) {
      throw new IllegalArgumentException("initialStates has an incorrect number of columns");
    }
    if(initialStates.length != numRows)
      throw new IllegalArgumentException("initialStates has an incorrect number of rows");

    //ensure all values of initialStates are of the same type
    if(badStates(initialStates))
      throw new IllegalArgumentException("initialStates is composed of multiple different kinds of states");

    initializeCells(startingX, startingY, numRows, numColumns, width, height, initialStates);
    addNeighbors(numRows, numColumns);
  }

  private void initializeCells(double startingX, double startingY, int numRows, int numColumns, double width, double height, CellState[][] initialStates) {
    cells = new Cell[numRows][numColumns];
    double widthPerCell = width / numRows;
    double heightPerCell = height / numColumns;
    for(int i = 0; i < numRows; i++) {
      for(int j = 0; j < numColumns; j++) {
        double x = startingX + i*widthPerCell;
        double y = startingY + j*heightPerCell;
        cells[i][j] = new Cell(x, y, widthPerCell, heightPerCell, initialStates[i][j]);
      }
    }
  }

  private boolean badStates(CellState[][] initialStates) {
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

  public void update() {
    for(Cell[] cellArray: cells) {
      for(Cell cell: cellArray) {
        cell.update();
      }
    }
  }

  public void calculate() {
    for(Cell[] cellArray: cells) {
      for(Cell cell: cellArray) {
        cell.calculate();
      }
    }
  }

  public Collection<Rectangle> getRects() {
    Collection<Rectangle> allRects = new HashSet<>();
    for(Cell[] cellArray: cells) {
      for(Cell cell: cellArray) {
        allRects.add(cell.getRect());
      }
    }
    return allRects;
  }
}
