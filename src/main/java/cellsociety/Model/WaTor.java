package cellsociety.Model;


import cellsociety.Model.CellState.WaTorState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WaTor extends CellularAutomataAlgorithm {

  public static final List<String> SPECIFIC_PARAMS = List.of("fishRefractoryPeriod", "sharkRefractoryPeriod", "energyInFood", "sharkInitialEnergy");

  private int fishRefractoryPeriod;
  private int sharkRefractoryPeriod;
  private int energyInFish;
  private int sharkInitialEnergy;

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
    energyInFish = Integer.parseInt(params.get("energyInFood"));
    sharkInitialEnergy = Integer.parseInt(params.get("sharkInitialEnergy"));
  }

  @Override
  protected void initializeResidents(Grid g) {
    for(Cell[] cellArray: g.getCells()) {
      for(Cell c: cellArray) {
        WaTorState currentState = (WaTorState) c.getState();
        switch(currentState) {
          case Shark -> {
            c.setResident(new Shark(sharkRefractoryPeriod,energyInFish,sharkInitialEnergy));
          } case Fish -> {
            c.setResident(new Fish(fishRefractoryPeriod));
          }
        }
      }
    }
  }

  @Override
  public void runAlgorithm(Grid g) {
    // find out where each resident will go
    Map<Cell, Set<CellObject>> locations = createLocations(g); //maps cells to their new resident
    manageSharkFishConflicts(locations);
    manageSharkDeath(locations);
    g.clearAllResidents();
    assignNextStates(locations);
  }

  //methods called by runAlgorithm
  private Map<Cell, Set<CellObject>> createLocations(Grid g) {
    Map<Cell, Set<CellObject>> locations = new HashMap<>();
    handleFishAndSharkLocation(g, locations);
    handleEmptyLocation(g, locations);
    return locations;
  }
  private void manageSharkFishConflicts(Map<Cell, Set<CellObject>> locations) {
    for(Set<CellObject> location: locations.values()) {
      if(location.size() >= 2) {
        Collection<CellObject> fishToRemove = new HashSet<>();
        for(CellObject resident: location) {
          if(resident.isFish()) { //fish dies
            fishToRemove.add(resident);
          } else if(resident.isShark()) { //shark eats
            Shark s = (Shark) resident;
            s.eat();
          }
        }
        location.removeAll(fishToRemove);
      }
    }
  }
  private void manageSharkDeath(Map<Cell, Set<CellObject>> locations) {
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
  }
  private void assignNextStates(Map<Cell, Set<CellObject>> locations) {

    for(Cell c: locations.keySet()) {
      Set<CellObject> location = locations.get(c);
      if(location.isEmpty()) {
        c.assignNextState(WaTorState.Empty);
        continue;
      }
      for(CellObject resident: location) {
        c.setResident(resident);
        if(resident.isShark()) {
          c.assignNextState(WaTorState.Shark);
        } else if (resident.isFish()) {
          c.assignNextState(WaTorState.Fish);
        }
      }
    }
  }


  //methods called by createLocations
  private void handleFishAndSharkLocation(Grid g, Map<Cell, Set<CellObject>> locations) {
    for(Cell[] cellArray: g.getCells()) {
      for(Cell c: cellArray) {
        WaTorState currentState = (WaTorState) c.getState();
        if(currentState != WaTorState.Empty) {
          switch (currentState) {
            case Fish -> {
              handleFishLocation(locations, c);
            }
            case Shark -> {
              handleSharkLocation(locations, c);
            }
          }
        }
      }
    }
  }
  private void handleEmptyLocation(Grid g, Map<Cell, Set<CellObject>> locations) {
    for(Cell[] cellArray: g.getCells()) {
      for(Cell c: cellArray) {
        locations.putIfAbsent(c,new HashSet<>());
      }
    }
  }

  //methods called by handleFishAndSharkLocation
  private void handleFishLocation(Map<Cell, Set<CellObject>> locations, Cell c) {
    Fish resident = (Fish) c.getResident();
    resident.age();
    Cell nextLocation = findNextFishNeighbor(c, locations);
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
  private void handleSharkLocation(Map<Cell, Set<CellObject>> locations, Cell c) {
    Shark resident = (Shark) c.getResident();
    resident.age();
    Cell nextLocation = findNextSharkNeighbor(c, locations);
    if(nextLocation != null) {
      locations.putIfAbsent(nextLocation, new HashSet<>());
      locations.get(nextLocation).add(resident);
      if(resident.canReproduce()) {
        resident.reproduce();
        Shark child = new Shark(sharkRefractoryPeriod, energyInFish, sharkInitialEnergy);
        locations.putIfAbsent(c, new HashSet<>());
        locations.get(c).add(child);
      }
    } else { //did not find a location
      locations.putIfAbsent(c,new HashSet<>());
      locations.get(c).add(resident);
    }
  }

  //methods called by handleFishLocation
  private Cell findNextFishNeighbor(Cell c, Map<Cell, Set<CellObject>> locations) {
    for(Cell neighbor: c.getNeighbors()) {
      if(neighbor.getState() == WaTorState.Empty && !fishWillBePresent(locations,neighbor))
        return neighbor;
    }
    return null;
  }

  //methods called by handleSharkLocation
  private Cell findNextSharkNeighbor(Cell c, Map<Cell, Set<CellObject>> locations) {
    for(Cell neighbor: c.getNeighbors()) {
      if(neighbor.getState() == WaTorState.Fish && !sharkWillBePresent(locations,neighbor))
        return neighbor;
    }
    for(Cell neighbor: c.getNeighbors()) {
      if(neighbor.getState() == WaTorState.Empty && !sharkWillBePresent(locations,neighbor))
        return neighbor;
    }
    return null;
  }

  //methods called by findNextFishNeighbor
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

  //methods called by findNextSharkNeighbor
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

}