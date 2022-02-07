package cellsociety.Model;

import cellsociety.CellularAutomata;
import java.util.Arrays;
import java.util.HashSet;

/**
 * This class represents an array of Cells belonging to a certain instance of CellularAutomataAlgorithm.
 *
 * @author Matt Knox
 */
public class Grid {

  private Cell[][] cells;
  private final CellularAutomataAlgorithm simtype;
  private SimulationData data;


  /**
   * Class constructor. Sets up the main cell array with their initial states and neighborhood.
   *
   * @param numRows number of rows in grid
   * @param numColumns number of columns in grid
   * @param initialStates array of CellStates to be assigned upon Cell initialization
   * @param data SimulationData containing all the metadata for this simulation
   * @param neighbors neighborhood configuration around given reference cell
   */
  public Grid(int numRows, int numColumns, CellState[][] initialStates, SimulationData data, int[][] neighbors) {


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
    // TODO: Replace static constants with XML input
    Neighborhood n = new Neighborhood(CellularAutomata.SQUARE);
    n.addNeighbors(cells,neighbors,data.shouldWrap(),CellularAutomata.vonNeumann);
    simtype = getSimulationType();
    simtype.initializeResidents(this);
  }

  /**
   * Clears all CellObjects living inside any cell in the grid
   */
  public void clearAllResidents() {
    for(Cell[] cellArray: cells) {
      for(Cell c: cellArray) {
        c.clearResident();
      }
    }
  }

  // Instantiates new Cell for every grid location
  private void initializeCells(int numRows, int numColumns, CellState[][] initialStates) {
    cells = new Cell[numRows][numColumns];
    for(int i = 0; i < numRows; i++) {
      for(int j = 0; j < numColumns; j++) {
        cells[i][j] = new Cell(initialStates[i][j]);
      }
    }
  }

  // Checks if any states in initialStates are invalid
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


  private CellularAutomataAlgorithm getSimulationType() {
    return switch(data.simulationType()) {
      case CellularAutomata.GAME_OF_LIFE -> new GameOfLife(data);
      case CellularAutomata.PERCOLATION -> new Percolation(data);
      case CellularAutomata.SCHELLING_SEGREGATION -> new SchellingSegregation(data);
      case CellularAutomata.SPREADING_OF_FIRE -> new SpreadingOfFire(data);
      case CellularAutomata.WATOR -> new WaTor(data);
      case CellularAutomata.FALLING_SAND -> new FallingSand(data);
      default -> throw new IllegalStateException("Unexpected value: " + data.simulationType());
    };
  }


  public void update() {
    for(Cell[] cellArray: cells) {
      for(Cell cell: cellArray) {
        cell.update();
      }
    }
  }

  public void calculateNextStates() {
    simtype.runAlgorithm(this);
  }

  public Cell[][] getCells() {
    return this.cells;
  }

}
