package cellsociety;

import javafx.scene.shape.Rectangle;

public class CellView {
    private final Rectangle rect;
    private CellState currentState;
    private CellState nextState;
    private

    public CellView(double x, double y, double width, double height, CellState initialState, double strokeWidth, CellState secondState) {
        rect = new Rectangle();
        rect.setX(x);
        rect.setY(y);
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setFill(getFillColor(initialState));
        rect.setStroke(getStrokeColor(initialState));
        rect.setStrokeWidth(strokeWidth);
        currentState = initialState;
        nextState = secondState;
    }
//    public Cell(double x, double y, double size, CellState initialState, double strokeWidth) {
//        this(x,y,size,size,initialState,strokeWidth);
//    }

    // add cell color
    protected void updateColor(CellView rectangle, CellState initialState, CellState nextState){

    }

}
