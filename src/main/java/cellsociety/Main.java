package cellsociety;

import cellsociety.CellState.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.shape.*;


/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author Haris Adnan, Matt Knox, Edison Ooi
 */
public class Main extends Application {
    // useful names for constant values used
    public static final String TITLE = "Example JavaFX Animation";
    public static final int SIZE = 400;
    public static final Paint BACKGROUND = Color.DARKSLATEGRAY;


    /**
     * Initialize what will be displayed.
     */
    @Override
    public void start (Stage stage) {
//        Circle shape = new Circle(190, 190, 20);
//        shape.setFill(Color.LIGHTSTEELBLUE);
        Simulation newSim = new Simulation();

        // attach scene to the stage and display it
        Scene scene = newSim.setUpSimulation(SIZE, SIZE, BACKGROUND);
        stage.setScene(scene);
        stage.setTitle(TITLE);
        stage.show();

    }
}
