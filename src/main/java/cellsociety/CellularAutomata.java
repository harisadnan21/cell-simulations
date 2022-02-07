package cellsociety;


import cellsociety.Model.CellState;
import cellsociety.Model.CellularAutomataAlgorithm;
import cellsociety.Model.FallingSand;
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
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
  public static final int RPS = 5;
  public static final int FALLING_SAND = 6;

  public static final int SQUARE = 0;
  public static final int TRIANGLE = 1;
  public static final int HEXAGON = 2;

  public static final int moore = 0;
  public static final int vonNeumann = 1;
  public static final int maximum = 2;
  public static final int tips = 3;
  public static final int sides = 4;


  private static final int BUTTON_PANEL_WIDTH = 100;
  public static final int BUTTON_OFFSET = 30;
  private boolean runVal;
  private CellularAutomataAlgorithm simulation;
  private Grid grid;
  private GridView gridView;
  private double frameDelay = 0.5;
  private double newTime;

  /**
   * Class constructor. Initializes visual resources for this instance of the program.
   */
  public CellularAutomata() {
    myResources = ResourceBundle.getBundle(VIEW_RESOURCE_PACKAGE + "SimulationValues", Locale.getDefault());
  }

  /**
   * Creates main scene object for simulation to be displayed on screen
   *
   * @param width width of scene
   * @param height height of scene
   * @param background background color of scene
   * @return Scene to be displayed
   */
  protected Scene setUpSimulation(int width, int height, Paint background) {

    // Get SimulationData record from XML
    File configFile = new File("./data/simulation_configs/Spreading_Of_Fire_Random_Config.xml");
    SimulationData simulationData = new ConfigurationXMLParser(
        SimulationData.DATA_TYPE).getSimulationData(configFile);

    runVal = true;
    root = new Group();
    root.getChildren().add(makeButtons());
    // Initialize simulation instance
    switch(simulationData.simulationType()) {
      case CellularAutomata.GAME_OF_LIFE -> simulation = new GameOfLife(simulationData);
      case CellularAutomata.PERCOLATION -> simulation = new Percolation(simulationData);
      case CellularAutomata.SCHELLING_SEGREGATION -> simulation = new SchellingSegregation(simulationData);
      case CellularAutomata.SPREADING_OF_FIRE -> simulation = new SpreadingOfFire(simulationData);
      case CellularAutomata.WATOR -> simulation = new WaTor(simulationData);
      case CellularAutomata.FALLING_SAND -> simulation = new FallingSand(simulationData);
    }

    // Initialize Grid and GridView with starting config
    CellState[][] initialStates = simulation.getInitialCellConfig();
    int[][] neighbors = simulation.getNeighborhoodConfig();

    grid = new Grid(simulationData.numRows(), simulationData.numColumns(), initialStates, simulationData, neighbors);
    gridView = new GridView(BUTTON_PANEL_WIDTH,0.0,width - BUTTON_PANEL_WIDTH, height, simulationData.numRows(),
        simulationData.numColumns(), getCellShape(), simulationData.simulationType(), root);
    gridView.updateCells(grid.getCells());
    scene = new Scene(root, width, height, background);

    //setting up the animation
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(frameDelay), e -> update(frameDelay)));
    animation.play();

    return scene;
  }

  /**
   * Creates a vertical panel of control buttons
   * @return VBox of Buttons
   */
  public VBox makeButtons(){
    VBox row = new VBox(BUTTON_OFFSET);

    Button playButton = new Button("Play");
    Button speedUpButton = new Button("SpeedUp");
    Button slowDownButton = new Button("SlowDown");
    Button pauseButton = new Button("Pause");
    playButton.setOnAction(e -> startSim());
    speedUpButton.setOnAction(e -> speedUp());
    slowDownButton.setOnAction(e ->slowDown());
    pauseButton.setOnAction(e -> stopSim());
    row.getChildren().addAll(playButton, speedUpButton, slowDownButton, pauseButton);
    row.setAlignment(Pos.CENTER);
    return row;
  }

  /**
   * Performs calculations and updates cells for the next generation of the current cell automata
   * simulation.
   */
  public void step() {

    grid.calculateNextStates();
    grid.update();
    gridView.updateCells(grid.getCells());

  }

  /**
   * Updates view of simulation
   * @param elapsedTime time since last update
   */
  public void update(double elapsedTime) {
    newTime = newTime + elapsedTime;
    if(newTime > frameDelay && runVal) {
      newTime = 0;
      step();
    }
  }

  /**
   * Allows simulation to run.
   */
  public void startSim() {
    runVal = true;
  }

  /**
   * Stops simulation from being updated.
   */
  public void stopSim() {
    runVal = false;
  }

  // Speeds up animation
  private void speedUp() {
    frameDelay = frameDelay / 2;
  }

  private int getCellShape() {
    return switch (myResources.getString("CellShape")) {
      case "Rectangle" -> SQUARE;
      case "Triangle" -> TRIANGLE;
      case "Hexagon" -> HEXAGON;
      default -> SQUARE;
    };
  }

  // Slows down animation
  private void slowDown() {
    frameDelay = frameDelay * 2;
  }
}
