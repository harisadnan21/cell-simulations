package cellsociety.View;

public class HexagonCellView extends CellView{

  private double width;
  private double height;
  private int rowCounter;

  /**
   * Class constructor. Sets the width and height, in pixels, for this Rectangle.
   *
   * @param width
   * @param height
   */
  public HexagonCellView(double width, double height, int rowCounter) {
    super(width, height);
    this.width = width;
    this.height = height;
    this.rowCounter = rowCounter;
  }

  @Override
  public void drawShape() {
    switch(rowCounter % 2) {
      case 0 -> setLayoutX(0.0);
      case 1 -> setLayoutX(width/2);
    }
    setLayoutY(0.0);
    getPoints().addAll(
        0.0,0.0,
        width/2,-height/3,
        width,0.0,
        width,height*2/3,
        width/2,height,
        0.0,height*2/3
    );
  }
}
