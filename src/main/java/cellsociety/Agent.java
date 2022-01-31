package cellsociety;

public class Agent extends CellObject{

  private boolean isAgentA;
  public Agent(boolean a) { isAgentA = a; }
  public boolean isAgentA() { return isAgentA; }
  public boolean isAgentB() { return !isAgentA; }
}
