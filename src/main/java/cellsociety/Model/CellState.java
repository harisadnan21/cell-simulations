package cellsociety.Model;

/**
 * Represents a possible state for a cell in a cell automata simulation.
 */
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

  enum RPSState implements CellState {
    Rock,
    Paper,
    Scissors
  }

  enum FallingSandState implements CellState {
    Empty,
    Sand
  }
}

