package cellsociety.Model;

import cellsociety.CellObject;

public class Agent extends CellObject {

  private boolean isAgentA;
  public Agent(boolean a) { isAgentA = a; }
  public boolean isAgentA() { return isAgentA; }
  public boolean isAgentB() { return !isAgentA; }

  @Override
  public boolean isFish() {
    return false;
  }

  @Override
  public boolean isShark() {
    return false;
  }
}
