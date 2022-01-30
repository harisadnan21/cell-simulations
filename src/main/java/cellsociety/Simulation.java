package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

public class Simulation implements EventHandler<ActionEvent> {
  private Scene scene;
  private Group root;


  private double GRIDSTARTINGX;
  private double GRIDSTARTINGY;
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
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;


  private SimulationType simtype;
  public Simulation(){
  }

  protected Scene setUpSimulation(int width, int height, Paint background){
    root = new Group();
    CellState[][] initialStates = getInitialConfig();
    Grid newgrid = new Grid(GRIDSTARTINGX, GRIDSTARTINGY,GIDROWS, GRIDCOLS, GRIDWIDTH, GRIDHEIGHT,  initialStates, GRIDSTROKEWIDTH);
    // How to add newgrid to root.
    startButton = new Button();
    stopButton= new Button();
    speedUp = new Button();
    slowDown= new Button();
    loadButton = new Button();
    saveButton = new Button();

    startButton.setText("Start");
    stopButton.setText("Stop");
    speedUp.setText(">>");
    slowDown.setText("<<");
    loadButton.setText("Load");
    saveButton.setText("Save");

    startButton.setOnAction(this);
    stopButton.setOnAction(this);
    speedUp.setOnAction(this);
    slowDown.setOnAction(this);
    loadButton.setOnAction(this);
    saveButton.setOnAction(this);

    startButton.setLayoutX();
    startButton.setLayoutY();
    stopButton.setLayoutX();
    stopButton.setLayoutY();
    saveButton.setLayoutX();
    saveButton.setLayoutY();
    loadButton.setLayoutX();
    loadButton.setLayoutY();
    speedUp.setLayoutX();
    speedUp.setLayoutY();
    slowDown.setLayoutX();
    slowDown.setLayoutY();


    root.getChildren().add(newgrid);
    root.getChildren().add(startButton);
    root.getChildren().add(stopButton);
    root.getChildren().add(speedUp);
    root.getChildren().add(slowDown);
    root.getChildren().add(loadButton);
    root.getChildren().add(saveButton);



    scene = new Scene(root, width, height, background);

    //setting up the animation
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY)));
    animation.play();



    return scene;
  }
  private CellState[][] getInitialConfig(){
   // RETURN  A LIST OF STATE OF EACH CELL FROM CONFIGURATION OR GRID
  }
  private void step(double elapsarlgotiyhm method, updates all cells according to simtype
          bloedTime){
    //runckStates.runAlgorithm();
    if ()


  }
  private void getParameter(){

  }
  private void setParameter(){

  }
  private void start(){

  }
  private void stop(){

  }
  private void save(){

  }
  private void load(){

  }
  private void speedUp(){


  }
  private void slowDown(){
    
  }

  @Override
  public void handle(ActionEvent actionEvent) {
    if(actionEvent.getSource() == startButton){
      start();
    }
    else if(actionEvent.getSource() == stopButton) {
      stop();
    }
    else if(actionEvent.getSource() == speedUp) {
      speedUp();
    }
    else if(actionEvent.getSource() == slowDown) {
      slowDown();
    }
    else if(actionEvent.getSource() == loadButton) {
      load();
    }
    else if(actionEvent.getSource() == saveButton) {
      save();
    }
  }
}
