package cellsociety.View;

/**
 * This cell represents CellViews that are triangular.
 *
 * @author Matt Knox
 */
public class TriangleCellView extends CellView {

  /**
   * Class constructor. Sets the width and height, in pixels, for this Rectangle.
   *
   * @param width
   * @param height
   */
  public TriangleCellView(double width, double height, int modCounter, double x, double y) {
    super(width, height, modCounter, x, y);
  }

  /**
   * Draws a triangle
   *
   * @param width: the width of each polygon
   * @param height: the height of each polygon
   * @param modCounter
   * @param x: the x location of the polygon
   * @param y: the y location of the polygon
   */
  @Override
  public void drawShape(double width, double height, int modCounter, double x, double y) {
    setLayoutX(x);
    setLayoutY(y);
    switch(modCounter % 2) {
      case 0 -> drawUpTriangle(width,height);
      case 1 -> drawDownTriangle(width,height);
    }
  }

  private void drawDownTriangle(double width, double height) {
    getPoints().addAll(
        0.0,0.0,
        width,0.0,
        width/2,height,
        0.0,0.0
    );
  }

  private void drawUpTriangle(double width, double height) {

    getPoints().addAll(
      width/2,0.0,
        0.0,height,
        width,height,
        width/2,0.0
    );


  }
}
