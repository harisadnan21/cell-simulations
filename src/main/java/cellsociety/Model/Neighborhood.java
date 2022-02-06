package cellsociety.Model;

import cellsociety.CellularAutomata;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javafx.util.Pair;

public class Neighborhood {

  public static final String ignoreCellString = "0";
  public static final String neighborCellString = "1";
  public static final String refCellString = "X";

  public static final int ignoreCell = 0;
  public static final int neighborCell = 1;
  public static final int refCell = 2;

  private static final int[][] squareMoore = new int[][]{
      { neighborCell, neighborCell, neighborCell},
      { neighborCell, refCell, neighborCell},
      { neighborCell, neighborCell, neighborCell},
  };

  private static final int[][] squareVonNeumann = new int[][]{
      { ignoreCell, neighborCell, ignoreCell},
      { neighborCell, refCell, neighborCell},
      { ignoreCell, neighborCell, ignoreCell},
  };

  private static final Set<Pair<Integer,Integer>> allowedSquareNeighbors = new HashSet<>(
      Arrays.asList(
          new Pair<>(-1,-1),
          new Pair<>(-1,0),
          new Pair<>(-1,1),
          new Pair<>(0,1),
          new Pair<>(1,1),
          new Pair<>(1,0),
          new Pair<>(1,-1),
          new Pair<>(0,-1)
      ));

  private static final Set<Pair<Integer,Integer>> allowedEvenRowHexagonNeighbors = new HashSet<>(
      Arrays.asList(
        new Pair<>(-2,0),
        new Pair<>(-1,0),
        new Pair<>(1,0),
        new Pair<>(2,0),
        new Pair<>(1,-1),
        new Pair<>(-1,-1)
  ));

  private static final Set<Pair<Integer,Integer>> allowedOddRowHexagonNeighbors = new HashSet<>(
      Arrays.asList(
          new Pair<>(-2,0),
          new Pair<>(-1,1),
          new Pair<>(1,1),
          new Pair<>(2,0),
          new Pair<>(1,0),
          new Pair<>(-1,-0)
      )
  );

  private static final Set<Pair<Integer,Integer>> allowedUpMaxTriangleNeighbors = new HashSet<>(
      Arrays.asList(
          new Pair<>(-1,0),
          new Pair<>(-1,1),
          new Pair<>(0,1),
          new Pair<>(0,2),
          new Pair<>(1,2),
          new Pair<>(1,1),
          new Pair<>(1,0),
          new Pair<>(1,-1),
          new Pair<>(1,-2),
          new Pair<>(0,-2),
          new Pair<>(0,-1),
          new Pair<>(-1,-1)
      )
  );

  private static final Set<Pair<Integer,Integer>> allowedDownMaxTriangleNeighbors = new HashSet<>(
      Arrays.asList(
          new Pair<>(-1,0),
          new Pair<>(-1,1),
          new Pair<>(-1,2),
          new Pair<>(0,2),
          new Pair<>(0,1),
          new Pair<>(1,1),
          new Pair<>(1,0),
          new Pair<>(1,-1),
          new Pair<>(0,-1),
          new Pair<>(0,-2),
          new Pair<>(-1,-2),
          new Pair<>(-1,-1)
      )
  );

  private static final Set<Pair<Integer,Integer>> allowedUpSideTriangleNeighbors = new HashSet<>(
      Arrays.asList(
          new Pair<>(-1,0),
          new Pair<>(1,0),
          new Pair<>(0,1)
      )
  );

  private static final Set<Pair<Integer,Integer>> allowedDownSideTriangleNeighbors = new HashSet<>(
      Arrays.asList(
          new Pair<>(-1,0),
          new Pair<>(1,0),
          new Pair<>(0,-1)
      )
  );

  private static final Set<Pair<Integer,Integer>> allowedUpTipTriangleNeighbors = new HashSet<>(
      Arrays.asList(
          new Pair<>(-1,0),
          new Pair<>(1,-2),
          new Pair<>(1,2)
      )
  );

  private static final Set<Pair<Integer,Integer>> allowedDownTipTriangleNeighbors = new HashSet<>(
      Arrays.asList(
          new Pair<>(1,0),
          new Pair<>(-1,-2),
          new Pair<>(-1,2)
      )
  );

