package cellsociety.View;

public class SquareCellView extends CellView{


  /**
   * Class constructor. Sets the width and height, in pixels, for this Rectangle.
   *
   * @param width
   * @param height
   */
  public SquareCellView(double width, double height, double x, double y) {
    super(width, height, 0, x, y);
  }

  /**
   * Draws a square
   *
   * @param width: the width of each polygon
   * @param height: the height of each polygon
   * @param counter: a counter specific to each polygon
   * @param x: the x location of the polygon
   * @param y: the y location of the polygon
   */
  @Override
  public void drawShape(double width, double height, int counter, double x, double y) {
    setLayoutX(x);
    setLayoutY(y);

    getPoints().addAll(
        0.0,height,
        height,width,
        height,0.0,
        0.0,0.0,
        0.0,height
    );



    /*
    getPoints().addAll(
        0.0,100.0,
        100.0,100.0,
        100.0,0.0,
        0.0,0.0,
        0.0,100.0
    );

     */
  }
}
