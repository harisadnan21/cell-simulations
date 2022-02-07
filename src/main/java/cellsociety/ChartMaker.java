//package cellsociety;
//
//import java.util.HashMap;
//import cellsociety.Model.Cell;
//import cellsociety.Model.CellState;
//import cellsociety.Model.Grid;
//import javafx.scene.chart.NumberAxis;
//import java.util.ResourceBundle;
//import javafx.scene.chart.LineChart;
//import java.util.List;
//import javafx.scene.chart.XYChart;
//public class ChartMaker extends LineChart<Number, Number>  {
//    private final HashMap<CellState, Series<Number, Number>> data;
//    private final Grid myGrid;
//    private int stepCount;
//
//    public ChartMaker(Grid grid, ResourceBundle resources) {
//        super(new NumberAxis(), new NumberAxis());
//        this.data = new HashMap<>();
//        this.myGrid = grid;
//        this.stepCount = 0;
//        this.getXAxis().setLabel(resources.getString("StepNumber"));
//        this.getYAxis().setLabel(resources.getString("CellCount"));
//        this.setTitle(resources.getString("CellCountsByTick"));
//    }
//    public void update() {
//        List<Cell> cells[][] = (List<Cell>[][]) myGrid.getCells();
//        HashMap<CellState, Integer> stepCounts = new HashMap<>();
//
//        for (List<Cell>[] row : cells) {
//            for (Cell cell: row){
//                CellState cellState = cell.getState();
//                stepCounts.putIfAbsent(cellState, 0);
//                stepCounts.put(cellState, stepCounts.get(cellState) + 1);
//            }
//
//        }
//        for (CellState i : stepCounts.keySet()) {
//            if (data.get(i) == null) {
//                Series<Number, Number> s = new Series<>();
//                this.getData().add(s);
//                this.data.put(i, s);
//            }
//            this.data.get(i).getData().add(new XYChart.Data<>(stepCount, stepCounts.get(i)));
//        }
//        stepCount++;
//    }
//
//}