  private static final int[][] allowedNeighbors = new int[][]{
      { neighborCell, neighborCell, neighborCell, neighborCell, neighborCell},
      { neighborCell, neighborCell, neighborCell, neighborCell, neighborCell},
      { neighborCell, neighborCell, refCell, neighborCell, neighborCell},
      { neighborCell, neighborCell, neighborCell, neighborCell, neighborCell},
      { neighborCell, neighborCell, neighborCell, neighborCell, neighborCell},
  };



  private int gridShape;


  public Neighborhood(int gridShape) {
    this.gridShape = gridShape;
  }


  public void addNeighbors(Cell[][] cells, int[][] data, boolean wrap, String customization) {
    switch(gridShape) {
      case CellularAutomata.SQUARE -> {
        switch (customization) {
          case CellularAutomata.moore -> addSquareNeighbors(cells,squareMoore,wrap);
          case CellularAutomata.vonNeumann -> addSquareNeighbors(cells,squareVonNeumann,wrap);
          default -> addSquareNeighbors(cells,data,wrap);
        }
      }
      case CellularAutomata.TRIANGLE -> {
        switch(customization) {
          case CellularAutomata.maximum -> addTriangleNeighbors(cells,allowedNeighbors, allowedUpMaxTriangleNeighbors, allowedDownMaxTriangleNeighbors);
          case CellularAutomata.sides -> addTriangleNeighbors(cells,allowedNeighbors,allowedUpSideTriangleNeighbors,allowedDownSideTriangleNeighbors);
          case CellularAutomata.tips -> addTriangleNeighbors(cells,allowedNeighbors,allowedUpTipTriangleNeighbors,allowedDownTipTriangleNeighbors);
          default -> addTriangleNeighbors(cells,data,allowedUpMaxTriangleNeighbors,allowedDownMaxTriangleNeighbors);
        }
      }
      case CellularAutomata.HEXAGON -> {
        switch (customization) {
          case CellularAutomata.maximum -> addHexagonNeighbors(cells,allowedNeighbors);
          default -> addHexagonNeighbors(cells,data);
        }
      }
    }
  }


  private void addSquareNeighbors(Cell[][] cells, int[][] data, boolean wrap) {
    Set<Pair<Integer,Integer>> relativeNeighbors = getRelativeNeighborSet(data);
    relativeNeighbors.retainAll(allowedSquareNeighbors);

    for(int i = 0; i < cells.length; i++) {
      for(int j = 0; j < cells[0].length; j++) {
        Collection<Cell> assignedNeighbors = new HashSet<>();
        for(Pair<Integer,Integer> neighbor: relativeNeighbors) {
          int xLoc = i + neighbor.getKey();
          int yLoc = j + neighbor.getValue();
          if(inBounds(xLoc,yLoc,cells)) {
            assignedNeighbors.add(cells[xLoc][yLoc]);
          } else if (wrap) {
            addIfAble(cells,xLoc - cells.length, yLoc, assignedNeighbors);
            addIfAble(cells,xLoc + cells.length, yLoc, assignedNeighbors);
            addIfAble(cells,xLoc,yLoc - cells[0].length, assignedNeighbors);
            addIfAble(cells,xLoc,yLoc + cells[0].length, assignedNeighbors);
          }
        }
        cells[i][j].addNeighbors(assignedNeighbors);
      }
    }
  }

  private void addHexagonNeighbors(Cell[][] cells, int[][] data) {
    int[][] evenRowData = parseHexagonEvenRowInput(data);
    Set<Pair<Integer,Integer>> relativeEvenRowNeighbors = getRelativeNeighborSet(evenRowData);
    relativeEvenRowNeighbors.retainAll(allowedEvenRowHexagonNeighbors);

    int[][] oddRowData = parseHexagonOddRowInput(data);
    Set<Pair<Integer,Integer>> relativeOddRowNeighbors = getRelativeNeighborSet(oddRowData);
    relativeOddRowNeighbors.retainAll(allowedOddRowHexagonNeighbors);

    for(int i = 0; i < cells.length; i++) {
      for(int j = 0; j < cells[0].length; j++) {
        Collection<Cell> assignedNeighbors = new HashSet<>();
        Collection<Pair<Integer,Integer>> iterableNeighbors = (i%2 == 0) ? relativeEvenRowNeighbors : relativeOddRowNeighbors;
        for(Pair<Integer,Integer> neighbor: iterableNeighbors) {
          int xLoc = i + neighbor.getKey();
          int yLoc = j + neighbor.getValue();
          if(inBounds(xLoc,yLoc,cells)) {
            assignedNeighbors.add(cells[xLoc][yLoc]);
          }
        }
        cells[i][j].addNeighbors(assignedNeighbors);
      }
    }
  }

