package cellsociety.View;

public class HexagonCellView extends CellView{


  /**
   * Class constructor. Sets the width and height, in pixels, for this Rectangle.
   *
   * @param width
   * @param height
   */
  public HexagonCellView(double width, double height, int rowCounter, double x, double y) {
    super(width, height, rowCounter, x, y);
  }

  /**
   * Draws a hexagon
   *
   * @param width: the width of each polygon
   * @param height: the height of each polygon
   * @param rowCounter
   * @param x: the x location of the polygon
   * @param y: the y location of the polygon
   */
  @Override
  public void drawShape(double width, double height, int rowCounter, double x, double y) {
    switch(rowCounter % 2) {
      case 0 -> setLayoutX(x);
      case 1 -> setLayoutX(x + width/2);
      //case 1 -> setLayoutX(0.0);
    }
    setLayoutY(y);
    //System.out.println(getPoints());
    //points
    getPoints().addAll(
        0.0,0.0,
        width/2,-height/3,
        width,0.0,
        width,height*2/3,
        width/2,height,
        0.0,height*2/3,
        0.0,0.0
    );
  }
}
