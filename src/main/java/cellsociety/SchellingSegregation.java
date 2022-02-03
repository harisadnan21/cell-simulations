package cellsociety;


import cellsociety.CellState.GameOfLifeState;
import cellsociety.CellState.SchellingSegregationState;
import cellsociety.CellState.WaTorState;
import java.util.List;
import java.util.Map;

public class SchellingSegregation extends CellularAutomataAlgorithm {

  public static final List<String> SPECIFIC_PARAMS = List.of("Satisfaction");

  private double satisfaction;



  /**
   * Class constructor.
   *
   * @param data SimulationData record that contains the data obtained from parsing the configuration
   *             XML
   */
  public SchellingSegregation(SimulationData data) {
    super(data);
  }

  @Override
  protected void setUpSimulationParameters() {
    checkSimulationParameters(SPECIFIC_PARAMS);
    Map<String, String> params = getSimulationParams();
    satisfaction = Double.parseDouble(params.get("Satisfaction"));
  }

  /*
  Algorithm:
  iterate through all agents, if they are not satisfied, find a new location
    if a new location has been found, set cell.next to empty, set newlocation.next to agent
    if no new location has been found, set cell.next to cell.current
  Iterate through all empty cells, if cell.next is null, set it to empty
   */
  @Override
  public void runAlgorithm(Grid g) {
    for(Cell[] cellArray: g.getCells()) {
      for(Cell c: cellArray) {
        SchellingSegregationState currentState = (SchellingSegregationState) c.getState();
        if(currentState != SchellingSegregationState.Empty) {
          if(!isSatisfied(c)) {
            Cell nextLocation = findEmptyCell(g);
            if(nextLocation == null) { //no new location has been found
              c.assignNextState(currentState);
            } else { //new location found
              c.assignNextState(SchellingSegregationState.Empty);
              nextLocation.assignNextState(currentState);
            }
          } else { //location is satisfied
            c.assignNextState(c.getState());
          }
        }
      }
    }
    for(Cell[] cellArray: g.getCells()) {
      for(Cell c: cellArray) {
        SchellingSegregationState currentState = (SchellingSegregationState) c.getState();
        if(currentState == SchellingSegregationState.Empty) {
          if(c.getNextState() == null) {
            c.assignNextState(SchellingSegregationState.Empty);
          }
        }
      }
    }
    /*
    switch((SchellingSegregationState)c.getState()) {
      case AgentA -> { return handleAgentAState(g,c); }
      case AgentB -> { return handleAgentBState(g,c); }
      case Empty -> { return handleEmptyState(g,c); }
    }
     */

  }

  /*
  private CellState handleEmptyState(Grid g, Cell c) {
    return SchellingSegregationState.Empty;
  }

  private CellState handleAgentBState(Grid g, Cell c) {
    if(isSatisfied(c)) {
      return SchellingSegregationState.AgentB;
    } else {
      Cell destination = findEmptyCell(g);
      c.moveResidentTo(destination);
      destination.assignNextState(SchellingSegregationState.AgentB);
      return SchellingSegregationState.Empty;
    }
  }

  private CellState handleAgentAState(Grid g, Cell c) {
    if(isSatisfied(c)) {
      return SchellingSegregationState.AgentA;
    } else {
      Cell destination = findEmptyCell(g);
      c.moveResidentTo(destination);
      destination.assignNextState(SchellingSegregationState.AgentA);
      return SchellingSegregationState.Empty;
    }
  }
   */

  private boolean isSatisfied(Cell c) {
    int numInGroup = 0;
    int numOutGroup = 0;
    SchellingSegregationState currentState = (SchellingSegregationState) c.getState();
    for(Cell neighbor: c.getNeighbors()) {
      SchellingSegregationState neighborState = (SchellingSegregationState) neighbor.getState();
      if(currentState == neighborState) {
        numInGroup++;
      } else if(neighborState != SchellingSegregationState.Empty) { //must be out group
        numOutGroup++;
      }
    }
    double proportionInGroup = ((double)numInGroup) / ((double)(numInGroup + numOutGroup));
    if(Double.isNaN(proportionInGroup)) return true;
    return proportionInGroup >= satisfaction;
  }

  //We want to find a cell that is both empty and has not been assigned yet
  //If a cell is empty, it's state is empty
  //If it has not been assigned yet, it's next state is null
  private Cell findEmptyCell(Grid g) {
    Cell[][] allCells = g.getCells();
    for(Cell[] cellArray: allCells) {
      for(Cell c: cellArray) {
        if(c.getState() == SchellingSegregationState.Empty && c.getNextState() == null) //found empty cell
          return c;
      }
    }
    return null;
  }
}