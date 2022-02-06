package cellsociety;


import cellsociety.Model.CellState;
import cellsociety.Model.CellularAutomataAlgorithm;
import cellsociety.Model.GameOfLife;
import cellsociety.Model.Grid;
import cellsociety.Model.Percolation;
import cellsociety.Model.SchellingSegregation;
import cellsociety.Model.SimulationData;
import cellsociety.Model.SpreadingOfFire;
import cellsociety.Model.WaTor;
import cellsociety.View.GridView;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

public class CellularAutomata  {

  public static final String VIEW_RESOURCE_PACKAGE = "/view/";

  private Scene scene;
  private Group root;
  private ResourceBundle myResources;

//>>>>>>> master:src/main/java/cellsociety/Simulation.java

  private double GRIDSTARTINGX = 10;
  private double GRIDSTARTINGY = 10;
  private double GRIDHEIGHT;
  private double GRIDWIDTH;
  private int GRIDCOLS;
  private int GIDROWS;
  private double GRIDSTROKEWIDTH;
  private Button startButton;
  private Button stopButton;
  private Button speedUp;
  private Button slowDown;
  private Button loadButton;
  private Button saveButton;
  private boolean runVal;
  private CellularAutomataAlgorithm simulation;
  private Grid grid;
  private GridView gridView;
  private double frameDelay = 1;

  public CellularAutomata(GUIController guiController, ResourceBundle languageResources) {
    myResources = ResourceBundle.getBundle(VIEW_RESOURCE_PACKAGE + "SimulationValues", Locale.getDefault());
  }

  protected Scene setUpSimulation(int width, int height, Paint background) {

    // Get SimulationData record from XML
    File configFile = new File("./data/simulation_configs/Percolation_ZigZag_From_Edge.xml");
    SimulationData simulationData = new ConfigurationXMLParser(
        SimulationData.DATA_TYPE).getSimulationData(configFile);

    runVal = true;
    root = new Group();

    // Initialize simulation instance
    switch(simulationData.simulationType()) {
      case CellularAutomataAlgorithm.GAME_OF_LIFE -> simulation = new GameOfLife(simulationData);
      case CellularAutomataAlgorithm.PERCOLATION -> simulation = new Percolation(simulationData);
      case CellularAutomataAlgorithm.SCHELLING_SEGREGATION -> simulation = new SchellingSegregation(simulationData);
      case CellularAutomataAlgorithm.SPREADING_OF_FIRE -> simulation = new SpreadingOfFire(simulationData);
      case CellularAutomataAlgorithm.WATOR -> simulation = new WaTor(simulationData);
    }

    // Initialize Grid and GridView with starting config
    CellState[][] initialStates = simulation.getInitialCellConfig();
    int[][] neighbors = simulation.getNeighborhoodConfig();
    boolean wrap = simulation.getWrap();

    grid = new Grid(simulationData.numRows(), simulationData.numColumns(), initialStates, simulationData, neighbors, wrap);
    gridView = new GridView(width, height, simulationData.numRows(),
        simulationData.numColumns());
    gridView.updateCells(grid.getCells());
    gridView.setLayoutX(0);
    gridView.setLayoutY(0);
    scene = new Scene(new HBox(gridView), width, height, background);

    //setting up the animation
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(frameDelay), e -> step(frameDelay)));
    animation.play();

    return scene;
  }

  public void step(double elapsedTime) {

    grid.calculateNextStates();
    grid.update();
    gridView.updateCells(grid.getCells());

  }

  public void startSim() {
    runVal = true;
  }

  public void stopSim() {
    runVal = false;
  }
//
//  private void save() {
//    stop();
//    //reverse of creating grid from parsed file
//  }
//
//  private void load() {
//    stop();
//    setUpSimulation(Main.SIZE, Main.SIZE, Main.BACKGROUND);
//
//  }
//
  private void speedUp() {
    frameDelay = frameDelay / 2;
  }
//
  private void slowDown() {
    frameDelay = frameDelay * 2;
  }


}
