package cellsociety;


import cellsociety.CellState.GameOfLifeState;
import cellsociety.CellState.WaTorState;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WaTor extends CellularAutomataAlgorithm {

  public static final List<String> SPECIFIC_PARAMS = List.of("fishRefractoryPeriod", "sharkRefractoryPeriod", "energyInFood");

  private int fishRefractoryPeriod;
  private int sharkRefractoryPeriod;
  private int energyInFood;

  /**
   * Class constructor.
   *
   * @param data SimulationData record that contains the data obtained from parsing the configuration
   *             XML
   */
  public WaTor(SimulationData data) {
    super(data);
  }

  @Override
  protected void setUpSimulationParameters() {
    checkSimulationParameters();
    Map<String, String> params = getSimulationParams();
    fishRefractoryPeriod = Integer.parseInt(params.get("fishRefractoryPeriod"));
    sharkRefractoryPeriod = Integer.parseInt(params.get("sharkRefractoryPeriod"));
    energyInFood = Integer.parseInt(params.get("energyInFood"));
  }

  @Override
  protected void checkSimulationParameters() {
    Map<String, String> params = getSimulationParams();

    for (String param : SPECIFIC_PARAMS) {
      if (!params.containsKey(param)) {
        throw new RuntimeException("Parameter " + param + " not found for simulation type "
            + "WaTor");
      }
    }
  }

  @Override
  public CellState runAlgorithm(Grid g, Cell c) {
    switch((WaTorState)c.getState()) {
      case Fish -> { handleFishState(g,c); }
      case Shark -> { handleSharkState(g,c); }
      case Empty -> { handleEmptyState(g,c); }
    }

    return null;
  }

  private CellState handleFishState(Grid g, Cell c) {
    WaTorState currentState = (WaTorState) c.getState();
    Fish resident = (Fish)c.getResident();
    resident.age();
    List<Cell> neighborList = new ArrayList<Cell>(c.getNeighbors());
    Collections.shuffle(neighborList);
    for(Cell neighbor: neighborList) {
      WaTorState neighborState = (WaTorState) neighbor.getState();
      if(neighborState == WaTorState.Empty && neighbor.getNextState() == null) { //currently empty and no one will go there next

        c.moveResidentTo(neighbor);
        neighbor.assignNextState(WaTorState.Fish);

        if(resident.canReproduce()) {
          resident.reproduce();
          c.setResident(new Fish(fishRefractoryPeriod));
        }

        return WaTorState.Empty;
      }

    }
    return WaTorState.Fish;
  }

  private CellState handleSharkState(Grid  g,  Cell c) {
    WaTorState currentState = (WaTorState) c.getState();
    Shark resident = (Shark)c.getResident();
    resident.age();
    if(resident.isDead()) {
      c.setResident(null);
      return WaTorState.Empty;
    }
    List<Cell> neighborList = new ArrayList<Cell>(c.getNeighbors());
    Collections.shuffle(neighborList);

    //check for movement to fish
    for(Cell neighbor: neighborList) {
      WaTorState neighborState = (WaTorState) neighbor.getState();
      if(neighborState == WaTorState.Fish) { //fish is present, move here
        c.moveResidentTo(neighbor);
        neighbor.assignNextState(WaTorState.Shark);
        resident.eat();
        if(resident.canReproduce()) {
          resident.reproduce();
          c.setResident(new Shark(sharkRefractoryPeriod,energyInFood));
        }
        return WaTorState.Empty;
      }
    }

    //check for movement to empty
    for(Cell neighbor: neighborList) {
      WaTorState neighborState = (WaTorState) neighbor.getState();
      if(neighborState == WaTorState.Empty) { //cell is empty, move here
        c.moveResidentTo(neighbor);
        neighbor.assignNextState(WaTorState.Shark);
        if(resident.canReproduce()) {
          resident.reproduce();
          c.setResident(new Shark(sharkRefractoryPeriod,energyInFood));
        }
        return WaTorState.Empty;
      }
    }
    return WaTorState.Shark;
  }

  private CellState handleEmptyState(Grid g, Cell c) {
    if(c.getNextState() != null) {
      return c.getNextState();
    }
    return WaTorState.Empty;
  }
}