package cellsociety;

import java.util.Arrays;
import java.util.HashSet;

public class Grid {

  private final Cell[][] cells;

  public Grid(double startingX, double startingY, int cPerRow, int cPerColumn, double width, double height, CellState initialState) {
    cells = new Cell[cPerRow][cPerColumn];
    double widthPerCell = width / cPerRow;
    double heightPerCell = height / cPerColumn;
    for(int i = 0; i < cPerRow; i++) {
      for(int j = 0; j < cPerColumn; j++) {
        double x = startingX + i*width;
        double y = startingY + j*height;
        cells[i][j] = new Cell(x, y, widthPerCell, heightPerCell, initialState);
      }
    }

    //add corner cell neighbors
    cells[0][0].addNeighbors(new HashSet<>(Arrays.asList(
        cells[0][1],
        cells[1][0],
        cells[1][1])));
    cells[0][cPerColumn-1].addNeighbors(new HashSet<>(Arrays.asList(
        cells[0][cPerColumn-2],
        cells[1][cPerColumn-1],
        cells[1][cPerColumn-2])));
    cells[cPerRow-1][0].addNeighbors(new HashSet<>(Arrays.asList(
        cells[cPerRow-2][0],
        cells[cPerRow-1][1],
        cells[cPerRow-2][1])));
    cells[cPerRow-1][cPerColumn-1].addNeighbors(new HashSet<>(Arrays.asList(
        cells[cPerRow-2][cPerColumn-2],
        cells[cPerRow-2][cPerColumn-1],
        cells[cPerRow-1][cPerColumn-2])));


    //add edge cell neighbors
    // TODO: add edge cell neighbors

    //add middle cell neighbors
    // TODO: add middle cell neighbors
  }

  public void update() {
    for(Cell[] cellarray: cells) {
      for(Cell cell: cellarray) {
        cell.update();
      }
    }
  }

  public void calculate() {
    for(Cell[] cellarray: cells) {
      for(Cell cell: cellarray) {
        cell.calculate();
      }
    }
  }



}