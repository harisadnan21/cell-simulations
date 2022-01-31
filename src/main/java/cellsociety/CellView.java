package cellsociety;

import javafx.scene.shape.Rectangle;

public class CellView extends Rectangle {
    private CellState currentState;

    public CellView() {
        super(50, 50);
    }

    public CellView(double x, double y, double width, double height, CellState initialState, double strokeWidth) {

        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setFill(GridView.getFillColor(initialState));
        setStroke(GridView.getStrokeColor(initialState));
        setStrokeWidth(strokeWidth);
        currentState = initialState;
    }

    protected void updateColor(CellView rectangle, CellState nextState){
        rect.setFill(GridView.getFillColor(nextState));
        rect.setStroke(GridView.getStrokeColor(nextState));

    }

}
