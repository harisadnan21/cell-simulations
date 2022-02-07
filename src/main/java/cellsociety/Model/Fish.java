package cellsociety.Model;

/**
 * This class represents a Fish in the simulation WaTor.
 * A fish can age and reproduce.
 *
 * @author Matt Knox
 */
public class Fish extends CellObject {
  private int timeSinceSex;
  private final int refractoryPeriod;
  public Fish(int refractoryPeriod) {
    this.refractoryPeriod = refractoryPeriod;
    timeSinceSex = 0;
  }
  public void age() { timeSinceSex++;}
  public boolean canReproduce() { return timeSinceSex >= refractoryPeriod; }
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
