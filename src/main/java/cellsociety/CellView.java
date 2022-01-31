package cellsociety;

import javafx.scene.shape.Rectangle;

public class CellView {
    private final Rectangle rect;
    private CellState currentState;
    private CellState nextState;

    private CellView(double x, double y, double width, double height, CellState initialState, double strokeWidth, CellState secondState) {
        rect = new Rectangle();
        rect.setX(x);
        rect.setY(y);
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setFill(GridView.getFillColor(initialState));
        rect.setStroke(GridView.getStrokeColor(initialState));
        rect.setStrokeWidth(strokeWidth);
        currentState = initialState;
        nextState = secondState;
    }

    protected void updateColor(CellView rectangle, CellState nextState){
        rect.setFill(GridView.getFillColor(nextState));
        rect.setStroke(GridView.getStrokeColor(nextState));

    }

}
