package cellsociety.View;

/**
 * This cell represents CellViews that are hexagonal.
 *
 * @author Matt Knox
 */
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

  @Override
  public void drawShape(double width, double height, int rowCounter, double x, double y) {
    switch(rowCounter % 2) {
      case 0 -> setLayoutX(x);
      case 1 -> setLayoutX(x + width/2);
    }
    setLayoutY(y);
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
