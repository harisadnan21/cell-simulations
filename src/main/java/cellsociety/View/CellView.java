package cellsociety.View;

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
  public CellView(double width, double height, int counter, double x, double y) {
    drawShape(width,height, counter, x, y);
  }

  /**
   * Draws the shape of the particular polygon
   *
   * @param width: the width of each polygon
   * @param height: the height of each polygon
   * @param counter: a counter specific to each polygon
   * @param x: the x location of the polygon
   * @param y: the y location of the polygon
   */
  public abstract void drawShape(double width, double height, int counter, double x, double y);
}
