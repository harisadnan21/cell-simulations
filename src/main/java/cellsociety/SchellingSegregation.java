package cellsociety;


import cellsociety.CellState.GameOfLifeState;
import cellsociety.CellState.SchellingSegregationState;
import cellsociety.CellState.WaTorState;
import java.util.List;
import java.util.Map;

public class SchellingSegregation extends CellularAutomataAlgorithm {

  public static final List<String> SPECIFIC_PARAMS = List.of("satisfaction");

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
    checkSimulationParameters();
    Map<String, String> params = getSimulationParams();
    satisfaction = Double.parseDouble(params.get("satisfaction"));
  }

  @Override
  protected void checkSimulationParameters() {
    Map<String, String> params = getSimulationParams();

    for (String param : SPECIFIC_PARAMS) {
      if (!params.containsKey(param)) {
        throw new RuntimeException("Parameter " + param + " not found for simulation type "
            + "SchellingSegregation");
      }
    }
  }

  @Override
  public CellState runAlgorithm(Grid g, Cell c) {
    switch((SchellingSegregationState)c.getState()) {
      case AgentA -> { return handleAgentAState(g,c); }
      case AgentB -> { return handleAgentBState(g,c); }
      case Empty -> { return handleEmptyState(g,c); }
    }

    return null;
  }

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
    return proportionInGroup >= satisfaction;
  }

  private Cell findEmptyCell(Grid g) {
    Cell[][] allCells = g.getCells();
    for(Cell[] cellArray: allCells) {
      for(Cell c: cellArray) {
        if(c.getState() == WaTorState.Empty && (c.getNextState() == null || c.getNextState() == WaTorState.Empty)) //found empty cell
          return c;
      }
    }
    return null;
  }
}