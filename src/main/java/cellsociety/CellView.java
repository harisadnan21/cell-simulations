package cellsociety;

import javafx.scene.shape.Rectangle;

public class CellView extends Rectangle {
    private CellState currentState;

    public CellView() {
        super();
    }

    public CellView(double width, double height) {
        setWidth(width);
        setHeight(height);
        //updateFillAndStroke(initialState);
        //currentState = initialState;
    }

    protected void updateFillAndStroke(CellState nextState){
        setFill(GridView.getFillColor(nextState));
        setStroke(GridView.getStrokeColor(nextState));

    }

}
