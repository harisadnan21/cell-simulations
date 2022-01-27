package cellsociety;

public interface CellState {
  enum GameOfLifeState implements CellState {
    Live,
    Dead
  }

  enum SpreadingOfFireState implements CellState {
    Empty,
    Tree,
    Burning
  }

  enum SchellingSegregationState implements CellState {
    Empty,
    AgentA,
    AgentB
  }

  enum WaTorState implements CellState {
    Empty,
    Fish,
    Shark
  }

  enum PercolationState implements CellState {
    Blocked,
    Open,
    Percolated
  }
}
