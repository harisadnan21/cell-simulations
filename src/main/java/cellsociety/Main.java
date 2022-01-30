package cellsociety;

import cellsociety.CellState.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;


/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author YOUR NAME HERE
 */
public class Main extends Application {
    // useful names for constant values used
    public static final String TITLE = "Example JavaFX Animation";
    public static final int SIZE = 400;


    /**
     * Initialize what will be displayed.
     */
    @Override
    public void start (Stage stage) {
        Circle shape = new Circle(190, 190, 20);
        shape.setFill(Color.LIGHTSTEELBLUE);

        Group root = new Group();
        root.getChildren().add(shape);

        int numrows = 50;
        int numcols = 50;

        CellState[][] startingStates = new CellState[numrows][numcols];
        for(int i = 0; i < numrows; i++) {
            for(int j = 0; j < numcols; j++) {
                int value = (int)(2*Math.random());
                switch(value) {
                    case 0 -> startingStates[i][j] = GameOfLifeState.Live;
                    case 1 -> startingStates[i][j] = GameOfLifeState.Dead;
                    case 2 -> startingStates[i][j] = PercolationState.Percolated;
                }
            }
        }

        Grid g = new Grid(0.0,0.0,numrows,numcols,SIZE,SIZE,startingStates, 0.0);
        for(Rectangle r: g.getRects()) {
            root.getChildren().add(r);
        }

        Scene scene = new Scene(root, SIZE, SIZE, Color.DARKBLUE);
        stage.setScene(scene);

        stage.setTitle(TITLE);
        stage.show();
    }
}
