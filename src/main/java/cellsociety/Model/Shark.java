package cellsociety.Model;

/**
 * This class represents a Shark that can move across Cells, eat fish, reproduce, and die in WaTor.
 *
 * @author Matt Knox
 */
public class Shark extends CellObject {
  public int energy;
  private int timeSinceSex;
  private final int energyInFood;
  private final int refractoryPeriod;

  /**
   * Class constructor.
   *
   * @param refractoryPeriod number of iterations between reproduction
   * @param energyInFood
   * @param initialEnergy
   */
  public Shark(int refractoryPeriod, int energyInFood, int initialEnergy) {
    this.energyInFood = energyInFood;
    this.refractoryPeriod = refractoryPeriod;
    energy = initialEnergy;
  }

  public void age() { energy--; timeSinceSex++; }
  public boolean isDead() { return energy <= 0;}
  public void eat() { energy += energyInFood; }
  public boolean canReproduce() { return timeSinceSex >= refractoryPeriod; }
  public void reproduce() { timeSinceSex = 0; }

  @Override
  public boolean isFish() {
    return false;
  }

  @Override
  public boolean isShark() {
    return true;
  }

  @Override
  public String toString() { return "Shark"; }
}
