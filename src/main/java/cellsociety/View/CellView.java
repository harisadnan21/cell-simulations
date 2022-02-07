package cellsociety.View;

import cellsociety.Model.CellState;
import javafx.scene.shape.Polygon;

/**
 * This class represents a rectangular visual depiction of one cell in a cell automata simulation.
 *
 * @author Matt Knox
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
   * Draws a Polygon that represents the bounds of this CellView
   * @param width
   * @param height
   * @param counter indicates the column index of the current shape
   * @param x starting x coordinate
   * @param y starting y coordinate
   */
  public abstract void drawShape(double width, double height, int counter, double x, double y);
}
