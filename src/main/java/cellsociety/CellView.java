package cellsociety;

import javafx.scene.shape.Rectangle;

/**
 * This class represents a rectangular visual depiction of one cell in a cell automata simulation.
 *
 * @author Edison Ooi
 */
public class CellView extends Rectangle {

  /**
   * Class constructor. Sets the width and height, in pixels, for this Rectangle.
   *
   * @param width
   * @param height
   */
  public CellView(double width, double height) {
    super(width, height);
  }

  // Updates visual properties of CellView according to its state
  protected void updateFillAndStroke(CellState nextState) {
    setFill(GridView.getFillColor(nextState));
    setStroke(GridView.getStrokeColor(nextState));
  }
}
