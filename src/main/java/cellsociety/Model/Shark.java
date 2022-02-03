package cellsociety.Model;

import cellsociety.CellObject;

public class Shark extends CellObject {
  private int energy;
  private int timeSinceSex;
  private final int energyInFood;
  private final int refractoryPeriod;

  public Shark(int refractoryPeriod, int energyInFood) {
    this.energyInFood = energyInFood;
    this.refractoryPeriod = refractoryPeriod;
    energy = 5;
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
}