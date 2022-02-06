package cellsociety.View;

public class TriangleCellView extends CellView{

  private double width;
  private double height;
  private int modCounter;
  /**
   * Class constructor. Sets the width and height, in pixels, for this Rectangle.
   *
   * @param width
   * @param height
   */
  public TriangleCellView(double width, double height, int modCounter) {
    super(width, height);
    this.width = width;
    this.height = height;
    this.modCounter = modCounter;
  }

  @Override
  public void drawShape() {
    setLayoutX(0.0);
    setLayoutY(0.0);
    switch(modCounter % 2) {
      case 0 -> drawUpTriangle();
      case 1 -> drawDownTriangle();
    }
  }

  private void drawDownTriangle() {
    getPoints().addAll(
        -width/2,0.0,
        width/2,0.0,
        0.0,height
    );
  }

  private void drawUpTriangle() {
    getPoints().addAll(
      width/2,0.0,
      0.0,height,
      width,height
    );
  }
}