  private void addTriangleNeighbors(Cell[][] cells, int[][] data,
      Set<Pair<Integer,Integer>> allowedUpNeighbors, Set<Pair<Integer,Integer>> allowedDownNeighbors) {
    Set<Pair<Integer,Integer>> relativeUpNeighbors = getRelativeNeighborSet(data);
    relativeUpNeighbors.retainAll(allowedUpNeighbors);
    Set<Pair<Integer,Integer>> relativeDownNeighbors = getRelativeNeighborSet(data);
    relativeDownNeighbors.retainAll(allowedDownNeighbors);

    for(int i = 0; i < cells.length; i++) {
      for(int j = 0; j < cells[0].length; j++) {
        Collection<Cell> assignedNeighbors = new HashSet<>();
        Collection<Pair<Integer,Integer>> iterableNeighbors = ((i+j)%2 == 0) ? relativeUpNeighbors : relativeDownNeighbors;
        for(Pair<Integer,Integer> neighbor: iterableNeighbors) {
          int xLoc = i + neighbor.getKey();
          int yLoc = j + neighbor.getValue();
          if(inBounds(xLoc,yLoc,cells)) {
            assignedNeighbors.add(cells[xLoc][yLoc]);
          }
        }
        cells[i][j].addNeighbors(assignedNeighbors);
      }
    }
  }


  private int[][] parseHexagonEvenRowInput(int[][] input) {
    int[][] ret = new int[5][2];
    ret[0][0] = ignoreCell; ret[0][1] = input[0][1];
    ret[1][0] = input[0][0]; ret[1][1] = input[0][2];
    ret[2][0] = ignoreCell; ret[2][1] = refCell;
    ret[3][0] = input[2][0]; ret[3][1] = input[2][2];
    ret[4][0] = ignoreCell; ret[4][1] = input[2][1];
    return ret;
  }

  private int[][] parseHexagonOddRowInput(int[][] input) {
    int[][] ret = new int[5][2];
    ret[0][0] = input[0][1]; ret[0][1] = ignoreCell;
    ret[1][0] = input[0][0]; ret[1][1] = input[0][2];
    ret[2][0] = refCell; ret[2][1] = ignoreCell;
    ret[3][0] = input[2][0]; ret[3][1] = input[2][2];
    ret[4][0] = input[2][1]; ret[4][1] = ignoreCell;
    return ret;
  }



  private Pair<Integer,Integer> getReferenceCell(int[][] neighbors) {
    for(int i = 0; i < neighbors.length; i++) {
      for(int j = 0; j < neighbors[0].length; j++) {
        if(neighbors[i][j] == refCell)
          return new Pair<>(i,j);
      }
    }
    return null;
  }

  private boolean inBounds(int i, int j, Cell[][] cells) {
    if(i < 0) return false;
    if(j < 0) return false;
    if(i >= cells.length) return false;
    if(j >= cells[0].length) return false;
    return true;
  }

  private Set<Pair<Integer, Integer>> getRelativeNeighborSet(int[][] neighbors) {
    Set<Pair<Integer, Integer>> allNeighbors;
    allNeighbors = new HashSet<>();
    Pair<Integer,Integer> referenceCell = getReferenceCell(neighbors);
    int xLocation = referenceCell.getKey();
    int yLocation = referenceCell.getValue();

    for(int i = 0; i < neighbors.length; i++) {
      for(int j = 0; j < neighbors[0].length; j++) {
        if(neighbors[i][j] == neighborCell) {
          int xToAdd = i - xLocation;
          int yToAdd = j - yLocation;
          allNeighbors.add(new Pair<>(xToAdd,yToAdd));
        }
      }
    }
    return allNeighbors;
  }

  private void addIfAble(Cell[][] cells, int xLocation, int yLocation, Collection<Cell> asssignedNeighborss) {
    if(inBounds(xLocation,yLocation,cells))
      asssignedNeighborss.add(cells[xLocation][yLocation]);
  }


}
