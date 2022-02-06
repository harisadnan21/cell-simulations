package cellsociety.View;

public class SquareCellView extends CellView{

  private double width;
  private double height;

  /**
   * Class constructor. Sets the width and height, in pixels, for this Rectangle.
   *
   * @param width
   * @param height
   */
  public SquareCellView(double width, double height) {
    super(width, height);
    this.width=width;
    this.height=height;
  }

  @Override
  public void drawShape() {
    setLayoutX(0.0);
    setLayoutY(0.0);
    getPoints().addAll(
        0.0,height,
        height,width,
        height,0.0
    );
  }
}
