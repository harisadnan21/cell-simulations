package cellsociety.Model;

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

  private Set<Pair<Integer,Integer>> allNeighbors;
  private int[][] neighbors;


  public Neighborhood(int[][] data) {
    neighbors = data;
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


  public void addNeighbors(Cell[][] cells) {
    for(int i = 0; i < cells.length; i++) {
      for(int j = 0; j < cells[0].length; j++) {
        Collection<Cell> assignedNeighbors = new HashSet<>();
        for(Pair<Integer,Integer> neighbor: allNeighbors) {
          int xLocation = i + neighbor.getKey();
          int yLocation = j + neighbor.getValue();
          if(inBounds(xLocation,yLocation,cells)) {
            assignedNeighbors.add(cells[xLocation][yLocation]);
          }
        }
        cells[i][j].addNeighbors(assignedNeighbors);
      }
    }
  }


}
