package cellsociety.Model;

/**
 * This class represents a Fish, which can move within Cells, reproduce, and age in the WaTor simulation.
 *
 * @author Matt Knox
 */
public class Fish extends CellObject {
  // Tracks how long since this fish has reproduced, so that it can reproduce again
  private int timeSinceSex;
  // Number of iterations between reproduction
  private final int refractoryPeriod;

  /**
   * Class constructor. Initializes the refractory period and the time since reproduction.
   *
   * @param refractoryPeriod number of iterations between reproduction
   */
  public Fish(int refractoryPeriod) {
    this.refractoryPeriod = refractoryPeriod;
    timeSinceSex = 0;
  }

  /**
   * Increases the amount of time since this fish has reproduced
   */
  public void age() { timeSinceSex++;}

  /**
   * @return boolean indicating whether or not this fish is allowed to reproduce
   */
  public boolean canReproduce() { return timeSinceSex >= refractoryPeriod; }

  /**
   * Resets time since last reproduction
   */
  public void reproduce() { timeSinceSex = 0; }

  @Override
  public boolean isFish() {
    return true;
  }

  @Override
  public boolean isShark() {
    return false;
  }

  @Override
  public String toString() { return "Fish"; }
}
