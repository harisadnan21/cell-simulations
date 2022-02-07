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
import javafx.stage.Stage;
import javafx.util.Duration;

public class CellularAutomata  {

  public static final String VIEW_RESOURCE_PACKAGE = "/view/";

  private Scene scene;
  private Group root;
  private ResourceBundle myResources;

  public static final int GAME_OF_LIFE = 0;
  public static final int PERCOLATION = 1;
  public static final int SCHELLING_SEGREGATION = 2;
  public static final int SPREADING_OF_FIRE = 3;
  public static final int WATOR = 4;

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
  private static final double GRAPH_DIM = 600;
  private boolean runVal;
  private CellularAutomataAlgorithm simulation;
  private Grid grid;
  private ChartMaker chart;
  private GridView gridView;
  private double frameDelay = 1;
  private double newTime;
  //private final ResourceBundle resources;
  private GUIController guicontroller;
  public CellularAutomata(GUIController guiController, ResourceBundle languageResources) {
    this.guicontroller = guiController;
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
      case CellularAutomata.GAME_OF_LIFE -> simulation = new GameOfLife(simulationData);
      case CellularAutomata.PERCOLATION -> simulation = new Percolation(simulationData);
      case CellularAutomata.SCHELLING_SEGREGATION -> simulation = new SchellingSegregation(simulationData);
      case CellularAutomata.SPREADING_OF_FIRE -> simulation = new SpreadingOfFire(simulationData);
      case CellularAutomata.WATOR -> simulation = new WaTor(simulationData);
    }

    // Initialize Grid and GridView with starting config
    CellState[][] initialStates = simulation.getInitialCellConfig();
    int[][] neighbors = simulation.getNeighborhoodConfig();
    //boolean wrap = simulation.getWrap();

    grid = new Grid(simulationData.numRows(), simulationData.numColumns(), initialStates, simulationData, neighbors, false);
    gridView = new GridView(width, height, simulationData.numRows(),
        simulationData.numColumns(),GridView.SQUARE, simulationData.simulationType());
    gridView.updateCells(grid.getCells());
    gridView.setLayoutX(0);
    gridView.setLayoutY(0);
    scene = new Scene(new HBox(gridView), width, height, background);

    //setting up the animation
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(frameDelay), e -> update(frameDelay)));
    animation.play();

    return scene;
  }

  public void step() {

    grid.calculateNextStates();
    grid.update();
    gridView.updateCells(grid.getCells());

  }
  public void update(double elapsedTime) {
    newTime = newTime + elapsedTime;
    if(newTime > frameDelay && runVal) {
      newTime = 0;
      step();
    }
  }
  public void exitSimulation() {
    guicontroller.exitSimulation();

  }
  public void showChart() {
    this.chart = new ChartMaker(this.grid, myResources);
    Stage stage = new Stage();
    stage.setScene(new Scene(chart, GRAPH_DIM, GRAPH_DIM));
    stage.show();
  }


  public void startSim() {
    runVal = true;
  }

  public void stopSim() {
    runVal = false;
  }

//  public void openAdditionalSimulation() {
//    GUIController.createNewControlledStage();
//  }
  private void clearState() {
    this.simulation = null;
    this.frameDelay = 1.0;
    this.grid = null;
    this.gridView = null;
    this.runVal = false;

  }
  public void speedUp() {
    frameDelay = frameDelay / 2;
  }
//
  public void slowDown() {
    frameDelay = frameDelay * 2;
  }


}
