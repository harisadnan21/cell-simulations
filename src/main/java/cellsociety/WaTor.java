package cellsociety;


import cellsociety.CellState.GameOfLifeState;
import cellsociety.CellState.WaTorState;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    checkSimulationParameters(SPECIFIC_PARAMS);
    Map<String, String> params = getSimulationParams();
    fishRefractoryPeriod = Integer.parseInt(params.get("fishRefractoryPeriod"));
    sharkRefractoryPeriod = Integer.parseInt(params.get("sharkRefractoryPeriod"));
    energyInFood = Integer.parseInt(params.get("energyInFood"));
  }
  
  private boolean fishWillBePresent(Map<Cell, Set<CellObject>> locations, Cell c) {
    if(!locations.containsKey(c)) return false; 
    Set<CellObject> residents = locations.get(c);
    for(CellObject resident: residents) {
      if(resident.isFish()) {
        return true;
      }
    }
    return false;
  }

  private boolean sharkWillBePresent(Map<Cell, Set<CellObject>> locations, Cell c) {
    if(!locations.containsKey(c)) return false;
    Set<CellObject> residents = locations.get(c);
    for(CellObject resident: residents) {
      if(resident.isShark()) {
        return true;
      }
    }
    return false;
  }
  
  private Map<Cell, Set<CellObject>> createLocations(Grid g) {
    Map<Cell, Set<CellObject>> locations = new HashMap<>();
    for(Cell[] cellArray: g.getCells()) {
      for(Cell c: cellArray) {
        WaTorState currentState = (WaTorState) c.getState();
        if(currentState != WaTorState.Empty) {
          switch (currentState) {
            case Fish -> {
              Fish resident = (Fish) c.getResident();
              resident.age();
              Cell nextLocation = findNextFishNeighbor(c,locations);
              if (nextLocation != null) { //found a location
                locations.putIfAbsent(nextLocation,new HashSet<>());
                locations.get(nextLocation).add(resident);
                if(resident.canReproduce()) {
                  resident.reproduce();
                  Fish child = new Fish(fishRefractoryPeriod);
                  locations.putIfAbsent(c,new HashSet<>());
                  locations.get(c).add(child);
                }
              } else { //did not find a location
                locations.putIfAbsent(c,new HashSet<>());
                locations.get(c).add(resident);
              }
            }
            case Shark -> {
              Shark resident = (Shark) c.getResident();
              resident.age();
              Cell nextLocation = findNextSharkNeighbor(c,locations);
              if(nextLocation != null) {
                locations.putIfAbsent(nextLocation, new HashSet<>());
                locations.get(nextLocation).add(resident);
                if(resident.canReproduce()) {
                  resident.reproduce();
                  Shark child = new Shark(sharkRefractoryPeriod,energyInFood);
                  locations.get(c).add(child);
                }
              } else { //did not find a location
                locations.putIfAbsent(c,new HashSet<>());
                locations.get(c).add(resident);
              }
            }
          }
        }
      }
    }
    for(Cell[] cellArray: g.getCells()) {
      for(Cell c: cellArray) {
        locations.putIfAbsent(c,new HashSet<>());
      }
    }
    return locations;
  }

  @Override
  public void runAlgorithm(Grid g) {
    // find out where each resident will go
    Map<Cell, Set<CellObject>> locations = createLocations(g); //maps cells to their new resident

    // manage shark and fish conflicts
    for(Set<CellObject> location: locations.values()) {
      if(location.size() >= 2) {
        System.out.printf("New locations has a size of %d.\n",location.size());
        Collection<CellObject> fishToRemove = new HashSet<>();
        for(CellObject resident: location) {
          if(resident.isFish()) { //fish dies
            fishToRemove.add(resident);
            //location.remove(resident);
          } else if(resident.isShark()) { //shark eats
            Shark s = (Shark) resident;
            s.eat();
          }
        }
        location.removeAll(fishToRemove);
      }
    }

    //check if sharks die, remove them if necessary
    for(Set<CellObject> location: locations.values()) {
      for(CellObject resident: location) {
        if(resident.isShark()) {
          Shark r = (Shark) resident;
          if(r.isDead()) {
            location.remove(resident);
          }
        }
      }
    }

    // assign new states
    for(Cell c: locations.keySet()) {
      Set<CellObject> location = locations.get(c);
      if(location.isEmpty()) {
        c.assignNextState(WaTorState.Empty);
        continue;
      }
      for(CellObject resident: location) {
        if(resident.isShark()) {
          c.assignNextState(WaTorState.Shark);
        } else if (resident.isFish()) {
          c.assignNextState(WaTorState.Fish);
        }
      }
    }

    // chec

    /*
    for(Cell[] cellArray: g.getCells()) {
      for(Cell c: cellArray) {
        WaTorState currentState = (WaTorState) c.getState();
        switch(currentState) {
          case Fish -> {
            CellObject r = c.getResident();
            boolean isAlive = r.isFish();
            if (isAlive) { //the fish was not eaten
              Fish resident = (Fish) c.getResident();
              Cell nextLocation = findNextFishNeighbor(c);
            } else { //the fish was eaten

            }


          }
          case Shark -> {

          }
        }
      }
    }

     */

    /*
    switch((WaTorState)c.getState()) {
      case Fish -> { return handleFishState(g,c); }
      case Shark -> { return handleSharkState(g,c); }
      case Empty -> { return handleEmptyState(g,c); }
    }

    return null;

     */
  }

  private Cell findEmptyNeighbor(Cell c) {
    for(Cell neighbor: c.getNeighbors()) {
      if(neighbor.getState() == WaTorState.Empty && neighbor.getNextState() == null)
        return neighbor;
    }
    return null;
  }

  private Cell findNextFishNeighbor(Cell c, Map<Cell, Set<CellObject>> locations) {
    for(Cell neighbor: c.getNeighbors()) {
      if(neighbor.getState() == WaTorState.Empty && !fishWillBePresent(locations,c))
        return neighbor;
    }
    return null;
  }

  private Cell findNextSharkNeighbor(Cell c, Map<Cell, Set<CellObject>> locations) {
    for(Cell neighbor: c.getNeighbors()) {
      if(neighbor.getState() == WaTorState.Fish && !sharkWillBePresent(locations,c))
        return neighbor;
    }
    for(Cell neighbor: c.getNeighbors()) {
      if(neighbor.getState() == WaTorState.Empty && !sharkWillBePresent(locations,c))
        return neighbor;
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