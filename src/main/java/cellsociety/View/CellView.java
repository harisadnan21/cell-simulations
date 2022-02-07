package cellsociety.View;

import cellsociety.Model.CellState;
import javafx.scene.shape.Polygon;

/**
 * This class represents a rectangular visual depiction of one cell in a cell automata simulation.
 *
 * @author Edison Ooi
 */
public abstract class CellView extends Polygon {

  /**
   * Class constructor. Sets the width and height, in pixels, for this Rectangle.
   *
   * @param width
   * @param height
   */
  public CellView(double width, double height) {
    super(width, height);
    drawShape();
  }

  // Updates visual properties of CellView according to its state
//  protected void updateFillAndStroke(CellState nextState, int simulationType) {
//    setFill(GridView.getFillColor(nextState,simulationType));
//    setStroke(GridView.getStrokeColor(nextState,simulationType));
//  }

  public abstract void drawShape();
}
