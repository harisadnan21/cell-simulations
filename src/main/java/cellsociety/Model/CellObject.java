package cellsociety.Model;

/**
 * This class represents additional data about what can live in a Cell.
 */
public abstract class CellObject {

  /**
   * @return boolean indicating if this object is a Fish from WaTor
   */
  public abstract boolean isFish();

  /**
   * @return boolean indicating if this object is a Shark from WaTor
   */
  public abstract boolean isShark();

}
